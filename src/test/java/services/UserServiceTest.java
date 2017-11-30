package services;

import model.entities.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.HibernateUtil;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService = new UserService();

    @BeforeEach
    void setUp() {
        HibernateUtil.getCurrentSession().beginTransaction();
    }

    @AfterEach
    void tearDown() {
        HibernateUtil.getCurrentSession().getTransaction().commit();
    }

    @Test
    void add() {
        try {

            UserEntity user = new UserEntity("Masha", "Krasotka");
            Serializable id = userService.add(user);
            assertTrue(HibernateUtil.getCurrentSession().contains(user));
            assertEquals((int) id, user.getId());
            HibernateUtil.getCurrentSession().delete(HibernateUtil.getCurrentSession().load(UserEntity.class, id));
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    @Test
    void remove() {
        try {
            UserEntity user = new UserEntity("Masha", "Krasotka");
            userService.add(user);
            userService.remove(user);
            assertFalse(HibernateUtil.getCurrentSession().contains(user));
            assertFalse(HibernateUtil.getCurrentSession().contains(user.getPassword()));
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }

    @Test
    void getUserByLogin() {
        try {
            UserEntity user = new UserEntity("Masha", "Krasotka");
            userService.add(user);
            UserEntity entity = userService.getUserByLogin("Masha");
            assertEquals(entity, user);
            userService.remove(user);
            entity = userService.getUserByLogin("Masha");
            assertEquals(entity, null);
        }
        catch (Exception exc) {
            HibernateUtil.getCurrentSession().getTransaction().rollback();
            throw exc;
        }
    }
}