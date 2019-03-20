package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Regimen;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Regimen entity.
 */
public interface RegimenSearchRepository extends ElasticsearchRepository<Regimen, Long> {
}
