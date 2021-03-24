/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.model.enums;

import in.macor.commons.iface.IEnums;

/**
 *
 * @author macorin
 */
public enum ESimNao implements IEnums<String> {

    Sim("S", "global.sim"),
    Nao("N", "global.nao");

    private String descricao;

    private String codigo;

    private ESimNao(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static ESimNao buscarPorCodigo(String codigo) {
        for (ESimNao e : ESimNao.values()) {
            if (e.getCodigo().equals(codigo)) {
                return e;
            }
        }

        return null;
    }
}
