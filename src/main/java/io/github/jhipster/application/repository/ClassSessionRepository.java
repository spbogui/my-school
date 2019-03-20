package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ClassSession;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClassSession entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassSessionRepository extends JpaRepository<ClassSession, Long> {

}
