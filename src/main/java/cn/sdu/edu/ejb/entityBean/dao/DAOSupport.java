package cn.sdu.edu.ejb.entityBean.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * 
 * @author Yonggang Yuan
 *
 * @param <T>
 */

public abstract class DAOSupport<T> implements DAO<T> {

    @PersistenceContext(unitName="person") protected EntityManager em;

    protected Class<T> typeOfT;

    @SuppressWarnings("unchecked")
    public DAOSupport() {
        /* 通过反射进行类型推断，拿到T的实际类型 */
        Type[] types = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
        if (types.length > 0) {
            this.typeOfT = (Class<T>) types[0];
        }
    }

    @Override
    public void save(T entity) {
        em.persist(entity);
    }

    @Override
    public T update(T entity) {
        return em.merge(entity);
    }

    @Override
    public void delete(Object entityId) {
        em.remove(em.getReference(typeOfT, entityId));
    }

    @Override
    public T find(Object entityId) {
        return em.find(typeOfT, entityId);
    }

}
