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
public enum EEnvio implements IEnums<Integer> {

    Pendente(0, ""),
    Enviado(1, ""),
    Recebido(2, "");

    private int codigo;
    private String descricao;
    
    private EEnvio(Integer codigo, String descricao) {
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
    
    public static EEnvio buscarPorCodigo(Integer codigo) {
        for (EEnvio e : EEnvio.values()) {
            if (e.getCodigo().equals(codigo)) {
                return e;
            }
        }
        
        return null;
    }
}
