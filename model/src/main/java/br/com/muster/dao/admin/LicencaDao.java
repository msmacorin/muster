/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.dao.admin;

import br.com.muster.model.admin.Licenca;
import in.macor.core.dao.IDao;
import java.util.List;
import java.util.UUID;
import javax.ejb.Local;

/**
 *
 * @author macorin
 */
@Local
public interface LicencaDao extends IDao<Licenca, UUID>{
    
    /**
     * retorna todas as licencas ativas da empresa.
     * @param idEmpresa
     * @return 
     */
    List<Licenca> licencasAtivasDaEmpresa(UUID idEmpresa);
    
}
