package services;

import model.entities.UserEntity;

public class UserService extends AbstractService<UserEntity> {
    public UserService() {
        super(UserEntity.class);
    }
}