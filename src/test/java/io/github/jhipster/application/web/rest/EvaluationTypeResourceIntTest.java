package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.EvaluationType;
import io.github.jhipster.application.repository.EvaluationTypeRepository;
import io.github.jhipster.application.repository.search.EvaluationTypeSearchRepository;
import io.github.jhipster.application.service.EvaluationTypeService;
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
 * Test class for the EvaluationTypeResource REST controller.
 *
 * @see EvaluationTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class EvaluationTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private EvaluationTypeRepository evaluationTypeRepository;

    @Autowired
    private EvaluationTypeService evaluationTypeService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.EvaluationTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private EvaluationTypeSearchRepository mockEvaluationTypeSearchRepository;

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

    private MockMvc restEvaluationTypeMockMvc;

    private EvaluationType evaluationType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EvaluationTypeResource evaluationTypeResource = new EvaluationTypeResource(evaluationTypeService);
        this.restEvaluationTypeMockMvc = MockMvcBuilders.standaloneSetup(evaluationTypeResource)
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
    public static EvaluationType createEntity(EntityManager em) {
        EvaluationType evaluationType = new EvaluationType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return evaluationType;
    }

    @Before
    public void initTest() {
        evaluationType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvaluationType() throws Exception {
        int databaseSizeBeforeCreate = evaluationTypeRepository.findAll().size();

        // Create the EvaluationType
        restEvaluationTypeMockMvc.perform(post("/api/evaluation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluationType)))
            .andExpect(status().isCreated());

        // Validate the EvaluationType in the database
        List<EvaluationType> evaluationTypeList = evaluationTypeRepository.findAll();
        assertThat(evaluationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EvaluationType testEvaluationType = evaluationTypeList.get(evaluationTypeList.size() - 1);
        assertThat(testEvaluationType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEvaluationType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the EvaluationType in Elasticsearch
        verify(mockEvaluationTypeSearchRepository, times(1)).save(testEvaluationType);
    }

    @Test
    @Transactional
    public void createEvaluationTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = evaluationTypeRepository.findAll().size();

        // Create the EvaluationType with an existing ID
        evaluationType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvaluationTypeMockMvc.perform(post("/api/evaluation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluationType)))
            .andExpect(status().isBadRequest());

        // Validate the EvaluationType in the database
        List<EvaluationType> evaluationTypeList = evaluationTypeRepository.findAll();
        assertThat(evaluationTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the EvaluationType in Elasticsearch
        verify(mockEvaluationTypeSearchRepository, times(0)).save(evaluationType);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = evaluationTypeRepository.findAll().size();
        // set the field null
        evaluationType.setName(null);

        // Create the EvaluationType, which fails.

        restEvaluationTypeMockMvc.perform(post("/api/evaluation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluationType)))
            .andExpect(status().isBadRequest());

        List<EvaluationType> evaluationTypeList = evaluationTypeRepository.findAll();
        assertThat(evaluationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEvaluationTypes() throws Exception {
        // Initialize the database
        evaluationTypeRepository.saveAndFlush(evaluationType);

        // Get all the evaluationTypeList
        restEvaluationTypeMockMvc.perform(get("/api/evaluation-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getEvaluationType() throws Exception {
        // Initialize the database
        evaluationTypeRepository.saveAndFlush(evaluationType);

        // Get the evaluationType
        restEvaluationTypeMockMvc.perform(get("/api/evaluation-types/{id}", evaluationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(evaluationType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEvaluationType() throws Exception {
        // Get the evaluationType
        restEvaluationTypeMockMvc.perform(get("/api/evaluation-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvaluationType() throws Exception {
        // Initialize the database
        evaluationTypeService.save(evaluationType);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockEvaluationTypeSearchRepository);

        int databaseSizeBeforeUpdate = evaluationTypeRepository.findAll().size();

        // Update the evaluationType
        EvaluationType updatedEvaluationType = evaluationTypeRepository.findById(evaluationType.getId()).get();
        // Disconnect from session so that the updates on updatedEvaluationType are not directly saved in db
        em.detach(updatedEvaluationType);
        updatedEvaluationType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restEvaluationTypeMockMvc.perform(put("/api/evaluation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEvaluationType)))
            .andExpect(status().isOk());

        // Validate the EvaluationType in the database
        List<EvaluationType> evaluationTypeList = evaluationTypeRepository.findAll();
        assertThat(evaluationTypeList).hasSize(databaseSizeBeforeUpdate);
        EvaluationType testEvaluationType = evaluationTypeList.get(evaluationTypeList.size() - 1);
        assertThat(testEvaluationType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEvaluationType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the EvaluationType in Elasticsearch
        verify(mockEvaluationTypeSearchRepository, times(1)).save(testEvaluationType);
    }

    @Test
    @Transactional
    public void updateNonExistingEvaluationType() throws Exception {
        int databaseSizeBeforeUpdate = evaluationTypeRepository.findAll().size();

        // Create the EvaluationType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluationTypeMockMvc.perform(put("/api/evaluation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluationType)))
            .andExpect(status().isBadRequest());

        // Validate the EvaluationType in the database
        List<EvaluationType> evaluationTypeList = evaluationTypeRepository.findAll();
        assertThat(evaluationTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EvaluationType in Elasticsearch
        verify(mockEvaluationTypeSearchRepository, times(0)).save(evaluationType);
    }

    @Test
    @Transactional
    public void deleteEvaluationType() throws Exception {
        // Initialize the database
        evaluationTypeService.save(evaluationType);

        int databaseSizeBeforeDelete = evaluationTypeRepository.findAll().size();

        // Delete the evaluationType
        restEvaluationTypeMockMvc.perform(delete("/api/evaluation-types/{id}", evaluationType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EvaluationType> evaluationTypeList = evaluationTypeRepository.findAll();
        assertThat(evaluationTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EvaluationType in Elasticsearch
        verify(mockEvaluationTypeSearchRepository, times(1)).deleteById(evaluationType.getId());
    }

    @Test
    @Transactional
    public void searchEvaluationType() throws Exception {
        // Initialize the database
        evaluationTypeService.save(evaluationType);
        when(mockEvaluationTypeSearchRepository.search(queryStringQuery("id:" + evaluationType.getId())))
            .thenReturn(Collections.singletonList(evaluationType));
        // Search the evaluationType
        restEvaluationTypeMockMvc.perform(get("/api/_search/evaluation-types?query=id:" + evaluationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluationType.class);
        EvaluationType evaluationType1 = new EvaluationType();
        evaluationType1.setId(1L);
        EvaluationType evaluationType2 = new EvaluationType();
        evaluationType2.setId(evaluationType1.getId());
        assertThat(evaluationType1).isEqualTo(evaluationType2);
        evaluationType2.setId(2L);
        assertThat(evaluationType1).isNotEqualTo(evaluationType2);
        evaluationType1.setId(null);
        assertThat(evaluationType1).isNotEqualTo(evaluationType2);
    }
}
