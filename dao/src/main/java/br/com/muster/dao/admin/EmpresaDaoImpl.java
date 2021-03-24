/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.dao.admin;

import br.com.muster.dao.Dao;
import br.com.muster.model.admin.Empresa;
import java.util.UUID;
import javax.ejb.Stateless;

/**
 *
 * @author macorin
 */
@Stateless
public class EmpresaDaoImpl extends Dao<Empresa, UUID> implements EmpresaDao {

    private static final long serialVersionUID = 5823000338942532769L;
}
