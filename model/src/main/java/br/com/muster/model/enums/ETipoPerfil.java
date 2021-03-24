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
public enum ETipoPerfil implements IEnums<String> {

    Super("ROLE_SUPER", "enumeration_tipoperfil_super"),
    Administrador("ROLE_ADMIN", "enumeration_tipoperfil_administrador"),
    Usuario("ROLE_USER", "enumeration_tipoperfil_usuario"),
    Local("ROLE_LOCAL", "enumeration_tipoperfil_local");
            
    private final String codigo;
    private final String descricao;

    private ETipoPerfil(String codigo, String descricao) {
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
    
    public static ETipoPerfil buscarPorCodigo(String codigo) {
        for (ETipoPerfil e : ETipoPerfil.values()) {
            if (e.getCodigo().equals(codigo)) {
                return e;
            }
        }
        
        return null;
    }

}
