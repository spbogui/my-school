package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Diploma;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Diploma entity.
 */
public interface DiplomaSearchRepository extends ElasticsearchRepository<Diploma, Long> {
}
