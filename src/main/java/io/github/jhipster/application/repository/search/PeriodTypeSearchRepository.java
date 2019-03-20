package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.PeriodType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PeriodType entity.
 */
public interface PeriodTypeSearchRepository extends ElasticsearchRepository<PeriodType, Long> {
}
