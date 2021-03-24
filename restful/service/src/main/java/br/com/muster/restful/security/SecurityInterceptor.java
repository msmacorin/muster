package br.com.muster.restful.security;

import br.com.muster.facade.admin.UsuarioFacade;
import br.com.muster.model.admin.ParametroSistema;
import br.com.muster.model.admin.Perfil;
import br.com.muster.model.admin.Usuario;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.util.Base64;

/**
 * This interceptor verify the access permissions for a user based on username
 * and passowrd provided in request
 *
 */
@Provider
@Stateless
@LocalBean
public class SecurityInterceptor implements javax.ws.rs.container.ContainerRequestFilter {

    @EJB
    private UsuarioFacade usuarioFacade;

    private static final ServerResponse ACCESS_DENIED
            = new ServerResponse("Acesso negado para este recurso.", 401, new Headers<>());
    private static final ServerResponse ACCESS_FORBIDDEN
            = new ServerResponse("Ninguém pode acessar este recurso.", 403, new Headers<>());
    private static final ServerResponse SERVER_ERROR
            = new ServerResponse("INTERNAL SERVER ERROR", 500, new Headers<>());

    @Override
    public void filter(ContainerRequestContext requestContext) {
        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();

        //Access allowed for all 
        if (!method.isAnnotationPresent(PermitAll.class)) {

            try {

                //Access denied for all
                if (method.isAnnotationPresent(DenyAll.class)) {
                    requestContext.abortWith(ACCESS_FORBIDDEN);
                    return;
                }

                //Get request headers
                final MultivaluedMap<String, String> headers = requestContext.getHeaders();

                //Fetch authorization header
                final List<String> authorization = headers.get(ParametroSistema.AUTHORIZATION_PROPERTY);

                //If no authorization information present; block access
                if (authorization == null || authorization.isEmpty()) {
                    requestContext.abortWith(ACCESS_DENIED);
                    return;
                }

                //Get encoded username and password
                final String encodedUserPassword = authorization.get(0).replaceFirst(ParametroSistema.AUTHENTICATION_SCHEME + " ", "");

                //Decode username and password
                String usernameAndPassword = null;
                try {
                    usernameAndPassword = new String(Base64.decode(encodedUserPassword));
                } catch (IOException e) {
                    requestContext.abortWith(SERVER_ERROR);
                    return;
                }

                //Split username and password tokens
                final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
                final String username = tokenizer.nextToken();
                final String password = tokenizer.nextToken();

                Usuario usuario = usuarioFacade.login(username, password);

                if (usuario != null) {
                    if (method.isAnnotationPresent(RolesAllowed.class)) {
                        RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                        Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

                        //Is user valid?
                        for (Perfil perfil : usuario.getPerfis()) {
                            if (rolesSet.contains(perfil.getTipoPerfil().getCodigo())) {
                                return;
                            }
                        }
                    }

                    return;
                }
            } catch (Exception ex) {
                Logger.getLogger(SecurityInterceptor.class.getName()).log(Level.SEVERE, null, ex);
            }

            requestContext.abortWith(ACCESS_DENIED);
        }
    }
}
