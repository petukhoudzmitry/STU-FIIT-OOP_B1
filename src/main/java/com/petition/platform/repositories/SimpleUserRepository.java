package com.petition.platform.repositories;

import com.petition.platform.models.SimpleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SimpleUserRepository extends JpaRepository<SimpleUser, Long> {
    Optional<SimpleUser> findByUsername(String username);
    Optional<SimpleUser> findByEmail(String email);
    List<SimpleUser> findByUsernameContaining(String username);
}
