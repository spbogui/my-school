package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.EvaluationMode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EvaluationMode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluationModeRepository extends JpaRepository<EvaluationMode, Long> {

}
