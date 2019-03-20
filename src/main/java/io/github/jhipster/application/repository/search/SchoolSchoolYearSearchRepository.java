package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.SchoolSchoolYear;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SchoolSchoolYear entity.
 */
public interface SchoolSchoolYearSearchRepository extends ElasticsearchRepository<SchoolSchoolYear, Long> {
}
