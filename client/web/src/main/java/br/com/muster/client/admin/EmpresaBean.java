/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.client.admin;

import br.com.muster.client.AbstractCrudBean;
import br.com.muster.client.Pagina;
import br.com.muster.facade.admin.EmpresaFacade;
import br.com.muster.model.DataTableColumnModel;
import br.com.muster.model.admin.Empresa;
import br.com.muster.model.enums.ESituacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.criterion.Order;

/**
 *
 * @author macorin
 */
@ManagedBean
@ViewScoped
public class EmpresaBean extends AbstractCrudBean<Empresa> implements Serializable {

    private static final long serialVersionUID = -8511664903266496062L;

    private Empresa empresa;

    @EJB
    private EmpresaFacade empresaFacade;

    @Override
    public List<Empresa> getValues() {
        return empresaFacade.selecionarTodos(Order.desc(Empresa.EmpresaColumns.nome));
    }

    @Override
    protected String[] getFieldsListBean() {
        return new String[]{"id", "pessoa.nome"};
    }

    @Override
    public List<DataTableColumnModel> getColumnModel() {
        List<DataTableColumnModel> list = new ArrayList<>();

        list.add(new DataTableColumnModel(getString("empresa_cnpj"), Empresa.EmpresaColumns.cnpj));
        list.add(new DataTableColumnModel(getString("empresa_nome"), Empresa.EmpresaColumns.nome));

        return list;
    }

    @Override
    protected void excluir() throws EJBException {
        empresaFacade.deletar(empresa.getId());
    }
    
    @Override
    protected void salvar() throws EJBException {
        empresaFacade.salvar(this.empresa);
    }

    @Override
    protected List<Empresa> registros() {
        return empresaFacade.selecionarTodos();
    }

    @Override
    protected String getPaginaInicial() {
        return Pagina.EMPRESA_INDEX;
    }

    @Override
    public Empresa getValue() {
        return empresa;
    }

    @Override
    public void setValue(Empresa value) {
        this.empresa = value;
    }
}
