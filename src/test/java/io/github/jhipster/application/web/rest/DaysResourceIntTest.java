package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.Days;
import io.github.jhipster.application.repository.DaysRepository;
import io.github.jhipster.application.repository.search.DaysSearchRepository;
import io.github.jhipster.application.service.DaysService;
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
 * Test class for the DaysResource REST controller.
 *
 * @see DaysResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class DaysResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DaysRepository daysRepository;

    @Autowired
    private DaysService daysService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.DaysSearchRepositoryMockConfiguration
     */
    @Autowired
    private DaysSearchRepository mockDaysSearchRepository;

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

    private MockMvc restDaysMockMvc;

    private Days days;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DaysResource daysResource = new DaysResource(daysService);
        this.restDaysMockMvc = MockMvcBuilders.standaloneSetup(daysResource)
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
    public static Days createEntity(EntityManager em) {
        Days days = new Days()
            .name(DEFAULT_NAME);
        return days;
    }

    @Before
    public void initTest() {
        days = createEntity(em);
    }

    @Test
    @Transactional
    public void createDays() throws Exception {
        int databaseSizeBeforeCreate = daysRepository.findAll().size();

        // Create the Days
        restDaysMockMvc.perform(post("/api/days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(days)))
            .andExpect(status().isCreated());

        // Validate the Days in the database
        List<Days> daysList = daysRepository.findAll();
        assertThat(daysList).hasSize(databaseSizeBeforeCreate + 1);
        Days testDays = daysList.get(daysList.size() - 1);
        assertThat(testDays.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Days in Elasticsearch
        verify(mockDaysSearchRepository, times(1)).save(testDays);
    }

    @Test
    @Transactional
    public void createDaysWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = daysRepository.findAll().size();

        // Create the Days with an existing ID
        days.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDaysMockMvc.perform(post("/api/days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(days)))
            .andExpect(status().isBadRequest());

        // Validate the Days in the database
        List<Days> daysList = daysRepository.findAll();
        assertThat(daysList).hasSize(databaseSizeBeforeCreate);

        // Validate the Days in Elasticsearch
        verify(mockDaysSearchRepository, times(0)).save(days);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = daysRepository.findAll().size();
        // set the field null
        days.setName(null);

        // Create the Days, which fails.

        restDaysMockMvc.perform(post("/api/days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(days)))
            .andExpect(status().isBadRequest());

        List<Days> daysList = daysRepository.findAll();
        assertThat(daysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDays() throws Exception {
        // Initialize the database
        daysRepository.saveAndFlush(days);

        // Get all the daysList
        restDaysMockMvc.perform(get("/api/days?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(days.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getDays() throws Exception {
        // Initialize the database
        daysRepository.saveAndFlush(days);

        // Get the days
        restDaysMockMvc.perform(get("/api/days/{id}", days.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(days.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDays() throws Exception {
        // Get the days
        restDaysMockMvc.perform(get("/api/days/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDays() throws Exception {
        // Initialize the database
        daysService.save(days);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockDaysSearchRepository);

        int databaseSizeBeforeUpdate = daysRepository.findAll().size();

        // Update the days
        Days updatedDays = daysRepository.findById(days.getId()).get();
        // Disconnect from session so that the updates on updatedDays are not directly saved in db
        em.detach(updatedDays);
        updatedDays
            .name(UPDATED_NAME);

        restDaysMockMvc.perform(put("/api/days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDays)))
            .andExpect(status().isOk());

        // Validate the Days in the database
        List<Days> daysList = daysRepository.findAll();
        assertThat(daysList).hasSize(databaseSizeBeforeUpdate);
        Days testDays = daysList.get(daysList.size() - 1);
        assertThat(testDays.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Days in Elasticsearch
        verify(mockDaysSearchRepository, times(1)).save(testDays);
    }

    @Test
    @Transactional
    public void updateNonExistingDays() throws Exception {
        int databaseSizeBeforeUpdate = daysRepository.findAll().size();

        // Create the Days

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDaysMockMvc.perform(put("/api/days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(days)))
            .andExpect(status().isBadRequest());

        // Validate the Days in the database
        List<Days> daysList = daysRepository.findAll();
        assertThat(daysList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Days in Elasticsearch
        verify(mockDaysSearchRepository, times(0)).save(days);
    }

    @Test
    @Transactional
    public void deleteDays() throws Exception {
        // Initialize the database
        daysService.save(days);

        int databaseSizeBeforeDelete = daysRepository.findAll().size();

        // Delete the days
        restDaysMockMvc.perform(delete("/api/days/{id}", days.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Days> daysList = daysRepository.findAll();
        assertThat(daysList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Days in Elasticsearch
        verify(mockDaysSearchRepository, times(1)).deleteById(days.getId());
    }

    @Test
    @Transactional
    public void searchDays() throws Exception {
        // Initialize the database
        daysService.save(days);
        when(mockDaysSearchRepository.search(queryStringQuery("id:" + days.getId())))
            .thenReturn(Collections.singletonList(days));
        // Search the days
        restDaysMockMvc.perform(get("/api/_search/days?query=id:" + days.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(days.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Days.class);
        Days days1 = new Days();
        days1.setId(1L);
        Days days2 = new Days();
        days2.setId(days1.getId());
        assertThat(days1).isEqualTo(days2);
        days2.setId(2L);
        assertThat(days1).isNotEqualTo(days2);
        days1.setId(null);
        assertThat(days1).isNotEqualTo(days2);
    }
}
