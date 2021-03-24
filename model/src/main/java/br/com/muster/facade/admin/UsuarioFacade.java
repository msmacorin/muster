/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.admin;

import in.macor.core.facade.IFacade;
import br.com.muster.model.admin.Usuario;
import java.util.List;
import java.util.UUID;
import javax.ejb.Local;

/**
 *
 * @author macorin
 */
@Local
public interface UsuarioFacade extends IFacade<Usuario, UUID>{
    
    /**
     * realiza o login do usuario no sistema validando usuario e senha
     * caso correto, retorna o usuario.
     * @param usuario
     * @param senha
     * @return 
     * @throws java.lang.Exception 
     */
    Usuario login(String usuario, String senha) throws Exception ;
    
    /**
     * busca o usuario no banco pelo login
     * @param login
     * @return
     * @throws Exception 
     */
    Usuario buscarPorLogin(String login) throws Exception;
    
    /**
     * busca os usuarios na API disponiveis para mapeamento local,
     * usuarios apenas com o perfil de usuario.
     * @param usuarioLogado usuario logado no sistema
     * @return
     * @throws Exception 
     */
    List<Usuario> getUsuariosSimplesRemoto(Usuario usuarioLogado) throws Exception;
    
    /**
     * busca usuarios com perfil diferentes de super ou admin.
     * @return
     * @throws Exception 
     */
    List<Usuario> getUsuariosSimples() throws Exception;
    
    /**
     * retorna usuarios vinculados a empresa.
     * @param idEmpresa
     * @return 
     */
    List<Usuario> getUsuariosDaEmpresa(UUID idEmpresa);
    
    /**
     * busca na API os usuarios vinculados a empresa
     * @param usuarioLogado
     * @param idEmpresa
     * @return 
     */
    List<Usuario> getUsuariosDaEmpresaRemoto(Usuario usuarioLogado, UUID idEmpresa);
    
    /**
     * retorna um usuario utilizado para configurar a base de dados local.
     * @return 
     */
    Usuario usuarioLocal();
    
    /**
     * busca na API o usuario remoto para configurar a base de dados local.
     * @param usuarioLogado
     * @return 
     */
    Usuario usuarioLocalRemoto(Usuario usuarioLogado);
}
