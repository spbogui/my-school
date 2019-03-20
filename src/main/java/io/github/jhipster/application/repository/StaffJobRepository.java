package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.StaffJob;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StaffJob entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StaffJobRepository extends JpaRepository<StaffJob, Long> {

}
