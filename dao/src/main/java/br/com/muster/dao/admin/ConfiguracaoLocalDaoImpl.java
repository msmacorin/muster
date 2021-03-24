/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.dao.admin;

import br.com.muster.dao.Dao;
import br.com.muster.model.admin.ConfiguracaoLocal;
import javax.ejb.Stateless;

/**
 *
 * @author macorin
 */
@Stateless
public class ConfiguracaoLocalDaoImpl extends Dao<ConfiguracaoLocal, Long> implements ConfiguracaoLocalDao{
    private static final long serialVersionUID = 4139904591719963745L;
    
}
