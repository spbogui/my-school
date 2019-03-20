package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ActorName;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ActorName entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActorNameRepository extends JpaRepository<ActorName, Long> {

}
