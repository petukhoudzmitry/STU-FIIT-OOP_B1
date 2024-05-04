package com.petition.platform.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class SuperUser extends User {

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
}
