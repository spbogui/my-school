package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Enrolment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Enrolment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnrolmentRepository extends JpaRepository<Enrolment, Long> {

}
