package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.LeaveHoliDay;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LeaveHoliDay entity.
 */
public interface LeaveHoliDaySearchRepository extends ElasticsearchRepository<LeaveHoliDay, Long> {
}
