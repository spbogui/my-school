package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Days;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Days entity.
 */
public interface DaysSearchRepository extends ElasticsearchRepository<Days, Long> {
}
