/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.dao;

import in.macor.core.dao.IDao;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.SimpleExpression;

/**
 *
 * @author macorin
 * @param <T> VO a qual o DAO se refere
 * @param <PK> Classe referente ao tipo da chave primaria.
 */
public abstract class Dao<T, PK> implements IDao<T, PK>, Serializable {

    private static final long serialVersionUID = 1L;

    private final Class<T> objectClass = (Class<T>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];

    @PersistenceContext(unitName = "musterPU")
    protected EntityManager em;
    
    protected Map<String, Object> params = new HashMap<>();

    protected Session getSession() {
        return (Session) em.unwrap(Session.class);
    }

    @Override
    public void save(T entity) {
        em.merge(entity);
    }

    @Override
    public void delete(PK id) {
        T entityToBeRemoved = em.getReference(objectClass, id);

        em.remove(entityToBeRemoved);
    }

    @Override
    public T findById(PK id) {
        try {
            return em.find(objectClass, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public T find(String query) {
        try {
            Query q = em.createQuery(query);

            populateQueryParameters(q, params);
            
            q.setMaxResults(1);

            return (T) q.getSingleResult();
        } catch (NoResultException noResultException) {
            return null;
        }
    }

    @Override
    public List<T> select(String query) {
        try {
            Query q = em.createQuery(query);
            
            populateQueryParameters(q, params);

            return q.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<T> findAll(Order... orders) {
        Criteria c = getSession().createCriteria(objectClass);

        for (Order order : orders) {
            c.addOrder(order);
        }

        c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        return c.list();
    }

    @Override
    public T findOneResult(SimpleExpression... expressions) {
        Criteria c = getSession().createCriteria(objectClass);

        for (SimpleExpression exp : expressions) {
            c.add(exp);
        }

        return (T) c.uniqueResult();
    }

    @Override
    public Long count(Criterion... criterions) {
        Criteria c = getSession().createCriteria(objectClass);

        for (Criterion cri : criterions) {
            c.add(cri);
        }

        c.setProjection(Projections.rowCount());

        return (Long) c.uniqueResult();
    }

    private void populateQueryParameters(Query query, Map<String, Object> parameters) {
        for (Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public List<T> selectPagination(String hql,
            int startRow,
            int maxResults,
            String sortColumn,
            String sortDirection) {

        Session session = getSession();

        StringBuilder hqlBuilder = new StringBuilder();

        hqlBuilder.append(hql);
        appendOrderClause(sortColumn, sortDirection, hqlBuilder);

        org.hibernate.Query query = session.createQuery(hqlBuilder.toString());

        Set<String> str = params.keySet();
        for (String string : str) {
            Object value = params.get(string);
            if (value instanceof Collection) {
                query.setParameterList(string, (Collection) value);
            } else {
                query.setParameter(string, value);
            }
        }

        query.setFirstResult(startRow);
        query.setMaxResults(maxResults);

        return (List<T>) query.list();
    }

    @Override
    public List<T> selectForListBean(String[] columns,
            int startRow,
            int maxResults,
            String sortColumn,
            String sortDirection,
            Map<String, String> filters,
            boolean virtualDelete) {

        Session session = getSession();

        String hql = createQueryDataTable(columns,
                sortColumn,
                sortDirection,
                filters,
                virtualDelete);

        org.hibernate.Query query = session.createQuery(hql);

        query.setFirstResult(startRow);
        query.setMaxResults(maxResults);

        return query.list();
    }

    private void appendOrderClause(String sortColumn, String sortDirection,
            StringBuilder builder) {
        if (sortColumn != null) {
            builder.append(" ORDER BY ");
            builder.append(sortColumn);
            builder.append((sortDirection != null && sortDirection
                    .equalsIgnoreCase("DESC")) ? " DESC" : " ASC");
        }
    }

    private String createQueryDataTable(String[] columns,
            String sortColumn,
            String sortDirection,
            Map<String, String> filters,
            boolean virtualDelete) {
        StringBuilder builder = new StringBuilder();

        builder.append("SELECT ");

        for (int i = 0; i < columns.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append("t.");
            builder.append(columns[i]);
        }

        builder.append(" FROM ");
        builder.append(objectClass.getSimpleName());
        builder.append(" t");

        appendWhereClause(columns, filters, builder, virtualDelete);

        if (sortColumn != null && !sortColumn.trim().isEmpty()) {
            appendOrderClause("t." + sortColumn, sortDirection,
                    builder);
        }

        return builder.toString();
    }

    private void appendWhereClause(String[] columns,
            Map<String, String> filters,
            StringBuilder builder,
            boolean virtualDelete) {
        String or = " WHERE ";

        if (virtualDelete) {
            builder.append(or);
            builder.append(" t.situacao = 1 ");
            or = " AND ";
        }

        if (filters != null && filters.size() > 0) {
            for (Map.Entry<String, String> filter : filters.entrySet()) {

                builder.append(or);

                builder.append(" lower(cast(").append("t.");
                builder.append(filter.getKey());
                builder.append(" as string))").append(" LIKE ").append("'%");
                builder.append(filter.getValue());
                builder.append("%' ");

                or = " OR ";
            }
        }
    }
}
