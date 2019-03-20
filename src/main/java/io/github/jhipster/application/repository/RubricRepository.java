package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Rubric;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Rubric entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RubricRepository extends JpaRepository<Rubric, Long> {

}
