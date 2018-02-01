package com.airport.service;

import com.airport.model.entities.UserEntity;
import com.airport.util.HibernateUtil;
import org.springframework.stereotype.Service;

import java.io.Serializable;

import static com.airport.util.HibernateUtil.getCurrentSession;

@Service
public class UserService extends AbstractService<UserEntity> {
    public UserService() {
        super(UserEntity.class);
    }

    public UserEntity getUserByLogin(String login) {
        try {
            HibernateUtil.getCurrentSession().beginTransaction();
            UserEntity user = (UserEntity) HibernateUtil.getCurrentSession()
                    .createQuery("SELECT user FROM UserEntity user " +
                            "WHERE user.login = :login")
                    .setString("login", login)
                    .uniqueResult();
            HibernateUtil.getCurrentSession().getTransaction().commit();
            return user;
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    public void changePassword(String login, String password) {
        try {
            UserEntity user = getUserByLogin(login);
            user.setPassword(password);
            HibernateUtil.getCurrentSession().beginTransaction();
            HibernateUtil.getCurrentSession().update(user);
            HibernateUtil.getCurrentSession().getTransaction().commit();
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }


}