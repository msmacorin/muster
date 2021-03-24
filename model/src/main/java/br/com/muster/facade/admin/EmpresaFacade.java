/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.admin;

import in.macor.core.facade.IFacade;
import br.com.muster.model.admin.Empresa;
import br.com.muster.model.admin.Usuario;
import java.util.List;
import java.util.UUID;
import javax.ejb.Local;

/**
 *
 * @author macorin
 */
@Local
public interface EmpresaFacade extends IFacade<Empresa, UUID>{
    
    List<Empresa> empresasLicenciadas();
    List<Empresa> empresasLicenciadasRemoto(Usuario usuarioLogado);
    
}
