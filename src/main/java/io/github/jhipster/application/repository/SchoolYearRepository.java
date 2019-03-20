package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.SchoolYear;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SchoolYear entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchoolYearRepository extends JpaRepository<SchoolYear, Long> {

}
