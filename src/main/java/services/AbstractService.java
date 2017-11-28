package services;

import java.io.Serializable;
import java.util.List;

import static utils.HibernateUtil.getCurrentSession;

abstract public class AbstractService<T> {
    private Class<T> typeParameterClass;

    public AbstractService(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public Serializable add(T entity) {
        return getCurrentSession().save(entity);
    }

    public List<T> getAll() {
        return (List<T>) getCurrentSession().createCriteria(typeParameterClass).list();
    }

    public void update(T entity) {
        getCurrentSession().update(entity);
    }

    public void remove(T entity) {
        getCurrentSession().delete(entity);
    }
}