package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.ActorName;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ActorName entity.
 */
public interface ActorNameSearchRepository extends ElasticsearchRepository<ActorName, Long> {
}
