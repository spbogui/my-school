package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Evaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Evaluation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    @Query(value = "select distinct evaluation from Evaluation evaluation left join fetch evaluation.classRooms",
        countQuery = "select count(distinct evaluation) from Evaluation evaluation")
    Page<Evaluation> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct evaluation from Evaluation evaluation left join fetch evaluation.classRooms")
    List<Evaluation> findAllWithEagerRelationships();

    @Query("select evaluation from Evaluation evaluation left join fetch evaluation.classRooms where evaluation.id =:id")
    Optional<Evaluation> findOneWithEagerRelationships(@Param("id") Long id);

}
