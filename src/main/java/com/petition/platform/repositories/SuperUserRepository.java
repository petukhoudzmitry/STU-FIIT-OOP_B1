package com.petition.platform.repositories;

import com.petition.platform.models.SuperUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuperUserRepository extends JpaRepository<SuperUser, Long> {
    Optional<SuperUser> findByEmail(String email);
    Optional<SuperUser> findByUsername(String email);
}
