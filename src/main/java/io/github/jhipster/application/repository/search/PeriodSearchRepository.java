package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Period;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Period entity.
 */
public interface PeriodSearchRepository extends ElasticsearchRepository<Period, Long> {
}
