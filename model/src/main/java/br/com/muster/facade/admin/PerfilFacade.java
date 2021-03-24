/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.admin;

import in.macor.core.facade.IFacade;
import br.com.muster.model.admin.Perfil;
import br.com.muster.model.enums.ETipoPerfil;
import java.util.UUID;
import javax.ejb.Local;

/**
 *
 * @author macorin
 */
@Local
public interface PerfilFacade extends IFacade<Perfil, UUID>{
    
    Perfil buscarPerfilPorTipo(ETipoPerfil tipoPerfil);
}
