package br.com.muster.model.enums;

import in.macor.commons.iface.IEnums;

public enum ETipoPessoa implements IEnums<String> {

    FISICA("FIS", "enumeration.tipopessoa.fisica"),
    JURIDICA("JUR", "enumeration.tipopessoa.juridica"),
    OUTROS("OUT", "enumeration.tipopessoa.outros");

    private final String codigo;
    private final String descricao;

    ETipoPessoa(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    @Override
    public String getCodigo() {
        return this.codigo;
    }

    @Override
    public String getDescricao() {
        return this.descricao;
    }

    public static ETipoPessoa buscarPorCodigo(String codigo) {
        for (ETipoPessoa tipo : ETipoPessoa.values()) {
            if (tipo.getCodigo().equals(codigo)) {
                return tipo;
            }
        }

        return null;
    }
}
