package com.petition.platform.repositories;

import com.petition.platform.models.CompanyUser;
import com.petition.platform.models.SimplePetition;
import com.petition.platform.models.SimpleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for accessing and managing SimplePetition entities in the database.
 */
@Repository
public interface SimplePetitionRepository extends JpaRepository<SimplePetition, Long> {
    /**
     * Retrieves a SimplePetition by its ID.
     *
     * @param id the ID of the SimplePetition to retrieve.
     * @return an Optional containing the SimplePetition with the specified ID, or empty if not found.
     */
    Optional<SimplePetition> findById(UUID id);

    /**
     * Retrieves a list of SimplePetitions whose title contains the specified string.
     *
     * @param title the string to search for within petition titles.
     * @return a list of SimplePetitions whose titles contain the specified string.
     */
    List<SimplePetition> findByTitleContaining(String title);

    /**
     * Retrieves a list of SimplePetitions created by the specified SimpleUser.
     *
     * @param creator the SimpleUser who created the petitions.
     * @return a list of SimplePetitions created by the specified SimpleUser.
     */
    List<SimplePetition> findByCreator(SimpleUser creator);

    /**
     * Retrieves a list of SimplePetitions associated with the specified CompanyUser.
     *
     * @param company the CompanyUser associated with the petitions.
     * @return a list of SimplePetitions associated with the specified CompanyUser.
     */
    List<SimplePetition> findByCompany(CompanyUser company);

    /**
     * Retrieves a list of SimplePetitions associated with the specified CompanyUser and ordered by their validUntil
     * field in descending order.
     *
     * @param companyUser the CompanyUser associated with the petitions.
     * @param creator     the SimpleUser who created the petitions.
     * @return a list of SimplePetitions associated with the specified CompanyUser and ordered by their validUntil field
     * in descending order.
     */
    List<SimplePetition> findByCompanyAndCreatorOrderByValidUntilDesc(CompanyUser companyUser, SimpleUser creator);

    /**
     * Retrieves a list of SimplePetitions whose text contains the specified string.
     *
     * @param text the string to search for within petition text.
     * @return a list of SimplePetitions whose text contains the specified string.
     */
    List<SimplePetition> findByTextContaining(String text);

    /**
     * Retrieves a list of SimplePetitions associated with a CompanyUser whose username contains the specified string.
     *
     * @param name the string to search for within company usernames.
     * @return a list of SimplePetitions associated with a CompanyUser whose username contains the specified string.
     */
    List<SimplePetition> findByCompanyUsernameContaining(String name);
}