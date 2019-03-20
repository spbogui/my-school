package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.EvaluationType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EvaluationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluationTypeRepository extends JpaRepository<EvaluationType, Long> {

}
