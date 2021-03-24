/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.client.admin;

import br.com.muster.client.AbstractCrudBean;
import br.com.muster.client.Pagina;
import br.com.muster.facade.admin.UsuarioFacade;
import br.com.muster.model.DataTableColumnModel;
import br.com.muster.model.admin.Usuario;
import in.macor.commons.util.SecurityUtil;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.criterion.Order;

/**
 *
 * @author macorin
 */
@ManagedBean
@ViewScoped
public class PerfilUsuarioBean extends AbstractCrudBean<Usuario> implements Serializable {

    private static final long serialVersionUID = -4631940619451983946L;

    private Usuario usuario;

    @EJB
    private UsuarioFacade usuarioFacade;
    
    @PostConstruct
    public void init() {
        usuario = session.getUsuario();
    }

    @Override
    protected String[] getFieldsListBean() {
        return null;
    }

    @Override
    protected void excluir() throws EJBException {
    }

    @Override
    protected void salvar() throws EJBException {
        usuarioFacade.salvar(usuario);
    }

    public String alterarSenha() {
        try {
            usuario.setSenha(SecurityUtil.encrypt(usuario.getSenha()));
            usuarioFacade.salvar(usuario);

            messages(new FacesMessage(getString("alteracao_senha_sucesso")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            logger.debug("Erro ao encriptar a senha de usuário.", ex);
            messages(new FacesMessage(getString("alteracao_senha_erro")));
        }

        return getPaginaInicial();
    }

    @Override
    protected List<Usuario> registros() {
        return usuarioFacade.selecionarTodos(Order.asc(Usuario.UsuarioColumns.nome));
    }

    @Override
    protected String getPaginaInicial() {
        return Pagina.PERFIL;
    }

    @Override
    public List<DataTableColumnModel> getColumnModel() {
        return null;
    }

    @Override
    public Usuario getValue() {
        return usuario;
    }

    @Override
    public void setValue(Usuario value) {
        this.usuario = value;
    }
}
