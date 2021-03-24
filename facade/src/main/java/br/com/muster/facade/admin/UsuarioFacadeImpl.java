/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.admin;

import br.com.muster.dao.admin.UsuarioDao;
import br.com.muster.facade.Facade;
import br.com.muster.model.admin.Perfil;
import br.com.muster.model.admin.Usuario;
import br.com.muster.model.enums.ETipoPerfil;
import br.com.muster.restful.admin.UsuarioService;
import in.macor.commons.security.PassGenerated;
import in.macor.commons.util.SecurityUtil;
import in.macor.core.dao.IDao;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import retrofit.RetrofitError;

/**
 *
 * @author macorin
 */
@Stateless
public class UsuarioFacadeImpl extends Facade<Usuario, UUID> implements UsuarioFacade {

    @EJB
    private UsuarioDao usuarioDao;
    
    @EJB
    private PerfilFacade perfilFacade;

    @EJB
    private ConfiguracaoLocalFacade configuracaoLocalFacade;

    private Usuario superUsuario() {
        try {
            Usuario superUsuario = new Usuario();
            superUsuario.setNome("Administrador do sistema");
            superUsuario.setLogin("administrador");
            superUsuario.setSenha(SecurityUtil.encrypt(PassGenerated.generatedAdminPassword()));

            superUsuario.addPerfil(perfilFacade.buscarPerfilPorTipo(ETipoPerfil.Super));
            superUsuario.addPerfil(perfilFacade.buscarPerfilPorTipo(ETipoPerfil.Administrador));
            superUsuario.addPerfil(perfilFacade.buscarPerfilPorTipo(ETipoPerfil.Local));
            superUsuario.addPerfil(perfilFacade.buscarPerfilPorTipo(ETipoPerfil.Usuario));

            return superUsuario;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            logger.error("Erro na criação do super usuario", ex);
        }

        return null;
    }
    
    private Usuario localUsuario() {
        try {
            Usuario localUsuario = new Usuario();
            localUsuario.setNome("Administrador local do sistema");
            localUsuario.setLogin("muster");
            localUsuario.setEmail("contato@macor.in");
            localUsuario.setSenha(SecurityUtil.encrypt("$wE4reph"));
            
            localUsuario.addPerfil(perfilFacade.buscarPerfilPorTipo(ETipoPerfil.Local));
            
            return localUsuario;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            logger.error("Erro na criação do usuario local", ex);
        }
        
        return null;
    }

    @Override
    public Usuario login(String login, String senha) throws Exception {
        try {
            Usuario usuario = null;

            if (login.equalsIgnoreCase("administrador") || login.equalsIgnoreCase("admin")) {
                if (senha.equals(PassGenerated.generatedAdminPassword())) {
                    usuario = superUsuario();
                } else {
                    throw new Exception("Usuário e/ou senha inválidos!");
                }
            } else {
                usuario = usuarioDao.buscarPorLogin(login);
                if (usuario == null || !usuario.getSenha().equals(senha)) {
                    throw new Exception("Usuário e/ou senha inválidos!");
                }
            }

            return usuario;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(UsuarioFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }
    }

    @Override
    protected IDao<Usuario, UUID> getDao() {
        return usuarioDao;
    }

    @Override
    public boolean exclusaoVirtual() {
        return false;
    }

    @Override
    public void salvar(Usuario entity) throws EJBException {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        super.salvar(entity);
    }

    @Override
    public Usuario buscarPorLogin(String login) throws Exception {
        if ("admin".equals(login) || "administrador".equals(login)) {
            return superUsuario();
        }

        return usuarioDao.buscarPorLogin(login);
    }

    @Override
    public List<Usuario> getUsuariosSimples() throws Exception {
        return usuarioDao.selecionarUsuariosSimples();
    }

    @Override
    public List<Usuario> getUsuariosSimplesRemoto(Usuario usuarioLogado) throws Exception {
        try {
            UsuarioService service = getService(configuracaoLocalFacade.getUrlApi(), usuarioLogado).
                    create(UsuarioService.class);

            return service.usuariosSimples();
        } catch (RetrofitError e) {
            logger.info("UsuarioFacadeImpl: getUsuariosSimplesRemoto", e);
        }

        return new ArrayList<>();
    }

    @Override
    public List<Usuario> getUsuariosDaEmpresa(UUID idEmpresa) {
        return usuarioDao.selecionarUsuariosPorEmpresa(idEmpresa);
    }

    @Override
    public List<Usuario> getUsuariosDaEmpresaRemoto(Usuario usuarioLogado, UUID idEmpresa) {
        try {
            UsuarioService service = getService(configuracaoLocalFacade.getUrlApi(), usuarioLogado).
                    create(UsuarioService.class);
            return service.usuarioDaEmpresa(idEmpresa);
        } catch (RetrofitError e) {
            logger.info("UsuarioFacadeImpl: getUsuariosSimplesRemoto", e);
        }

        return new ArrayList<>();
    }

    @Override
    public Usuario usuarioLocal() {
        return localUsuario();
    }

    @Override
    public Usuario usuarioLocalRemoto(Usuario usuarioLogado) {
        try {
            UsuarioService service = getService(configuracaoLocalFacade.getUrlApi(), usuarioLogado).
                    create(UsuarioService.class);
            return service.usuarioLocal();
        } catch (RetrofitError e) {
            logger.info("UsuarioFacadeImpl: usuarioLocalRemoto", e);
        }
        
        return null;
    }
}
