package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Level;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Level entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {

}
