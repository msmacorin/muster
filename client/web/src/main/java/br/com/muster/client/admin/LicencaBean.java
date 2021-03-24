/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.client.admin;

import br.com.muster.client.AbstractCrudBean;
import br.com.muster.client.Pagina;
import br.com.muster.facade.admin.LicencaFacade;
import br.com.muster.model.DataTableColumnModel;
import br.com.muster.model.admin.Licenca;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class LicencaBean extends AbstractCrudBean<Licenca> implements Serializable {

    private static final long serialVersionUID = -6135140318625764653L;

    @EJB
    private LicencaFacade licencaFacade;

    private Licenca licenca;

    @Override
    protected String[] getFieldsListBean() {
        return new String[]{
            Licenca.LicencaColumns.empresa_nome,
            Licenca.LicencaColumns.inicio,
            Licenca.LicencaColumns.prazo
        };
    }

    @Override
    protected void excluir() throws EJBException {
        licencaFacade.deletar(licenca.getId());
    }

    @Override
    protected void salvar() throws EJBException {
        licencaFacade.salvar(licenca);
    }

    @Override
    protected List<Licenca> registros() {
        return licencaFacade.selecionarTodos();
    }

    @Override
    protected String getPaginaInicial() {
        return Pagina.LICENCA_INDEX;
    }

    @Override
    public List<DataTableColumnModel> getColumnModel() {
        List<DataTableColumnModel> list = new ArrayList<>();

        list.add(new DataTableColumnModel(getString("licenca_empresa"), Licenca.LicencaColumns.empresa_nome));
        list.add(new DataTableColumnModel(getString("licenca_inicio"), Licenca.LicencaColumns.inicio));
        list.add(new DataTableColumnModel(getString("licenca_prazo"), Licenca.LicencaColumns.prazo));

        return list;
    }

    @Override
    public Licenca getValue() {
        return licenca;
    }

    @Override
    public void setValue(Licenca value) {
        this.licenca = value;
    }
}
