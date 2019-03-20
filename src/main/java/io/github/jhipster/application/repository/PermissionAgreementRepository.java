package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.PermissionAgreement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PermissionAgreement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PermissionAgreementRepository extends JpaRepository<PermissionAgreement, Long> {

}
