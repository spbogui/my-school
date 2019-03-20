package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Evaluation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Evaluation entity.
 */
public interface EvaluationSearchRepository extends ElasticsearchRepository<Evaluation, Long> {
}
