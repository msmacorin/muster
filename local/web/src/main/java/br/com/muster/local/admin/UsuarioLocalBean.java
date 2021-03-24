/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.local.admin;

import br.com.muster.facade.admin.UsuarioFacade;
import br.com.muster.local.AbstractCrudBean;
import br.com.muster.local.Pagina;
import br.com.muster.model.DataTableColumnModel;
import br.com.muster.model.admin.Usuario;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author macorin
 */
@ManagedBean
@ViewScoped
public class UsuarioLocalBean extends AbstractCrudBean<Usuario> {

    private Usuario usuario;
    
    @EJB
    private UsuarioFacade usuarioFacade;
    
    @PostConstruct
    public void init () {
        try {
            usuario = usuarioFacade.buscarPorLogin("muster");
        } catch (Exception ex) {
            logger.error("UsuarioLocalBean: erro ao buscar usuario local.", ex);
        }
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

    @Override
    protected List<Usuario> registros() {
        return null;
    }

    @Override
    protected String getPaginaInicial() {
        return Pagina.USUARIO_LOCAL;
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
    
    public void buscarUsuarioLocal() {
        setValue(usuarioFacade.usuarioLocalRemoto(session.getUsuario()));
    }
    
}
