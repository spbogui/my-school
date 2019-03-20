package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.TeacherSubjectSchoolYear;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TeacherSubjectSchoolYear entity.
 */
public interface TeacherSubjectSchoolYearSearchRepository extends ElasticsearchRepository<TeacherSubjectSchoolYear, Long> {
}
