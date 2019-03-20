package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.StudentMissingSession;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the StudentMissingSession entity.
 */
public interface StudentMissingSessionSearchRepository extends ElasticsearchRepository<StudentMissingSession, Long> {
}
