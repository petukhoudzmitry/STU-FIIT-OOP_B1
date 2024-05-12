package com.petition.platform.ooprequirements;

import com.petition.platform.models.SuperUser;

public class SuperUserFactory implements UserFactory<SuperUser> {
    @Override
    public SuperUser createUser() {
        return new SuperUser();
    }
}
