package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.IdentifierType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the IdentifierType entity.
 */
public interface IdentifierTypeSearchRepository extends ElasticsearchRepository<IdentifierType, Long> {
}
