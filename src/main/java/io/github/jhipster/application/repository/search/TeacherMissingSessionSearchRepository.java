package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.TeacherMissingSession;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TeacherMissingSession entity.
 */
public interface TeacherMissingSessionSearchRepository extends ElasticsearchRepository<TeacherMissingSession, Long> {
}
