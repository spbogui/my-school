package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.StaffJob;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the StaffJob entity.
 */
public interface StaffJobSearchRepository extends ElasticsearchRepository<StaffJob, Long> {
}
