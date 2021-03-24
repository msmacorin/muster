/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.client;

import br.com.muster.model.DataTableColumnModel;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;

/**
 *
 * @author macorin
 * @param <T>
 */
public abstract class AbstractCrudBean<T> extends AbstractBean {

//    private LazyDataModel<T> values;
    private List<T> values;

    private final Class<T> objectClass = (Class<T>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];

    /**
     * preenche o listBean com o resultado do select paginado com os campos de
     * filtro por coluna da tela.
     *
     * @return
     */
//    public LazyDataModel<T> getValues() {
//        values = new LazyDataModel<T>() {
//            private static final long serialVersionUID = 5340099026592784595L;
//
//            @Override
//            public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
//                List<T> l = getFacade().selecionarListBean(
//                        getFieldsListBean(),
//                        first,
//                        pageSize,
//                        sortField,
//                        (SortOrder.DESCENDING.equals(sortOrder) ? " DESC " : " ASC "),
//                        filters);
//
//                values.setRowCount(l.size());
//                return l;
//            }
//
//        };
//
//        return values;
//    }
    public List<T> getValues() {
        if (values == null) {
            values = this.registros();
        }

        return values;
    }

    public void prepareInsert() {
        try {
            this.setValue(objectClass.newInstance());
        } catch (InstantiationException | IllegalAccessException ex) {
            logger.error("Erro ao tentar inserir: " + objectClass.getCanonicalName(), ex);
            messages(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    getString("inserir_mensagem_erro"),
                    ex.getMessage()));
        }
    }

    /**
     * metodo utilizado para deletar registro na lista de crud
     *
     */
    public void delete() {
        try {
            this.excluir();
            values = this.registros();
            messages(new FacesMessage(getString("exclusao_mensagem_sucesso")));
        } catch (EJBException e) {
            logger.error("Erro ao remover o registro: " + objectClass.getCanonicalName(), e);
            messages(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    getString("exclusao_mensagem_erro"),
                    e.getMessage()));
        }
    }

    public String save() {
        try {
            this.salvar();
            values = this.registros();
            messages(new FacesMessage(getString("salvar_mensagem_sucesso")));
            return getPaginaInicial();
        } catch (EJBException e) {
            logger.error("Erro ao salvar registro: " + objectClass.getCanonicalName(), e);
            messages(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    getString("salvar_mensagem_erro"),
                    e.getMessage()));
        }

        return "";
    }

//    /*
//     sempre a tela de crud DEVERA abrir uma nova view para uma melhor 
//     usabilidade em celulares e tablets.
//     */
//    public String openCrudPage(Long id) {
//        if (getChaveId() != null) {
//            session.getParams().put(getChaveId(), id);
//            return getPaginaCadastro();
//        }
//
//        return "";
//    }

    /**
     * Campos que compoem o bean e que estarão presentes na table.
     *
     * @return
     */
    protected abstract String[] getFieldsListBean();

    /*
     pegar o facade da classe que extende o abstract não funcionou pois
     deu erro que o facade não era uma implementacao de IFacade.
     parece que ele não entende que uma interface extendendo a outra e
     quando esta interface ser implementada, a instancia dela não é uma
     implementacao da interface principal.
     */
    /**
     * metodo para excluir o registro com o id no facade.
     *
     * @param id
     * @throws EJBException
     */
    protected abstract void excluir() throws EJBException;

    /**
     * metodo para salvar o registro com o facade.
     *
     * @throws EJBException
     */
    protected abstract void salvar() throws EJBException;

    /**
     *
     * @return
     */
    protected abstract List<T> registros();

    /**
     * retorna a pagina inicial do crud com a listagem de registros.
     *
     * @return
     */
    protected abstract String getPaginaInicial();

    /**
     * lista com a string de cabeçalho da coluna do datatable e o atributo que
     * ela referencia no objeto da lista.
     *
     * @return
     */
    public abstract List<DataTableColumnModel> getColumnModel();

    /**
     * objeto da tela de crud.
     *
     * @return
     */
    public abstract T getValue();

    /**
     * objeto da tela de crud.
     *
     * @param value
     */
    public abstract void setValue(T value);
}
