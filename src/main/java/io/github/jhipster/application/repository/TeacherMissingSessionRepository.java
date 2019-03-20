package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.TeacherMissingSession;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TeacherMissingSession entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeacherMissingSessionRepository extends JpaRepository<TeacherMissingSession, Long> {

}
