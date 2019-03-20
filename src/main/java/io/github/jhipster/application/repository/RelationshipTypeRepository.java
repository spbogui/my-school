package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.RelationshipType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RelationshipType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelationshipTypeRepository extends JpaRepository<RelationshipType, Long> {

}
