package com.petition.platform.repositories;

import com.petition.platform.models.SuperUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing and managing SuperUser entities in the database.
 */
@Repository
public interface SuperUserRepository extends JpaRepository<SuperUser, Long> {
    /**
     * Retrieves a SuperUser by its email.
     *
     * @param email the email of the SuperUser to retrieve.
     * @return an Optional containing the SuperUser with the specified email, or empty if not found.
     */
    Optional<SuperUser> findByEmail(String email);

    /**
     * Retrieves a SuperUser by its username.
     *
     * @param email the username of the SuperUser to retrieve.
     * @return an Optional containing the SuperUser with the specified username, or empty if not found.
     */
    Optional<SuperUser> findByUsername(String email);

    /**
     * Retrieves a list of SuperUsers whose username contains the specified string.
     *
     * @param username the string to search for within usernames.
     * @return a list of SuperUsers whose usernames contain the specified string.
     */
    List<SuperUser> findByUsernameContaining(String username);

    /**
     * Retrieves all SuperUsers whose isRoot property is false.
     *
     * @return a list of SuperUsers where isRoot is false.
     */
    List<SuperUser> findAllByIsRootIsFalse();

    /**
     * Retrieves all SuperUsers whose username contains the specified string and isRoot property is false.
     *
     * @param username the string to search for within usernames.
     * @return a list of SuperUsers whose usernames contain the specified string and isRoot is false.
     */
    List<SuperUser> findAllByUsernameContainingAndIsRootIsFalse(String username);
}