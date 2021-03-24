package br.com.muster.model.usertype;

import in.macor.commons.orm.EnumUserType;
import br.com.muster.model.enums.ETipoPessoa;

public class TipoPessoaUserType extends EnumUserType<String> {
    @Override
    protected Object getValue(String s) {
        return ETipoPessoa.buscarPorCodigo(s);
    }

    @Override
    public Class returnedClass() {
        return ETipoPessoa.class;
    }
}
