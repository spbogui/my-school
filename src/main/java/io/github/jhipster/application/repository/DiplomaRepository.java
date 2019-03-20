package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Diploma;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Diploma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiplomaRepository extends JpaRepository<Diploma, Long> {

}
