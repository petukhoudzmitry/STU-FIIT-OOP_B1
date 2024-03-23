package com.petition.platform.repositories;

import com.petition.platform.models.SimpleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SimpleUser, Integer> {
    Optional<SimpleUser> findByUsername(String username);
    Optional<SimpleUser> findByEmail(String email);
}
