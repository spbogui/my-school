package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Responsible;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Responsible entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResponsibleRepository extends JpaRepository<Responsible, Long> {

}
