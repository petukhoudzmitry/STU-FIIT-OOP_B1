package com.petition.platform.repositories;

import com.petition.platform.models.SimpleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing and managing SimpleUser entities in the database.
 */
@Repository
public interface SimpleUserRepository extends JpaRepository<SimpleUser, Long> {
    /**
     * Retrieves a SimpleUser by its username.
     *
     * @param username the username of the SimpleUser to retrieve.
     * @return an Optional containing the SimpleUser with the specified username, or empty if not found.
     */
    Optional<SimpleUser> findByUsername(String username);

    /**
     * Retrieves a SimpleUser by its email.
     *
     * @param email the email of the SimpleUser to retrieve.
     * @return an Optional containing the SimpleUser with the specified email, or empty if not found.
     */
    Optional<SimpleUser> findByEmail(String email);

    /**
     * Retrieves a list of SimpleUsers whose username contains the specified string.
     *
     * @param username the string to search for within usernames.
     * @return a list of SimpleUsers whose usernames contain the specified string.
     */
    List<SimpleUser> findByUsernameContaining(String username);
}