package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.AskingPermission;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AskingPermission entity.
 */
public interface AskingPermissionSearchRepository extends ElasticsearchRepository<AskingPermission, Long> {
}
