package com.petition.platform.ooprequirements;

import com.petition.platform.models.SimpleUser;

public class SimpleUserFactory implements UserFactory<SimpleUser>{
    @Override
    public SimpleUser createUser() {
        return new SimpleUser();
    }
}
