package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.School;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the School entity.
 */
public interface SchoolSearchRepository extends ElasticsearchRepository<School, Long> {
}
