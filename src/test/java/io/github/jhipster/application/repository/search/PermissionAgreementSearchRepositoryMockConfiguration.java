package io.github.jhipster.application.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of PermissionAgreementSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PermissionAgreementSearchRepositoryMockConfiguration {

    @MockBean
    private PermissionAgreementSearchRepository mockPermissionAgreementSearchRepository;

}
