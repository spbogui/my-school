package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Responsible;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Responsible entity.
 */
public interface ResponsibleSearchRepository extends ElasticsearchRepository<Responsible, Long> {
}
