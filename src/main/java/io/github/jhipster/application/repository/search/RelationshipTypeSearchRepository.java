package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.RelationshipType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RelationshipType entity.
 */
public interface RelationshipTypeSearchRepository extends ElasticsearchRepository<RelationshipType, Long> {
}
