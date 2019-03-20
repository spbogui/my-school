package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.StaffJob;
import io.github.jhipster.application.repository.StaffJobRepository;
import io.github.jhipster.application.repository.search.StaffJobSearchRepository;
import io.github.jhipster.application.service.StaffJobService;
import io.github.jhipster.application.service.dto.StaffJobDTO;
import io.github.jhipster.application.service.mapper.StaffJobMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Test class for the StaffJobResource REST controller.
 *
 * @see StaffJobResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class StaffJobResourceIntTest {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private StaffJobRepository staffJobRepository;

    @Autowired
    private StaffJobMapper staffJobMapper;

    @Autowired
    private StaffJobService staffJobService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.StaffJobSearchRepositoryMockConfiguration
     */
    @Autowired
    private StaffJobSearchRepository mockStaffJobSearchRepository;

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

    private MockMvc restStaffJobMockMvc;

    private StaffJob staffJob;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StaffJobResource staffJobResource = new StaffJobResource(staffJobService);
        this.restStaffJobMockMvc = MockMvcBuilders.standaloneSetup(staffJobResource)
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
    public static StaffJob createEntity(EntityManager em) {
        StaffJob staffJob = new StaffJob()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return staffJob;
    }

    @Before
    public void initTest() {
        staffJob = createEntity(em);
    }

    @Test
    @Transactional
    public void createStaffJob() throws Exception {
        int databaseSizeBeforeCreate = staffJobRepository.findAll().size();

        // Create the StaffJob
        StaffJobDTO staffJobDTO = staffJobMapper.toDto(staffJob);
        restStaffJobMockMvc.perform(post("/api/staff-jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staffJobDTO)))
            .andExpect(status().isCreated());

        // Validate the StaffJob in the database
        List<StaffJob> staffJobList = staffJobRepository.findAll();
        assertThat(staffJobList).hasSize(databaseSizeBeforeCreate + 1);
        StaffJob testStaffJob = staffJobList.get(staffJobList.size() - 1);
        assertThat(testStaffJob.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testStaffJob.getEndDate()).isEqualTo(DEFAULT_END_DATE);

        // Validate the StaffJob in Elasticsearch
        verify(mockStaffJobSearchRepository, times(1)).save(testStaffJob);
    }

    @Test
    @Transactional
    public void createStaffJobWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = staffJobRepository.findAll().size();

        // Create the StaffJob with an existing ID
        staffJob.setId(1L);
        StaffJobDTO staffJobDTO = staffJobMapper.toDto(staffJob);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaffJobMockMvc.perform(post("/api/staff-jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staffJobDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StaffJob in the database
        List<StaffJob> staffJobList = staffJobRepository.findAll();
        assertThat(staffJobList).hasSize(databaseSizeBeforeCreate);

        // Validate the StaffJob in Elasticsearch
        verify(mockStaffJobSearchRepository, times(0)).save(staffJob);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = staffJobRepository.findAll().size();
        // set the field null
        staffJob.setStartDate(null);

        // Create the StaffJob, which fails.
        StaffJobDTO staffJobDTO = staffJobMapper.toDto(staffJob);

        restStaffJobMockMvc.perform(post("/api/staff-jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staffJobDTO)))
            .andExpect(status().isBadRequest());

        List<StaffJob> staffJobList = staffJobRepository.findAll();
        assertThat(staffJobList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStaffJobs() throws Exception {
        // Initialize the database
        staffJobRepository.saveAndFlush(staffJob);

        // Get all the staffJobList
        restStaffJobMockMvc.perform(get("/api/staff-jobs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staffJob.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getStaffJob() throws Exception {
        // Initialize the database
        staffJobRepository.saveAndFlush(staffJob);

        // Get the staffJob
        restStaffJobMockMvc.perform(get("/api/staff-jobs/{id}", staffJob.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(staffJob.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStaffJob() throws Exception {
        // Get the staffJob
        restStaffJobMockMvc.perform(get("/api/staff-jobs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStaffJob() throws Exception {
        // Initialize the database
        staffJobRepository.saveAndFlush(staffJob);

        int databaseSizeBeforeUpdate = staffJobRepository.findAll().size();

        // Update the staffJob
        StaffJob updatedStaffJob = staffJobRepository.findById(staffJob.getId()).get();
        // Disconnect from session so that the updates on updatedStaffJob are not directly saved in db
        em.detach(updatedStaffJob);
        updatedStaffJob
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        StaffJobDTO staffJobDTO = staffJobMapper.toDto(updatedStaffJob);

        restStaffJobMockMvc.perform(put("/api/staff-jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staffJobDTO)))
            .andExpect(status().isOk());

        // Validate the StaffJob in the database
        List<StaffJob> staffJobList = staffJobRepository.findAll();
        assertThat(staffJobList).hasSize(databaseSizeBeforeUpdate);
        StaffJob testStaffJob = staffJobList.get(staffJobList.size() - 1);
        assertThat(testStaffJob.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testStaffJob.getEndDate()).isEqualTo(UPDATED_END_DATE);

        // Validate the StaffJob in Elasticsearch
        verify(mockStaffJobSearchRepository, times(1)).save(testStaffJob);
    }

    @Test
    @Transactional
    public void updateNonExistingStaffJob() throws Exception {
        int databaseSizeBeforeUpdate = staffJobRepository.findAll().size();

        // Create the StaffJob
        StaffJobDTO staffJobDTO = staffJobMapper.toDto(staffJob);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaffJobMockMvc.perform(put("/api/staff-jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staffJobDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StaffJob in the database
        List<StaffJob> staffJobList = staffJobRepository.findAll();
        assertThat(staffJobList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StaffJob in Elasticsearch
        verify(mockStaffJobSearchRepository, times(0)).save(staffJob);
    }

    @Test
    @Transactional
    public void deleteStaffJob() throws Exception {
        // Initialize the database
        staffJobRepository.saveAndFlush(staffJob);

        int databaseSizeBeforeDelete = staffJobRepository.findAll().size();

        // Delete the staffJob
        restStaffJobMockMvc.perform(delete("/api/staff-jobs/{id}", staffJob.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StaffJob> staffJobList = staffJobRepository.findAll();
        assertThat(staffJobList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StaffJob in Elasticsearch
        verify(mockStaffJobSearchRepository, times(1)).deleteById(staffJob.getId());
    }

    @Test
    @Transactional
    public void searchStaffJob() throws Exception {
        // Initialize the database
        staffJobRepository.saveAndFlush(staffJob);
        when(mockStaffJobSearchRepository.search(queryStringQuery("id:" + staffJob.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(staffJob), PageRequest.of(0, 1), 1));
        // Search the staffJob
        restStaffJobMockMvc.perform(get("/api/_search/staff-jobs?query=id:" + staffJob.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staffJob.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StaffJob.class);
        StaffJob staffJob1 = new StaffJob();
        staffJob1.setId(1L);
        StaffJob staffJob2 = new StaffJob();
        staffJob2.setId(staffJob1.getId());
        assertThat(staffJob1).isEqualTo(staffJob2);
        staffJob2.setId(2L);
        assertThat(staffJob1).isNotEqualTo(staffJob2);
        staffJob1.setId(null);
        assertThat(staffJob1).isNotEqualTo(staffJob2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StaffJobDTO.class);
        StaffJobDTO staffJobDTO1 = new StaffJobDTO();
        staffJobDTO1.setId(1L);
        StaffJobDTO staffJobDTO2 = new StaffJobDTO();
        assertThat(staffJobDTO1).isNotEqualTo(staffJobDTO2);
        staffJobDTO2.setId(staffJobDTO1.getId());
        assertThat(staffJobDTO1).isEqualTo(staffJobDTO2);
        staffJobDTO2.setId(2L);
        assertThat(staffJobDTO1).isNotEqualTo(staffJobDTO2);
        staffJobDTO1.setId(null);
        assertThat(staffJobDTO1).isNotEqualTo(staffJobDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(staffJobMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(staffJobMapper.fromId(null)).isNull();
    }
}
