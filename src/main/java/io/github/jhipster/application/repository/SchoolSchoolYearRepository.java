package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.SchoolSchoolYear;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SchoolSchoolYear entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchoolSchoolYearRepository extends JpaRepository<SchoolSchoolYear, Long> {

}
