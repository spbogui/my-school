package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Days;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Days entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DaysRepository extends JpaRepository<Days, Long> {

}
