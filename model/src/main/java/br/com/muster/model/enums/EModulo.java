package br.com.muster.model.enums;

import in.macor.commons.iface.IEnums;

/**
 * Created by macorin on 30/06/14.
 */
public enum EModulo implements IEnums<String> {

    ADMIN("ADMIN", "enumeration.modulo.admin"),
    ESTOQUE("ESTOQUE", "enumeration.modulo.estoque"),
    PCM("PCM", "enumeration.modulo.pcm"),
    PESSOA("PESSOA", "enumeration.modulo.pessoa"),
    CONTABILIDADE("CONTABILIDADE", "enumeration.modulo.contabilidade"),
    TIMESHEET("TIMESHEET", "enumeration.modulo.timesheet");

    private final String codigo;
    private final String descricao;

    private EModulo(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }
    
    public static EModulo buscarPorCodigo(String codigo) {
        for (EModulo m : EModulo.values()) {
            if (m.getCodigo().equals(codigo)) {
                return m;
            }
        }
        
        return null;
    }
}
