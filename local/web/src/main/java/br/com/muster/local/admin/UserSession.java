/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.local.admin;

import br.com.muster.facade.admin.UsuarioFacade;
import br.com.muster.model.admin.Empresa;
import br.com.muster.model.admin.Perfil;
import br.com.muster.model.admin.Usuario;
import br.com.muster.model.enums.ETipoPerfil;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author macorin
 */
@Stateless
@EJB(name = "userSessionEJB", beanInterface = UsuarioFacade.class)
@ManagedBean(name = "userSession")
@SessionScoped
public class UserSession implements Serializable {

    private static final long serialVersionUID = 8446987049615865906L;
    public static final String INJECTION_NAME = "#{userSession}";

    private Usuario usuario;

    private UsuarioFacade usuarioFacade;

    public UserSession() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context instanceof SecurityContext) {
            Authentication authentication = context.getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                try {
                    if (usuario == null) {
                        usuario = getUsuario(authentication.getPrincipal().toString());
                    }
                } catch (Exception ex) {
                    Logger.getLogger(UserSession.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean usuarioLogado() {
        return usuario != null;
    }

    public boolean superUsuario() {
        return validaTipoUsuarioLogado(ETipoPerfil.Super);
    }

    public boolean adminUsuario() {
        return validaTipoUsuarioLogado(ETipoPerfil.Administrador);
    }
    
    public boolean localUsuario() {
        return validaTipoUsuarioLogado(ETipoPerfil.Local);
    }
    
    private boolean validaTipoUsuarioLogado(ETipoPerfil tipoPerfil) {
        if (usuarioLogado()) {
            for (Perfil p : usuario.getPerfis()) {
                if (tipoPerfil.equals(p.getTipoPerfil())) {
                    return true;
                }
            }
        }

        return false;
    }

    private Usuario getUsuario(String login) throws NamingException, Exception {
        InitialContext initialContext = new InitialContext();
        usuarioFacade = (UsuarioFacade) initialContext.lookup("java:comp/env/userSessionEJB");

        return usuarioFacade.buscarPorLogin(login);
    }
}
