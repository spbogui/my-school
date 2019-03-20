package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ActorRelationship;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ActorRelationship entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActorRelationshipRepository extends JpaRepository<ActorRelationship, Long> {

}
