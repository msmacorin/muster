/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.client.admin;

import br.com.muster.client.AbstractCrudBean;
import br.com.muster.client.Pagina;
import br.com.muster.model.DataTableColumnModel;
import br.com.muster.model.admin.Empresa;
import br.com.muster.model.admin.ParametroSistema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author macorin
 */
@ManagedBean(name = "empresaSelecionada")
@RequestScoped
public class EmpresaSelecionadaBean extends AbstractCrudBean<Empresa> implements Serializable {
    private static final long serialVersionUID = 8588054731538336154L;

    private Empresa empresa;
    private List<Empresa> empresasUsuario;

    @PostConstruct
    public void init() {
        empresa = session.getObjectSession(ParametroSistema.EMPRESA_SESSION);

        if (session.usuarioPossuiEmpresa()) {
            if (session.getUsuario().getEmpresas().size() == 1) {
                empresa = session.getUsuario().getEmpresas().iterator().next();
                session.setObjectSession(ParametroSistema.EMPRESA_SESSION, empresa);
            }

            empresasUsuario = new ArrayList<>(session.getUsuario().getEmpresas());
        } else {
            empresasUsuario = new ArrayList<>();
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
    }

    @Override
    protected List<Empresa> registros() {
        return null;
    }

    @Override
    protected String getPaginaInicial() {
        return null;
    }

    @Override
    public List<DataTableColumnModel> getColumnModel() {
        return null;
    }

    @Override
    public Empresa getValue() {
        return empresa;
    }

    @Override
    public void setValue(Empresa value) {
        this.empresa = value;
    }

    public String getNomeEmpresaSelecionada() {
        if (empresa == null) {
            return getString("selecionar_empresa_header");
        }

        return empresa.getNome();
    }

    public List<Empresa> getEmpresasUsuario() {
        return empresasUsuario;
    }

    public void setEmpresasUsuario(List<Empresa> empresasUsuario) {
        this.empresasUsuario = empresasUsuario;
    }
    
    public boolean necessitaSelecionarEmpresa() {
        return empresa == null && session.usuarioPossuiEmpresa();
    }
    
    public String index() {
        if (empresasUsuario.size() > 1) {
            return forward(Pagina.SELECIONAR_EMPRESA);
        }
        
        return "";
    }

    public void gravarEmpresa() {
        try {
            session.setObjectSession(ParametroSistema.EMPRESA_SESSION, empresa);
            messages(new FacesMessage(getString("selecionar_empresa_sucesso")));
        } catch (Exception e) {
            logger.error("Erro ao incluir empresa na sessão", e);
            messages(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    getString("selecionar_empresa_erro"),
                    e.getMessage()));
        }
    }
}
