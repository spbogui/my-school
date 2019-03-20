package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.RoomType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RoomType entity.
 */
public interface RoomTypeSearchRepository extends ElasticsearchRepository<RoomType, Long> {
}
