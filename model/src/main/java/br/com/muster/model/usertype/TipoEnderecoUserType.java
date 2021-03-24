/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.model.usertype;

import in.macor.commons.orm.EnumUserType;
import br.com.muster.model.enums.ETipoEndereco;

/**
 *
 * @author macorin
 */
public class TipoEnderecoUserType extends EnumUserType<String>{

    @Override
    protected Object getValue(String codigo) {
        return ETipoEndereco.buscarPorCodigo(codigo);
    }

    @Override
    public Class returnedClass() {
        return ETipoEndereco.class;
    }
    
}
