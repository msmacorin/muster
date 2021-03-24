/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.local.admin;

import br.com.muster.local.Pagina;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author macorin
 */
@ManagedBean
@RequestScoped
public class RequestBean implements Serializable {
    private static final long serialVersionUID = -1891836229729930715L;

    public String getConfiguracaoLocalIndex() {
        return Pagina.CONFIGURACAO_LOCAL;
    }
    
    public String getUsuarioLocalIndex() {
        return Pagina.USUARIO_LOCAL;
    }
    
    public String getStatusServicoIndex() {
        return Pagina.STATUS_SERVICO;
    }
}
