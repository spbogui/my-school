package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.RubricAmount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RubricAmount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RubricAmountRepository extends JpaRepository<RubricAmount, Long> {

}
