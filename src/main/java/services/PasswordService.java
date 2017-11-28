package services;

import model.entities.PasswordEntity;

public class PasswordService extends AbstractService<PasswordEntity> {
    public PasswordService() {
        super(PasswordEntity.class);
    }
}
