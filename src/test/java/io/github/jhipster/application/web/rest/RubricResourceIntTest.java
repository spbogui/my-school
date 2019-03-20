package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.Rubric;
import io.github.jhipster.application.repository.RubricRepository;
import io.github.jhipster.application.repository.search.RubricSearchRepository;
import io.github.jhipster.application.service.RubricService;
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
 * Test class for the RubricResource REST controller.
 *
 * @see RubricResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class RubricResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RubricRepository rubricRepository;

    @Autowired
    private RubricService rubricService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.RubricSearchRepositoryMockConfiguration
     */
    @Autowired
    private RubricSearchRepository mockRubricSearchRepository;

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

    private MockMvc restRubricMockMvc;

    private Rubric rubric;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RubricResource rubricResource = new RubricResource(rubricService);
        this.restRubricMockMvc = MockMvcBuilders.standaloneSetup(rubricResource)
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
    public static Rubric createEntity(EntityManager em) {
        Rubric rubric = new Rubric()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return rubric;
    }

    @Before
    public void initTest() {
        rubric = createEntity(em);
    }

    @Test
    @Transactional
    public void createRubric() throws Exception {
        int databaseSizeBeforeCreate = rubricRepository.findAll().size();

        // Create the Rubric
        restRubricMockMvc.perform(post("/api/rubrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubric)))
            .andExpect(status().isCreated());

        // Validate the Rubric in the database
        List<Rubric> rubricList = rubricRepository.findAll();
        assertThat(rubricList).hasSize(databaseSizeBeforeCreate + 1);
        Rubric testRubric = rubricList.get(rubricList.size() - 1);
        assertThat(testRubric.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRubric.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Rubric in Elasticsearch
        verify(mockRubricSearchRepository, times(1)).save(testRubric);
    }

    @Test
    @Transactional
    public void createRubricWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rubricRepository.findAll().size();

        // Create the Rubric with an existing ID
        rubric.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRubricMockMvc.perform(post("/api/rubrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubric)))
            .andExpect(status().isBadRequest());

        // Validate the Rubric in the database
        List<Rubric> rubricList = rubricRepository.findAll();
        assertThat(rubricList).hasSize(databaseSizeBeforeCreate);

        // Validate the Rubric in Elasticsearch
        verify(mockRubricSearchRepository, times(0)).save(rubric);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = rubricRepository.findAll().size();
        // set the field null
        rubric.setName(null);

        // Create the Rubric, which fails.

        restRubricMockMvc.perform(post("/api/rubrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubric)))
            .andExpect(status().isBadRequest());

        List<Rubric> rubricList = rubricRepository.findAll();
        assertThat(rubricList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRubrics() throws Exception {
        // Initialize the database
        rubricRepository.saveAndFlush(rubric);

        // Get all the rubricList
        restRubricMockMvc.perform(get("/api/rubrics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rubric.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getRubric() throws Exception {
        // Initialize the database
        rubricRepository.saveAndFlush(rubric);

        // Get the rubric
        restRubricMockMvc.perform(get("/api/rubrics/{id}", rubric.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rubric.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRubric() throws Exception {
        // Get the rubric
        restRubricMockMvc.perform(get("/api/rubrics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRubric() throws Exception {
        // Initialize the database
        rubricService.save(rubric);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockRubricSearchRepository);

        int databaseSizeBeforeUpdate = rubricRepository.findAll().size();

        // Update the rubric
        Rubric updatedRubric = rubricRepository.findById(rubric.getId()).get();
        // Disconnect from session so that the updates on updatedRubric are not directly saved in db
        em.detach(updatedRubric);
        updatedRubric
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restRubricMockMvc.perform(put("/api/rubrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRubric)))
            .andExpect(status().isOk());

        // Validate the Rubric in the database
        List<Rubric> rubricList = rubricRepository.findAll();
        assertThat(rubricList).hasSize(databaseSizeBeforeUpdate);
        Rubric testRubric = rubricList.get(rubricList.size() - 1);
        assertThat(testRubric.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRubric.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Rubric in Elasticsearch
        verify(mockRubricSearchRepository, times(1)).save(testRubric);
    }

    @Test
    @Transactional
    public void updateNonExistingRubric() throws Exception {
        int databaseSizeBeforeUpdate = rubricRepository.findAll().size();

        // Create the Rubric

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRubricMockMvc.perform(put("/api/rubrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubric)))
            .andExpect(status().isBadRequest());

        // Validate the Rubric in the database
        List<Rubric> rubricList = rubricRepository.findAll();
        assertThat(rubricList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Rubric in Elasticsearch
        verify(mockRubricSearchRepository, times(0)).save(rubric);
    }

    @Test
    @Transactional
    public void deleteRubric() throws Exception {
        // Initialize the database
        rubricService.save(rubric);

        int databaseSizeBeforeDelete = rubricRepository.findAll().size();

        // Delete the rubric
        restRubricMockMvc.perform(delete("/api/rubrics/{id}", rubric.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Rubric> rubricList = rubricRepository.findAll();
        assertThat(rubricList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Rubric in Elasticsearch
        verify(mockRubricSearchRepository, times(1)).deleteById(rubric.getId());
    }

    @Test
    @Transactional
    public void searchRubric() throws Exception {
        // Initialize the database
        rubricService.save(rubric);
        when(mockRubricSearchRepository.search(queryStringQuery("id:" + rubric.getId())))
            .thenReturn(Collections.singletonList(rubric));
        // Search the rubric
        restRubricMockMvc.perform(get("/api/_search/rubrics?query=id:" + rubric.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rubric.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rubric.class);
        Rubric rubric1 = new Rubric();
        rubric1.setId(1L);
        Rubric rubric2 = new Rubric();
        rubric2.setId(rubric1.getId());
        assertThat(rubric1).isEqualTo(rubric2);
        rubric2.setId(2L);
        assertThat(rubric1).isNotEqualTo(rubric2);
        rubric1.setId(null);
        assertThat(rubric1).isNotEqualTo(rubric2);
    }
}
