package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.AskingPermission;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AskingPermission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AskingPermissionRepository extends JpaRepository<AskingPermission, Long> {

}
