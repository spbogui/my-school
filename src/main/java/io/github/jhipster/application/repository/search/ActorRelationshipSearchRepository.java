package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.ActorRelationship;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ActorRelationship entity.
 */
public interface ActorRelationshipSearchRepository extends ElasticsearchRepository<ActorRelationship, Long> {
}
