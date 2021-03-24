/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.model.enums;

import in.macor.commons.iface.IEnums;

/**
 *
 * @author macorin
 */
public enum ESexo implements IEnums<String> {

    MASCULINO("M", "enumeration_sexo_masculino"),
    FEMININO("F", "enumeration_sexo_feminino"),
    NAO_INFORMADO("N", "enumeration_sexo_naoinformado");

    private final String codigo;
    private final String descricao;
    
    private ESexo(String codigo, String descricao) {
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
    
    public static ESexo buscarPorCodigo(String codigo) {
        for (ESexo e : ESexo.values()) {
            if (e.getCodigo().equals(codigo)) {
                return e;
            }
        }
        
        return null;
    }
    
}
