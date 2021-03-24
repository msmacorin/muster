package br.com.muster.local.admin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.muster.local.AbstractBean;
import br.com.muster.local.Pagina;
import in.macor.commons.util.StringUtil;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author macorin
 */
@ManagedBean
@RequestScoped
public class LoginBean extends AbstractBean {

    private String usuario;
    private String senha;

    @ManagedProperty(value = "#{authenticationManager}")
    private AuthenticationManager authenticationManager;

    public String login() {
        try {
            try {
                Authentication request = new UsernamePasswordAuthenticationToken(this.getUsuario(), this.getSenha());
                Authentication result = authenticationManager.authenticate(request);
                SecurityContextHolder.getContext().setAuthentication(result);
            } catch (AuthenticationException e) {
                logger.error(e.getMessage());
                throw new Exception(e);
            }
            
            return Pagina.HOME;
        } catch (Exception e) {
            String msg = StringUtil.empty(e.getMessage()) ? "Erro no login!" : e.getMessage();
            logger.error(msg, e);
            messages(new FacesMessage(msg));
        }

        return "";
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        SecurityContextHolder.clearContext();

        return redirect(Pagina.LOGIN);
    }

    // gets and sets
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
}
