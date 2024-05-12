package com.petition.platform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.petition.platform.models.CompanyUser;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing and managing CompanyUser entities in the database.
 */
@Repository
public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {
    /**
     * Retrieves a CompanyUser by their email.
     *
     * @param email the email of the CompanyUser to retrieve.
     * @return an Optional containing the CompanyUser with the specified email, or empty if not found.
     */
    Optional<CompanyUser> findByEmail(String email);

    /**
     * Retrieves a CompanyUser by their username.
     *
     * @param username the username of the CompanyUser to retrieve.
     * @return an Optional containing the CompanyUser with the specified username, or empty if not found.
     */
    Optional<CompanyUser> findByUsername(String username);

    /**
     * Retrieves a list of CompanyUsers whose username contains the specified string.
     *
     * @param username the string to search for within usernames.
     * @return a list of CompanyUsers whose usernames contain the specified string.
     */
    List<CompanyUser> findByUsernameContaining(String username);
}