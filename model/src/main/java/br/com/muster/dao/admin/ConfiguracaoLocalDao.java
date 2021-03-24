/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.dao.admin;

import br.com.muster.model.admin.ConfiguracaoLocal;
import in.macor.core.dao.IDao;
import javax.ejb.Local;

/**
 *
 * @author macorin
 */
@Local
public interface ConfiguracaoLocalDao extends IDao<ConfiguracaoLocal, Long>{
}
