package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.ClassSessionType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ClassSessionType entity.
 */
public interface ClassSessionTypeSearchRepository extends ElasticsearchRepository<ClassSessionType, Long> {
}
