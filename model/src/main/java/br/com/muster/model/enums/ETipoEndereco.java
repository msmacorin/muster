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
public enum ETipoEndereco implements IEnums<String>{

    Principal("PRI", "Principal");

    private final String codigo;
    private final String descricao;
    
    private ETipoEndereco(String codigo, String descricao) {
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
    
    public static ETipoEndereco buscarPorCodigo(String codigo) {
        for (ETipoEndereco e : ETipoEndereco.values()) {
            if (e.getCodigo().equals(codigo)) {
                return e;
            }
        }
        
        return null;
    }
    
}
