package br.com.muster.model.usertype;

import in.macor.commons.orm.EnumUserType;
import br.com.muster.model.enums.ESimNao;

public class SimNaoUserType extends EnumUserType<String> {
    @Override
    protected Object getValue(String s) {
        return ESimNao.buscarPorCodigo(s);
    }

    @Override
    public Class returnedClass() {
        return ESimNao.class;
    }
}
