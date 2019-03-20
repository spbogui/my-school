package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ActorIdentifierNumber;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ActorIdentifierNumber entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActorIdentifierNumberRepository extends JpaRepository<ActorIdentifierNumber, Long> {

}
