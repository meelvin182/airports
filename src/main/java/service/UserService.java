package service;

import model.entities.UserEntity;
import util.HibernateUtil;

import java.io.Serializable;

import static util.HibernateUtil.getCurrentSession;

public class UserService {
    public Serializable add(UserEntity entity) {
        return getCurrentSession().save(entity);
    }
    public void remove(UserEntity entity) {
        getCurrentSession().delete(entity);
    }

    public UserEntity getUserByLogin(String login) {
        UserEntity user = (UserEntity) HibernateUtil.getCurrentSession()
                .createQuery("SELECT user FROM UserEntity user " +
                        "WHERE user.login = :login")
                .setString("login", login)
                .uniqueResult();
        return user;
    }
}