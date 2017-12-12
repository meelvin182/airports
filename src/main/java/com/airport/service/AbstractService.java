package com.airport.service;

import com.airport.util.HibernateUtil;

import java.io.Serializable;
import java.util.List;

import static com.airport.util.HibernateUtil.getCurrentSession;

abstract public class AbstractService<T> {
    private Class<T> typeParameterClass;

    public AbstractService(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public Serializable add(T entity) {
        try {
            HibernateUtil.getCurrentSession().beginTransaction();
            Serializable serializable = getCurrentSession().save(entity);
            HibernateUtil.getCurrentSession().getTransaction().commit();
            return serializable;
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    public List<T> getAll() {
        try {
            HibernateUtil.getCurrentSession().beginTransaction();
            List<T> list = (List<T>) getCurrentSession().createCriteria(typeParameterClass).list();
            HibernateUtil.getCurrentSession().getTransaction().commit();
            return list;
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    public void remove(T entity) {
        try {
            HibernateUtil.getCurrentSession().beginTransaction();
            getCurrentSession().delete(entity);
            HibernateUtil.getCurrentSession().getTransaction().commit();
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }
}