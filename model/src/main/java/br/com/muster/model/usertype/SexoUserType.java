/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.model.usertype;

import in.macor.commons.orm.EnumUserType;
import br.com.muster.model.enums.ESexo;

/**
 *
 * @author macorin
 */
public class SexoUserType extends EnumUserType<String> {

    @Override
    protected Object getValue(String codigo) {
        return ESexo.buscarPorCodigo(codigo);
    }

    @Override
    public Class returnedClass() {
        return ESexo.class;
    }
    
}
