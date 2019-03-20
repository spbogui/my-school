package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.IdentifierType;
import io.github.jhipster.application.repository.IdentifierTypeRepository;
import io.github.jhipster.application.repository.search.IdentifierTypeSearchRepository;
import io.github.jhipster.application.service.IdentifierTypeService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the IdentifierTypeResource REST controller.
 *
 * @see IdentifierTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class IdentifierTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private IdentifierTypeRepository identifierTypeRepository;

    @Autowired
    private IdentifierTypeService identifierTypeService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.IdentifierTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private IdentifierTypeSearchRepository mockIdentifierTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restIdentifierTypeMockMvc;

    private IdentifierType identifierType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IdentifierTypeResource identifierTypeResource = new IdentifierTypeResource(identifierTypeService);
        this.restIdentifierTypeMockMvc = MockMvcBuilders.standaloneSetup(identifierTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IdentifierType createEntity(EntityManager em) {
        IdentifierType identifierType = new IdentifierType()
            .name(DEFAULT_NAME);
        return identifierType;
    }

    @Before
    public void initTest() {
        identifierType = createEntity(em);
    }

    @Test
    @Transactional
    public void createIdentifierType() throws Exception {
        int databaseSizeBeforeCreate = identifierTypeRepository.findAll().size();

        // Create the IdentifierType
        restIdentifierTypeMockMvc.perform(post("/api/identifier-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identifierType)))
            .andExpect(status().isCreated());

        // Validate the IdentifierType in the database
        List<IdentifierType> identifierTypeList = identifierTypeRepository.findAll();
        assertThat(identifierTypeList).hasSize(databaseSizeBeforeCreate + 1);
        IdentifierType testIdentifierType = identifierTypeList.get(identifierTypeList.size() - 1);
        assertThat(testIdentifierType.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the IdentifierType in Elasticsearch
        verify(mockIdentifierTypeSearchRepository, times(1)).save(testIdentifierType);
    }

    @Test
    @Transactional
    public void createIdentifierTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = identifierTypeRepository.findAll().size();

        // Create the IdentifierType with an existing ID
        identifierType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdentifierTypeMockMvc.perform(post("/api/identifier-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identifierType)))
            .andExpect(status().isBadRequest());

        // Validate the IdentifierType in the database
        List<IdentifierType> identifierTypeList = identifierTypeRepository.findAll();
        assertThat(identifierTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the IdentifierType in Elasticsearch
        verify(mockIdentifierTypeSearchRepository, times(0)).save(identifierType);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = identifierTypeRepository.findAll().size();
        // set the field null
        identifierType.setName(null);

        // Create the IdentifierType, which fails.

        restIdentifierTypeMockMvc.perform(post("/api/identifier-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identifierType)))
            .andExpect(status().isBadRequest());

        List<IdentifierType> identifierTypeList = identifierTypeRepository.findAll();
        assertThat(identifierTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIdentifierTypes() throws Exception {
        // Initialize the database
        identifierTypeRepository.saveAndFlush(identifierType);

        // Get all the identifierTypeList
        restIdentifierTypeMockMvc.perform(get("/api/identifier-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(identifierType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getIdentifierType() throws Exception {
        // Initialize the database
        identifierTypeRepository.saveAndFlush(identifierType);

        // Get the identifierType
        restIdentifierTypeMockMvc.perform(get("/api/identifier-types/{id}", identifierType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(identifierType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIdentifierType() throws Exception {
        // Get the identifierType
        restIdentifierTypeMockMvc.perform(get("/api/identifier-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIdentifierType() throws Exception {
        // Initialize the database
        identifierTypeService.save(identifierType);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockIdentifierTypeSearchRepository);

        int databaseSizeBeforeUpdate = identifierTypeRepository.findAll().size();

        // Update the identifierType
        IdentifierType updatedIdentifierType = identifierTypeRepository.findById(identifierType.getId()).get();
        // Disconnect from session so that the updates on updatedIdentifierType are not directly saved in db
        em.detach(updatedIdentifierType);
        updatedIdentifierType
            .name(UPDATED_NAME);

        restIdentifierTypeMockMvc.perform(put("/api/identifier-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIdentifierType)))
            .andExpect(status().isOk());

        // Validate the IdentifierType in the database
        List<IdentifierType> identifierTypeList = identifierTypeRepository.findAll();
        assertThat(identifierTypeList).hasSize(databaseSizeBeforeUpdate);
        IdentifierType testIdentifierType = identifierTypeList.get(identifierTypeList.size() - 1);
        assertThat(testIdentifierType.getName()).isEqualTo(UPDATED_NAME);

        // Validate the IdentifierType in Elasticsearch
        verify(mockIdentifierTypeSearchRepository, times(1)).save(testIdentifierType);
    }

    @Test
    @Transactional
    public void updateNonExistingIdentifierType() throws Exception {
        int databaseSizeBeforeUpdate = identifierTypeRepository.findAll().size();

        // Create the IdentifierType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdentifierTypeMockMvc.perform(put("/api/identifier-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identifierType)))
            .andExpect(status().isBadRequest());

        // Validate the IdentifierType in the database
        List<IdentifierType> identifierTypeList = identifierTypeRepository.findAll();
        assertThat(identifierTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the IdentifierType in Elasticsearch
        verify(mockIdentifierTypeSearchRepository, times(0)).save(identifierType);
    }

    @Test
    @Transactional
    public void deleteIdentifierType() throws Exception {
        // Initialize the database
        identifierTypeService.save(identifierType);

        int databaseSizeBeforeDelete = identifierTypeRepository.findAll().size();

        // Delete the identifierType
        restIdentifierTypeMockMvc.perform(delete("/api/identifier-types/{id}", identifierType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IdentifierType> identifierTypeList = identifierTypeRepository.findAll();
        assertThat(identifierTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the IdentifierType in Elasticsearch
        verify(mockIdentifierTypeSearchRepository, times(1)).deleteById(identifierType.getId());
    }

    @Test
    @Transactional
    public void searchIdentifierType() throws Exception {
        // Initialize the database
        identifierTypeService.save(identifierType);
        when(mockIdentifierTypeSearchRepository.search(queryStringQuery("id:" + identifierType.getId())))
            .thenReturn(Collections.singletonList(identifierType));
        // Search the identifierType
        restIdentifierTypeMockMvc.perform(get("/api/_search/identifier-types?query=id:" + identifierType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(identifierType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IdentifierType.class);
        IdentifierType identifierType1 = new IdentifierType();
        identifierType1.setId(1L);
        IdentifierType identifierType2 = new IdentifierType();
        identifierType2.setId(identifierType1.getId());
        assertThat(identifierType1).isEqualTo(identifierType2);
        identifierType2.setId(2L);
        assertThat(identifierType1).isNotEqualTo(identifierType2);
        identifierType1.setId(null);
        assertThat(identifierType1).isNotEqualTo(identifierType2);
    }
}
