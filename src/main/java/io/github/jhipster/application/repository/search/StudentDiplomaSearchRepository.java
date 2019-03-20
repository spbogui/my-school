package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.StudentDiploma;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the StudentDiploma entity.
 */
public interface StudentDiplomaSearchRepository extends ElasticsearchRepository<StudentDiploma, Long> {
}
