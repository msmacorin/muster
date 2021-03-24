/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.local.ejb;

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.sql.DataSource;

/**
 *
 * @author macorin
 */
@Stateful
public class ConnectionFactory {

    @Resource(mappedName = "java:/OracleDS")
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }
    
}
