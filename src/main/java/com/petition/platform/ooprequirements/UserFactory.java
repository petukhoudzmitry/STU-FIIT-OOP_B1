package com.petition.platform.ooprequirements;

import com.petition.platform.models.User;

public interface UserFactory <T extends User> {
    T createUser();
}
