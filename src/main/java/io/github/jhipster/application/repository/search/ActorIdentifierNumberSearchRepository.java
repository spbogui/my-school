package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.ActorIdentifierNumber;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ActorIdentifierNumber entity.
 */
public interface ActorIdentifierNumberSearchRepository extends ElasticsearchRepository<ActorIdentifierNumber, Long> {
}
