package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ClassRoom;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClassRoom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {

}
