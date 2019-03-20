package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Enrolment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Enrolment entity.
 */
public interface EnrolmentSearchRepository extends ElasticsearchRepository<Enrolment, Long> {
}
