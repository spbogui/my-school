package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Level;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Level entity.
 */
public interface LevelSearchRepository extends ElasticsearchRepository<Level, Long> {
}
