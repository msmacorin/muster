/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.client.admin;

import br.com.muster.client.AbstractCrudBean;
import br.com.muster.client.Pagina;
import br.com.muster.facade.admin.PerfilFacade;
import br.com.muster.model.DataTableColumnModel;
import br.com.muster.model.admin.Perfil;
import br.com.muster.model.enums.ETipoPerfil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class PerfilBean extends AbstractCrudBean<Perfil> implements Serializable {
    
    private static final long serialVersionUID = -3051353486758508150L;

    @EJB
    private PerfilFacade perfilFacade;

    private Perfil perfil;

    private Map<String, ETipoPerfil> tiposPerfil;

    @PostConstruct
    public void init() {
        tiposPerfil = doSelectOneMenu(ETipoPerfil.values());
        
        //TODO: implementar converter de tipo perfil ?
        // tem q instanciar aqui senão da erro no selectOneMenu de TipoPerfil
        perfil = new Perfil();
    }

    @Override
    protected String[] getFieldsListBean() {
        return new String[]{
            Perfil.PerfilColumns.nome,
            Perfil.PerfilColumns.tipoPerfil
        };
    }

    @Override
    protected void excluir() throws EJBException {
        perfilFacade.deletar(perfil.getId());
    }

    @Override
    protected void salvar() throws EJBException {
        perfilFacade.salvar(perfil);
    }

    @Override
    protected List<Perfil> registros() {
        return perfilFacade.selecionarTodos();
    }

    @Override
    protected String getPaginaInicial() {
        return Pagina.PERFIL_INDEX;
    }

    @Override
    public List<DataTableColumnModel> getColumnModel() {
        List<DataTableColumnModel> model = new ArrayList<>();

        model.add(new DataTableColumnModel(getString("perfil_nome"), Perfil.PerfilColumns.nome));
        model.add(new DataTableColumnModel(getString("perfil_tipo_perfil"), Perfil.PerfilColumns.tipoPerfil));

        return model;
    }

    @Override
    public Perfil getValue() {
        return perfil;
    }

    @Override
    public void setValue(Perfil value) {
        this.perfil = value;
    }

    public Map<String, ETipoPerfil> getTiposPerfil() {
        return tiposPerfil;
    }

    public void setTiposPerfil(Map<String, ETipoPerfil> tiposPerfil) {
        this.tiposPerfil = tiposPerfil;
    }
}
