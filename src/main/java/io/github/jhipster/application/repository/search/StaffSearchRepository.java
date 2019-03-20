package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Staff;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Staff entity.
 */
public interface StaffSearchRepository extends ElasticsearchRepository<Staff, Long> {
}
