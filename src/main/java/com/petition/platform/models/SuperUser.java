package com.petition.platform.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.io.Serial;
import java.io.Serializable;

@Entity
public class SuperUser extends User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public SuperUser(){}

    public SuperUser(User user){
        super(user);
    }

    @Column(name = "isRoot", nullable = false)
    private Boolean isRoot = false;

    public Boolean getRoot() {
        return isRoot;
    }

    public void setRoot(Boolean root) {
        isRoot = root;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof SuperUser superUser){
            return getId().equals(superUser.getId()) && getEmail().equals(superUser.getEmail()) && getPassword().equals(superUser.getPassword()) && getRole().equals(superUser.getRole());
        }

        return false;
    }
}
