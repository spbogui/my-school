package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Cycle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Cycle entity.
 */
public interface CycleSearchRepository extends ElasticsearchRepository<Cycle, Long> {
}
