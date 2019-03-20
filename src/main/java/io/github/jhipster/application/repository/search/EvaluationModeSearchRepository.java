package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.EvaluationMode;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EvaluationMode entity.
 */
public interface EvaluationModeSearchRepository extends ElasticsearchRepository<EvaluationMode, Long> {
}
