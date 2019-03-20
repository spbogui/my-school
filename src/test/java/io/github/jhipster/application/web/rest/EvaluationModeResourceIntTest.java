package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.EvaluationMode;
import io.github.jhipster.application.repository.EvaluationModeRepository;
import io.github.jhipster.application.repository.search.EvaluationModeSearchRepository;
import io.github.jhipster.application.service.EvaluationModeService;
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
 * Test class for the EvaluationModeResource REST controller.
 *
 * @see EvaluationModeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class EvaluationModeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private EvaluationModeRepository evaluationModeRepository;

    @Autowired
    private EvaluationModeService evaluationModeService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.EvaluationModeSearchRepositoryMockConfiguration
     */
    @Autowired
    private EvaluationModeSearchRepository mockEvaluationModeSearchRepository;

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

    private MockMvc restEvaluationModeMockMvc;

    private EvaluationMode evaluationMode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EvaluationModeResource evaluationModeResource = new EvaluationModeResource(evaluationModeService);
        this.restEvaluationModeMockMvc = MockMvcBuilders.standaloneSetup(evaluationModeResource)
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
    public static EvaluationMode createEntity(EntityManager em) {
        EvaluationMode evaluationMode = new EvaluationMode()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return evaluationMode;
    }

    @Before
    public void initTest() {
        evaluationMode = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvaluationMode() throws Exception {
        int databaseSizeBeforeCreate = evaluationModeRepository.findAll().size();

        // Create the EvaluationMode
        restEvaluationModeMockMvc.perform(post("/api/evaluation-modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluationMode)))
            .andExpect(status().isCreated());

        // Validate the EvaluationMode in the database
        List<EvaluationMode> evaluationModeList = evaluationModeRepository.findAll();
        assertThat(evaluationModeList).hasSize(databaseSizeBeforeCreate + 1);
        EvaluationMode testEvaluationMode = evaluationModeList.get(evaluationModeList.size() - 1);
        assertThat(testEvaluationMode.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEvaluationMode.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the EvaluationMode in Elasticsearch
        verify(mockEvaluationModeSearchRepository, times(1)).save(testEvaluationMode);
    }

    @Test
    @Transactional
    public void createEvaluationModeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = evaluationModeRepository.findAll().size();

        // Create the EvaluationMode with an existing ID
        evaluationMode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvaluationModeMockMvc.perform(post("/api/evaluation-modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluationMode)))
            .andExpect(status().isBadRequest());

        // Validate the EvaluationMode in the database
        List<EvaluationMode> evaluationModeList = evaluationModeRepository.findAll();
        assertThat(evaluationModeList).hasSize(databaseSizeBeforeCreate);

        // Validate the EvaluationMode in Elasticsearch
        verify(mockEvaluationModeSearchRepository, times(0)).save(evaluationMode);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = evaluationModeRepository.findAll().size();
        // set the field null
        evaluationMode.setName(null);

        // Create the EvaluationMode, which fails.

        restEvaluationModeMockMvc.perform(post("/api/evaluation-modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluationMode)))
            .andExpect(status().isBadRequest());

        List<EvaluationMode> evaluationModeList = evaluationModeRepository.findAll();
        assertThat(evaluationModeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEvaluationModes() throws Exception {
        // Initialize the database
        evaluationModeRepository.saveAndFlush(evaluationMode);

        // Get all the evaluationModeList
        restEvaluationModeMockMvc.perform(get("/api/evaluation-modes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluationMode.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getEvaluationMode() throws Exception {
        // Initialize the database
        evaluationModeRepository.saveAndFlush(evaluationMode);

        // Get the evaluationMode
        restEvaluationModeMockMvc.perform(get("/api/evaluation-modes/{id}", evaluationMode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(evaluationMode.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEvaluationMode() throws Exception {
        // Get the evaluationMode
        restEvaluationModeMockMvc.perform(get("/api/evaluation-modes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvaluationMode() throws Exception {
        // Initialize the database
        evaluationModeService.save(evaluationMode);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockEvaluationModeSearchRepository);

        int databaseSizeBeforeUpdate = evaluationModeRepository.findAll().size();

        // Update the evaluationMode
        EvaluationMode updatedEvaluationMode = evaluationModeRepository.findById(evaluationMode.getId()).get();
        // Disconnect from session so that the updates on updatedEvaluationMode are not directly saved in db
        em.detach(updatedEvaluationMode);
        updatedEvaluationMode
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restEvaluationModeMockMvc.perform(put("/api/evaluation-modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEvaluationMode)))
            .andExpect(status().isOk());

        // Validate the EvaluationMode in the database
        List<EvaluationMode> evaluationModeList = evaluationModeRepository.findAll();
        assertThat(evaluationModeList).hasSize(databaseSizeBeforeUpdate);
        EvaluationMode testEvaluationMode = evaluationModeList.get(evaluationModeList.size() - 1);
        assertThat(testEvaluationMode.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEvaluationMode.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the EvaluationMode in Elasticsearch
        verify(mockEvaluationModeSearchRepository, times(1)).save(testEvaluationMode);
    }

    @Test
    @Transactional
    public void updateNonExistingEvaluationMode() throws Exception {
        int databaseSizeBeforeUpdate = evaluationModeRepository.findAll().size();

        // Create the EvaluationMode

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluationModeMockMvc.perform(put("/api/evaluation-modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluationMode)))
            .andExpect(status().isBadRequest());

        // Validate the EvaluationMode in the database
        List<EvaluationMode> evaluationModeList = evaluationModeRepository.findAll();
        assertThat(evaluationModeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EvaluationMode in Elasticsearch
        verify(mockEvaluationModeSearchRepository, times(0)).save(evaluationMode);
    }

    @Test
    @Transactional
    public void deleteEvaluationMode() throws Exception {
        // Initialize the database
        evaluationModeService.save(evaluationMode);

        int databaseSizeBeforeDelete = evaluationModeRepository.findAll().size();

        // Delete the evaluationMode
        restEvaluationModeMockMvc.perform(delete("/api/evaluation-modes/{id}", evaluationMode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EvaluationMode> evaluationModeList = evaluationModeRepository.findAll();
        assertThat(evaluationModeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EvaluationMode in Elasticsearch
        verify(mockEvaluationModeSearchRepository, times(1)).deleteById(evaluationMode.getId());
    }

    @Test
    @Transactional
    public void searchEvaluationMode() throws Exception {
        // Initialize the database
        evaluationModeService.save(evaluationMode);
        when(mockEvaluationModeSearchRepository.search(queryStringQuery("id:" + evaluationMode.getId())))
            .thenReturn(Collections.singletonList(evaluationMode));
        // Search the evaluationMode
        restEvaluationModeMockMvc.perform(get("/api/_search/evaluation-modes?query=id:" + evaluationMode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluationMode.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluationMode.class);
        EvaluationMode evaluationMode1 = new EvaluationMode();
        evaluationMode1.setId(1L);
        EvaluationMode evaluationMode2 = new EvaluationMode();
        evaluationMode2.setId(evaluationMode1.getId());
        assertThat(evaluationMode1).isEqualTo(evaluationMode2);
        evaluationMode2.setId(2L);
        assertThat(evaluationMode1).isNotEqualTo(evaluationMode2);
        evaluationMode1.setId(null);
        assertThat(evaluationMode1).isNotEqualTo(evaluationMode2);
    }
}
