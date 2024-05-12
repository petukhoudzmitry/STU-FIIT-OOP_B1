package com.petition.platform.repositories;

import com.petition.platform.models.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing and managing AdminUser entities in the database.
 */
@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    /**
     * Retrieves an AdminUser by their email.
     *
     * @param email the email of the AdminUser to retrieve.
     * @return an Optional containing the AdminUser with the specified email, or empty if not found.
     */
    Optional<AdminUser> findByEmail(String email);

    /**
     * Retrieves an AdminUser by their username.
     *
     * @param username the username of the AdminUser to retrieve.
     * @return an Optional containing the AdminUser with the specified username, or empty if not found.
     */
    Optional<AdminUser> findByUsername(String username);

    /**
     * Retrieves a list of AdminUsers whose username contains the specified string.
     *
     * @param username the string to search for within usernames.
     * @return a list of AdminUsers whose usernames contain the specified string.
     */
    List<AdminUser> findByUsernameContaining(String username);
}