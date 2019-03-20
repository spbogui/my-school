package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.RubricAmount;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RubricAmount entity.
 */
public interface RubricAmountSearchRepository extends ElasticsearchRepository<RubricAmount, Long> {
}
