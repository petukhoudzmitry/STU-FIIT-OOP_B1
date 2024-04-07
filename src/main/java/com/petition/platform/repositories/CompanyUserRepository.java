package com.petition.platform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.petition.platform.models.CompanyUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {
    Optional<CompanyUser> findByEmail(String email);
    Optional<CompanyUser> findByUsername(String username);
    List<CompanyUser> findByUsernameContaining(String username);
}
