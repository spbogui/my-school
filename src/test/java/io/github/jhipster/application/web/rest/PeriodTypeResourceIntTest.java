package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.PeriodType;
import io.github.jhipster.application.repository.PeriodTypeRepository;
import io.github.jhipster.application.repository.search.PeriodTypeSearchRepository;
import io.github.jhipster.application.service.PeriodTypeService;
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
 * Test class for the PeriodTypeResource REST controller.
 *
 * @see PeriodTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class PeriodTypeResourceIntTest {

    private static final String DEFAULT_PERIOD_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_PERIOD_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PeriodTypeRepository periodTypeRepository;

    @Autowired
    private PeriodTypeService periodTypeService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.PeriodTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private PeriodTypeSearchRepository mockPeriodTypeSearchRepository;

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

    private MockMvc restPeriodTypeMockMvc;

    private PeriodType periodType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PeriodTypeResource periodTypeResource = new PeriodTypeResource(periodTypeService);
        this.restPeriodTypeMockMvc = MockMvcBuilders.standaloneSetup(periodTypeResource)
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
    public static PeriodType createEntity(EntityManager em) {
        PeriodType periodType = new PeriodType()
            .periodLabel(DEFAULT_PERIOD_LABEL)
            .description(DEFAULT_DESCRIPTION);
        return periodType;
    }

    @Before
    public void initTest() {
        periodType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeriodType() throws Exception {
        int databaseSizeBeforeCreate = periodTypeRepository.findAll().size();

        // Create the PeriodType
        restPeriodTypeMockMvc.perform(post("/api/period-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodType)))
            .andExpect(status().isCreated());

        // Validate the PeriodType in the database
        List<PeriodType> periodTypeList = periodTypeRepository.findAll();
        assertThat(periodTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PeriodType testPeriodType = periodTypeList.get(periodTypeList.size() - 1);
        assertThat(testPeriodType.getPeriodLabel()).isEqualTo(DEFAULT_PERIOD_LABEL);
        assertThat(testPeriodType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the PeriodType in Elasticsearch
        verify(mockPeriodTypeSearchRepository, times(1)).save(testPeriodType);
    }

    @Test
    @Transactional
    public void createPeriodTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = periodTypeRepository.findAll().size();

        // Create the PeriodType with an existing ID
        periodType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodTypeMockMvc.perform(post("/api/period-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodType)))
            .andExpect(status().isBadRequest());

        // Validate the PeriodType in the database
        List<PeriodType> periodTypeList = periodTypeRepository.findAll();
        assertThat(periodTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the PeriodType in Elasticsearch
        verify(mockPeriodTypeSearchRepository, times(0)).save(periodType);
    }

    @Test
    @Transactional
    public void checkPeriodLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodTypeRepository.findAll().size();
        // set the field null
        periodType.setPeriodLabel(null);

        // Create the PeriodType, which fails.

        restPeriodTypeMockMvc.perform(post("/api/period-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodType)))
            .andExpect(status().isBadRequest());

        List<PeriodType> periodTypeList = periodTypeRepository.findAll();
        assertThat(periodTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPeriodTypes() throws Exception {
        // Initialize the database
        periodTypeRepository.saveAndFlush(periodType);

        // Get all the periodTypeList
        restPeriodTypeMockMvc.perform(get("/api/period-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodType.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodLabel").value(hasItem(DEFAULT_PERIOD_LABEL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getPeriodType() throws Exception {
        // Initialize the database
        periodTypeRepository.saveAndFlush(periodType);

        // Get the periodType
        restPeriodTypeMockMvc.perform(get("/api/period-types/{id}", periodType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(periodType.getId().intValue()))
            .andExpect(jsonPath("$.periodLabel").value(DEFAULT_PERIOD_LABEL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPeriodType() throws Exception {
        // Get the periodType
        restPeriodTypeMockMvc.perform(get("/api/period-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeriodType() throws Exception {
        // Initialize the database
        periodTypeService.save(periodType);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPeriodTypeSearchRepository);

        int databaseSizeBeforeUpdate = periodTypeRepository.findAll().size();

        // Update the periodType
        PeriodType updatedPeriodType = periodTypeRepository.findById(periodType.getId()).get();
        // Disconnect from session so that the updates on updatedPeriodType are not directly saved in db
        em.detach(updatedPeriodType);
        updatedPeriodType
            .periodLabel(UPDATED_PERIOD_LABEL)
            .description(UPDATED_DESCRIPTION);

        restPeriodTypeMockMvc.perform(put("/api/period-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPeriodType)))
            .andExpect(status().isOk());

        // Validate the PeriodType in the database
        List<PeriodType> periodTypeList = periodTypeRepository.findAll();
        assertThat(periodTypeList).hasSize(databaseSizeBeforeUpdate);
        PeriodType testPeriodType = periodTypeList.get(periodTypeList.size() - 1);
        assertThat(testPeriodType.getPeriodLabel()).isEqualTo(UPDATED_PERIOD_LABEL);
        assertThat(testPeriodType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the PeriodType in Elasticsearch
        verify(mockPeriodTypeSearchRepository, times(1)).save(testPeriodType);
    }

    @Test
    @Transactional
    public void updateNonExistingPeriodType() throws Exception {
        int databaseSizeBeforeUpdate = periodTypeRepository.findAll().size();

        // Create the PeriodType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodTypeMockMvc.perform(put("/api/period-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodType)))
            .andExpect(status().isBadRequest());

        // Validate the PeriodType in the database
        List<PeriodType> periodTypeList = periodTypeRepository.findAll();
        assertThat(periodTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PeriodType in Elasticsearch
        verify(mockPeriodTypeSearchRepository, times(0)).save(periodType);
    }

    @Test
    @Transactional
    public void deletePeriodType() throws Exception {
        // Initialize the database
        periodTypeService.save(periodType);

        int databaseSizeBeforeDelete = periodTypeRepository.findAll().size();

        // Delete the periodType
        restPeriodTypeMockMvc.perform(delete("/api/period-types/{id}", periodType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PeriodType> periodTypeList = periodTypeRepository.findAll();
        assertThat(periodTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PeriodType in Elasticsearch
        verify(mockPeriodTypeSearchRepository, times(1)).deleteById(periodType.getId());
    }

    @Test
    @Transactional
    public void searchPeriodType() throws Exception {
        // Initialize the database
        periodTypeService.save(periodType);
        when(mockPeriodTypeSearchRepository.search(queryStringQuery("id:" + periodType.getId())))
            .thenReturn(Collections.singletonList(periodType));
        // Search the periodType
        restPeriodTypeMockMvc.perform(get("/api/_search/period-types?query=id:" + periodType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodType.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodLabel").value(hasItem(DEFAULT_PERIOD_LABEL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodType.class);
        PeriodType periodType1 = new PeriodType();
        periodType1.setId(1L);
        PeriodType periodType2 = new PeriodType();
        periodType2.setId(periodType1.getId());
        assertThat(periodType1).isEqualTo(periodType2);
        periodType2.setId(2L);
        assertThat(periodType1).isNotEqualTo(periodType2);
        periodType1.setId(null);
        assertThat(periodType1).isNotEqualTo(periodType2);
    }
}
