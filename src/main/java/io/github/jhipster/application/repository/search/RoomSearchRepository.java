package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Room;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Room entity.
 */
public interface RoomSearchRepository extends ElasticsearchRepository<Room, Long> {
}
