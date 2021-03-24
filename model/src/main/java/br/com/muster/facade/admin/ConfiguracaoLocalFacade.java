/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.admin;

import br.com.muster.model.admin.ConfiguracaoLocal;
import br.com.muster.model.admin.Empresa;
import br.com.muster.model.admin.Usuario;
import in.macor.core.facade.IFacade;
import javax.ejb.Local;

/**
 *
 * @author macorin
 */
@Local
public interface ConfiguracaoLocalFacade extends IFacade<ConfiguracaoLocal, Long>{
    
    Usuario getUsuarioLocal();
    Empresa getEmpresaLocal();
    String getUrlApi();
    String getEmailAviso();
    boolean executarServicos();
}
