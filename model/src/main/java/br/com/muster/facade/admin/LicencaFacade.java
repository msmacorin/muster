/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.admin;

import br.com.muster.model.admin.Licenca;
import in.macor.core.facade.IFacade;
import java.util.List;
import java.util.UUID;
import javax.ejb.Local;
import javax.validation.ValidationException;

/**
 *
 * @author macorin
 */
@Local
public interface LicencaFacade extends IFacade<Licenca, UUID>{

    /**
     * valida uma licenca de acordo com a data de inicio, prazo e hash de validação.
     * @param licenca
     * @return 
     */
    boolean validarLicenca(Licenca licenca) throws ValidationException;
    
    /**
     * retorna todas as licencas ativas de uma determinada empresa.
     * @param idEmpresa
     * @return 
     */
    List<Licenca> licencasAtivasDaEmpresa(UUID idEmpresa);
    
}
