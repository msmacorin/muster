/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.client.admin;

import br.com.muster.client.AbstractCrudBean;
import br.com.muster.client.Pagina;
import br.com.muster.facade.admin.EmpresaFacade;
import br.com.muster.facade.admin.PerfilFacade;
import br.com.muster.facade.admin.UsuarioFacade;
import br.com.muster.model.DataTableColumnModel;
import br.com.muster.model.admin.Empresa;
import br.com.muster.model.admin.Perfil;
import br.com.muster.model.admin.Usuario;
import in.macor.commons.util.SecurityUtil;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.criterion.Order;
import org.primefaces.model.DualListModel;

/**
 *
 * @author macorin
 */
@ManagedBean
@ViewScoped
public class UsuarioBean extends AbstractCrudBean<Usuario> implements Serializable {

    private static final long serialVersionUID = -4631940619451983946L;

    private Usuario usuario;

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private PerfilFacade perfilFacade;

    @EJB
    private EmpresaFacade empresaFacade;

    private DualListModel<Perfil> perfis;

    private DualListModel<Empresa> empresas;

    @PostConstruct
    public void init() {
        perfis = new DualListModel<>();
        empresas = new DualListModel<>();
    }

    @Override
    protected String[] getFieldsListBean() {
        return new String[]{Usuario.UsuarioColumns.nome, Usuario.UsuarioColumns.email};
    }

    @Override
    protected void excluir() throws EJBException {
        usuarioFacade.deletar(usuario.getId());
    }

    @Override
    protected void salvar() throws EJBException {
        try {
            usuario.setEmpresas(new LinkedHashSet<Empresa>());
            usuario.getEmpresas().addAll(empresas.getTarget());

            usuario.setPerfis(new LinkedHashSet<Perfil>());
            usuario.getPerfis().addAll(perfis.getTarget());

            if (usuario.getId() == null) {
                usuario.setSenha(SecurityUtil.encrypt(usuario.getSenha()));
            }

            usuarioFacade.salvar(usuario);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            logger.debug("Erro ao encriptar a senha de usuário.", ex);
        }
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
        return Pagina.USUARIO_INDEX;
    }

    @Override
    public List<DataTableColumnModel> getColumnModel() {
        List<DataTableColumnModel> dataTableModel = new ArrayList<>();

        dataTableModel.add(new DataTableColumnModel(getString("usuario_nome"), Usuario.UsuarioColumns.nome));
        dataTableModel.add(new DataTableColumnModel(getString("usuario_email"), Usuario.UsuarioColumns.email));

        return dataTableModel;
    }

    @Override
    public Usuario getValue() {
        return usuario;
    }

    @Override
    public void setValue(Usuario value) {
        this.usuario = value;

        perfis = new DualListModel<>();
        perfis.setSource(perfilFacade.selecionarTodos(Order.asc(Perfil.PerfilColumns.nome)));

        empresas = new DualListModel<>();
        empresas.setSource(empresaFacade.selecionarTodos(Order.asc(Empresa.EmpresaColumns.nome)));

        if (usuario != null) {
            if (usuario.getPerfis() != null) {
                perfis.setTarget(new ArrayList<>(usuario.getPerfis()));
                perfis.getSource().removeAll(perfis.getTarget());
            }

            if (usuario.getEmpresas() != null) {
                empresas.setTarget(new ArrayList<>(usuario.getEmpresas()));
                empresas.getSource().removeAll(empresas.getTarget());
            }
        }
    }

    public DualListModel<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(DualListModel<Perfil> perfis) {
        this.perfis = perfis;
    }

    public DualListModel<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(DualListModel<Empresa> empresas) {
        this.empresas = empresas;
    }
}
