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
public enum ESituacao implements IEnums<Integer> {

    Ativo(1, "enumeration.situacao.ativo"),
    Suspenso(2, "enumeration.situacao.suspenso"),
    Excluido(3, "enumeration.situacao.excluido"),
    Finalizado(4, "enumeration.situacao.finalizado");
    
    private final Integer codigo;
    private final String descricao;

    private ESituacao(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    @Override
    public Integer getCodigo() {
        return codigo;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }
    
    public static ESituacao buscarPorCodigo(Integer codigo){
        for (ESituacao situacao : ESituacao.values()) {
            if (situacao.getCodigo().equals(codigo)) return situacao;
        }
        
        return null;
    }
}
