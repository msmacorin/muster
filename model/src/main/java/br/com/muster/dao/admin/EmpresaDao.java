/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.dao.admin;

import in.macor.core.dao.IDao;
import br.com.muster.model.admin.Empresa;
import java.util.UUID;
import javax.ejb.Local;

/**
 *
 * @author macorin
 */
@Local
public interface EmpresaDao extends IDao<Empresa, UUID>{
}
