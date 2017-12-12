package com.airport.service;

import com.airport.model.entities.UserEntity;
import com.airport.util.HibernateUtil;

import java.io.Serializable;

import static com.airport.util.HibernateUtil.getCurrentSession;

public class UserService {
    public Serializable add(UserEntity entity) {
        return getCurrentSession().save(entity);
    }
    public void remove(UserEntity entity) {
        getCurrentSession().delete(entity);
    }

    public UserEntity getUserByLogin(String login) {
        HibernateUtil.getCurrentSession().beginTransaction();
        UserEntity user = (UserEntity) HibernateUtil.getCurrentSession()
                .createQuery("SELECT user FROM UserEntity user " +
                        "WHERE user.login = :login")
                .setString("login", login)
                .uniqueResult();
        HibernateUtil.getCurrentSession().getTransaction().commit();
        return user;
    }
}