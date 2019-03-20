package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.PermissionAgreement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PermissionAgreement entity.
 */
public interface PermissionAgreementSearchRepository extends ElasticsearchRepository<PermissionAgreement, Long> {
}
