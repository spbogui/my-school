package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Rubric;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Rubric entity.
 */
public interface RubricSearchRepository extends ElasticsearchRepository<Rubric, Long> {
}
