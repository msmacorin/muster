/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade;

import br.com.muster.model.admin.Usuario;
import br.com.muster.restful.ServiceBuilder;
import in.macor.core.dao.IDao;
import in.macor.core.facade.IFacade;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBException;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author macorin
 * @param <T>
 * @param <PK>
 */
public abstract class Facade<T, PK> implements IFacade<T, PK> {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override
    public void salvar(T entity) throws EJBException {
        getDao().save(entity);
    }

    @Override
    public void deletar(PK id) throws EJBException {
        getDao().delete(id);
    }

    @Override
    public T buscarPorId(PK id) {
        return getDao().findById(id);
    }

    @Override
    public List<T> selecionarTodos() {
        return this.selecionarTodos(Order.desc("id"));
    }

    @Override
    public List<T> selecionarTodos(Order... ordem) {
        return getDao().findAll(ordem);
    }
    
    @Override
    public List<T> selecionarListBean(String[] camposListBean, int linhaInicial, int maximoResultados, String colunaOrdenacao, String direcaoOrdenacao, Map<String, String> filtros) {
        return getDao().selectForListBean(camposListBean, linhaInicial, maximoResultados, colunaOrdenacao, direcaoOrdenacao, filtros, exclusaoVirtual());
    }
    
    protected abstract IDao<T, PK> getDao();
    
    protected ServiceBuilder getService(String url, Usuario usuario) {
        ServiceBuilder builder = new ServiceBuilder(
                url, 
                usuario.getLogin(), 
                usuario.getSenha()
        );
        return builder;
    }
}
