/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.client;

import br.com.muster.facade.admin.UsuarioFacade;
import br.com.muster.model.admin.Perfil;
import br.com.muster.model.admin.Usuario;
import in.macor.commons.util.SecurityUtil;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 *
 * @author macorin
 */
@Stateless
@EJB(name = "authenticationProvider", beanInterface = UsuarioFacade.class)
public class MusterAuthenticationProvider implements AuthenticationProvider {

    private UsuarioFacade usuarioFacade;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        try {
            Usuario usuario = doLogin(name, password);
            Collection<GrantedAuthority> roles = new ArrayList<>();

            for (Perfil perfil : usuario.getPerfis()) {
                roles.add(new SimpleGrantedAuthority(perfil.getTipoPerfil().getCodigo()));
            }

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(usuario.getLogin(), null, roles);
            token.setDetails(usuario);

            return new UsernamePasswordAuthenticationToken(name, password, roles);
        } catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication)
                && authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private Usuario doLogin(String login, String password) throws NamingException, Exception {
        InitialContext initialContext = new InitialContext();
        usuarioFacade = (UsuarioFacade) initialContext.lookup("java:comp/env/authenticationProvider");

        return usuarioFacade.login(login, SecurityUtil.encrypt(password));
    }
}
