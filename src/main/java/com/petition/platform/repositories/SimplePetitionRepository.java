package com.petition.platform.repositories;

import com.petition.platform.models.CompanyUser;
import com.petition.platform.models.SimplePetition;
import com.petition.platform.models.SimpleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SimplePetitionRepository extends JpaRepository<SimplePetition, Long> {
    Optional<SimplePetition> findById(UUID id);
    List<SimplePetition> findByTitleContaining(String title);
    List<SimplePetition> findByCreator(SimpleUser creator);
    List<SimplePetition> findByCompany(CompanyUser company);
    List<SimplePetition> findByCompanyAndCreatorOrderByValidUntilDesc(CompanyUser companyUser, SimpleUser creator);
    List<SimplePetition> findByTextContaining(String text);
    List<SimplePetition> findByCompanyUsernameContaining(String name);
}
