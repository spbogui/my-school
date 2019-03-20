package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.StudentEvaluation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the StudentEvaluation entity.
 */
public interface StudentEvaluationSearchRepository extends ElasticsearchRepository<StudentEvaluation, Long> {
}
