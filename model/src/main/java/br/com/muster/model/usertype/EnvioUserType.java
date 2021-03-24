/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.model.usertype;

import br.com.muster.model.enums.EEnvio;
import in.macor.commons.orm.EnumUserType;

/**
 *
 * @author macorin
 */
public class EnvioUserType extends EnumUserType<Integer>{

    @Override
    protected Object getValue(Integer codigo) {
        return EEnvio.buscarPorCodigo(codigo);
    }

    @Override
    public Class returnedClass() {
        return EEnvio.class;
    }
    
}
