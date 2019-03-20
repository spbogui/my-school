package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.SchoolYear;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SchoolYear entity.
 */
public interface SchoolYearSearchRepository extends ElasticsearchRepository<SchoolYear, Long> {
}
