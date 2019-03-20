package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.ClassRoom;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ClassRoom entity.
 */
public interface ClassRoomSearchRepository extends ElasticsearchRepository<ClassRoom, Long> {
}
