package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.Cycle;
import io.github.jhipster.application.repository.CycleRepository;
import io.github.jhipster.application.repository.search.CycleSearchRepository;
import io.github.jhipster.application.service.CycleService;
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
 * Test class for the CycleResource REST controller.
 *
 * @see CycleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class CycleResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private CycleService cycleService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.CycleSearchRepositoryMockConfiguration
     */
    @Autowired
    private CycleSearchRepository mockCycleSearchRepository;

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

    private MockMvc restCycleMockMvc;

    private Cycle cycle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CycleResource cycleResource = new CycleResource(cycleService);
        this.restCycleMockMvc = MockMvcBuilders.standaloneSetup(cycleResource)
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
    public static Cycle createEntity(EntityManager em) {
        Cycle cycle = new Cycle()
            .label(DEFAULT_LABEL)
            .description(DEFAULT_DESCRIPTION);
        return cycle;
    }

    @Before
    public void initTest() {
        cycle = createEntity(em);
    }

    @Test
    @Transactional
    public void createCycle() throws Exception {
        int databaseSizeBeforeCreate = cycleRepository.findAll().size();

        // Create the Cycle
        restCycleMockMvc.perform(post("/api/cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cycle)))
            .andExpect(status().isCreated());

        // Validate the Cycle in the database
        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeCreate + 1);
        Cycle testCycle = cycleList.get(cycleList.size() - 1);
        assertThat(testCycle.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testCycle.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Cycle in Elasticsearch
        verify(mockCycleSearchRepository, times(1)).save(testCycle);
    }

    @Test
    @Transactional
    public void createCycleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cycleRepository.findAll().size();

        // Create the Cycle with an existing ID
        cycle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCycleMockMvc.perform(post("/api/cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cycle)))
            .andExpect(status().isBadRequest());

        // Validate the Cycle in the database
        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeCreate);

        // Validate the Cycle in Elasticsearch
        verify(mockCycleSearchRepository, times(0)).save(cycle);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = cycleRepository.findAll().size();
        // set the field null
        cycle.setLabel(null);

        // Create the Cycle, which fails.

        restCycleMockMvc.perform(post("/api/cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cycle)))
            .andExpect(status().isBadRequest());

        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCycles() throws Exception {
        // Initialize the database
        cycleRepository.saveAndFlush(cycle);

        // Get all the cycleList
        restCycleMockMvc.perform(get("/api/cycles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cycle.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getCycle() throws Exception {
        // Initialize the database
        cycleRepository.saveAndFlush(cycle);

        // Get the cycle
        restCycleMockMvc.perform(get("/api/cycles/{id}", cycle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cycle.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCycle() throws Exception {
        // Get the cycle
        restCycleMockMvc.perform(get("/api/cycles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCycle() throws Exception {
        // Initialize the database
        cycleService.save(cycle);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCycleSearchRepository);

        int databaseSizeBeforeUpdate = cycleRepository.findAll().size();

        // Update the cycle
        Cycle updatedCycle = cycleRepository.findById(cycle.getId()).get();
        // Disconnect from session so that the updates on updatedCycle are not directly saved in db
        em.detach(updatedCycle);
        updatedCycle
            .label(UPDATED_LABEL)
            .description(UPDATED_DESCRIPTION);

        restCycleMockMvc.perform(put("/api/cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCycle)))
            .andExpect(status().isOk());

        // Validate the Cycle in the database
        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeUpdate);
        Cycle testCycle = cycleList.get(cycleList.size() - 1);
        assertThat(testCycle.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testCycle.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Cycle in Elasticsearch
        verify(mockCycleSearchRepository, times(1)).save(testCycle);
    }

    @Test
    @Transactional
    public void updateNonExistingCycle() throws Exception {
        int databaseSizeBeforeUpdate = cycleRepository.findAll().size();

        // Create the Cycle

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCycleMockMvc.perform(put("/api/cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cycle)))
            .andExpect(status().isBadRequest());

        // Validate the Cycle in the database
        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Cycle in Elasticsearch
        verify(mockCycleSearchRepository, times(0)).save(cycle);
    }

    @Test
    @Transactional
    public void deleteCycle() throws Exception {
        // Initialize the database
        cycleService.save(cycle);

        int databaseSizeBeforeDelete = cycleRepository.findAll().size();

        // Delete the cycle
        restCycleMockMvc.perform(delete("/api/cycles/{id}", cycle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Cycle in Elasticsearch
        verify(mockCycleSearchRepository, times(1)).deleteById(cycle.getId());
    }

    @Test
    @Transactional
    public void searchCycle() throws Exception {
        // Initialize the database
        cycleService.save(cycle);
        when(mockCycleSearchRepository.search(queryStringQuery("id:" + cycle.getId())))
            .thenReturn(Collections.singletonList(cycle));
        // Search the cycle
        restCycleMockMvc.perform(get("/api/_search/cycles?query=id:" + cycle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cycle.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cycle.class);
        Cycle cycle1 = new Cycle();
        cycle1.setId(1L);
        Cycle cycle2 = new Cycle();
        cycle2.setId(cycle1.getId());
        assertThat(cycle1).isEqualTo(cycle2);
        cycle2.setId(2L);
        assertThat(cycle1).isNotEqualTo(cycle2);
        cycle1.setId(null);
        assertThat(cycle1).isNotEqualTo(cycle2);
    }
}
