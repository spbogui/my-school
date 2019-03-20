package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.EvaluationType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EvaluationType entity.
 */
public interface EvaluationTypeSearchRepository extends ElasticsearchRepository<EvaluationType, Long> {
}
