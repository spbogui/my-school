package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.StudentMissingSession;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StudentMissingSession entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentMissingSessionRepository extends JpaRepository<StudentMissingSession, Long> {

}
