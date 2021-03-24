/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.model.usertype;

import in.macor.commons.orm.EnumUserType;
import br.com.muster.model.enums.ESituacao;

/**
 *
 * @author macorin
 */
public class SituacaoUserType extends EnumUserType<Integer> {

    @Override
    protected Object getValue(Integer codigo) {
        return ESituacao.buscarPorCodigo(codigo);
    }

    @Override
    public Class<ESituacao> returnedClass() {
        return ESituacao.class;
    }
    
}
