/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.dao.admin;

import br.com.muster.dao.Dao;
import br.com.muster.model.admin.Usuario;
import br.com.muster.model.enums.ETipoPerfil;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;

/**
 *
 * @author macorin
 */
@Stateless
public class UsuarioDaoImpl extends Dao<Usuario, UUID> implements UsuarioDao {

    private static final long serialVersionUID = -3005609090603706936L;
    private static final String FIND_BY_USER = "SELECT u "
            + "FROM Usuario u "
            + "JOIN FETCH u.perfis p "
            + "WHERE u.login = :login";
    
    private static final String SELECT_SIMPLE = "SELECT u "
            + "FROM Usuario u "
            + "JOIN FETCH u.perfis p "
            + "JOIN FETCH u.empresas e "
            + "WHERE p.tipoPerfil NOT IN (:perfis) "
            + "ORDER BY u.nome ";
    
    private final String SELECT_EMPRESA="SELECT u "
            + "FROM Usuario u "
            + "JOIN FETCH u.empresas e "
            + "WHERE e.id = :idEmpresa "
            + "ORDER BY u.nome ";

    @Override
    public Usuario buscarPorLogin(String login) {
        try {
            params.put("login", login);
            return find(FIND_BY_USER);
        } finally {
            params.clear();
        }
    }

    @Override
    public List<Usuario> selecionarUsuariosSimples() {
        try {
            List<ETipoPerfil> perfis = new ArrayList<>();
            perfis.add(ETipoPerfil.Super);
            perfis.add(ETipoPerfil.Administrador);
            
            params.put("perfis", perfis);
            
            return select(SELECT_SIMPLE);
        } finally {
            params.clear();
        }
    }

    @Override
    public List<Usuario> selecionarUsuariosPorEmpresa(UUID idEmpresa) {
        try {
            params.put("idEmpresa", idEmpresa);
            return select(SELECT_EMPRESA);
        } finally {
            params.clear();
        }
    }

}
