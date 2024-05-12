package com.petition.platform.ooprequirements;

import com.petition.platform.models.SuperUser;

/**
 * Factory class for creating instances of SuperUser.
 */
public class SuperUserFactory implements UserFactory<SuperUser> {
    /**
     * Default constructor for SuperUserFactory.
     */
    public SuperUserFactory() {}
    /**
     * Creates and returns a new instance of SuperUser.
     *
     * @return A new instance of SuperUser.
     */
    @Override
    public SuperUser createUser() {
        return new SuperUser();
    }
}