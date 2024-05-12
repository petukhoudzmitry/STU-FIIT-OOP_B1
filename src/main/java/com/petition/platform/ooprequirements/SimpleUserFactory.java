package com.petition.platform.ooprequirements;

import com.petition.platform.models.SimpleUser;

/**
 * Factory class for creating instances of SimpleUser.
 */
public class SimpleUserFactory implements UserFactory<SimpleUser>{
    /**
     * Default constructor for SimpleUserFactory.
     */
    public SimpleUserFactory() {}

    /**
     * Creates and returns a new instance of SimpleUser.
     *
     * @return A new instance of SimpleUser.
     */
    @Override
    public SimpleUser createUser() {
        return new SimpleUser();
    }
}