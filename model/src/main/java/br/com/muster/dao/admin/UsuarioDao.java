/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.dao.admin;

import in.macor.core.dao.IDao;
import br.com.muster.model.admin.Usuario;
import java.util.List;
import java.util.UUID;
import javax.ejb.Local;

/**
 *
 * @author macorin
 */
@Local
public interface UsuarioDao extends IDao<Usuario, UUID>{
    
    /**
     * Retorna o registro pelo nome de usuario.
     * @param login
     * @return 
     */
    Usuario buscarPorLogin(String login);
    
    /**
     * retorna lista de usuarios com perfis diferentes de admin e super.
     * @return 
     */
    List<Usuario> selecionarUsuariosSimples();

    /**
     * retorna os usuarios ativos vinculados a uma empresa.
     * @param idEmpresa
     * @return 
     */
    List<Usuario> selecionarUsuariosPorEmpresa(UUID idEmpresa);
}
