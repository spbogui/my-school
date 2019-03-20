package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.LeaveHoliDay;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LeaveHoliDay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveHoliDayRepository extends JpaRepository<LeaveHoliDay, Long> {

}
