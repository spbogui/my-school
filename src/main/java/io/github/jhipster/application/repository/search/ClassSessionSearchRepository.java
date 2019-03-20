package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.ClassSession;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ClassSession entity.
 */
public interface ClassSessionSearchRepository extends ElasticsearchRepository<ClassSession, Long> {
}
