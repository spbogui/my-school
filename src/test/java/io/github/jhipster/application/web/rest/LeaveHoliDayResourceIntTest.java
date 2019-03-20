package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.LeaveHoliDay;
import io.github.jhipster.application.repository.LeaveHoliDayRepository;
import io.github.jhipster.application.repository.search.LeaveHoliDaySearchRepository;
import io.github.jhipster.application.service.LeaveHoliDayService;
import io.github.jhipster.application.service.dto.LeaveHoliDayDTO;
import io.github.jhipster.application.service.mapper.LeaveHoliDayMapper;
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
 * Test class for the LeaveHoliDayResource REST controller.
 *
 * @see LeaveHoliDayResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class LeaveHoliDayResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MEMO = "AAAAAAAAAA";
    private static final String UPDATED_MEMO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private LeaveHoliDayRepository leaveHoliDayRepository;

    @Autowired
    private LeaveHoliDayMapper leaveHoliDayMapper;

    @Autowired
    private LeaveHoliDayService leaveHoliDayService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.LeaveHoliDaySearchRepositoryMockConfiguration
     */
    @Autowired
    private LeaveHoliDaySearchRepository mockLeaveHoliDaySearchRepository;

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

    private MockMvc restLeaveHoliDayMockMvc;

    private LeaveHoliDay leaveHoliDay;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LeaveHoliDayResource leaveHoliDayResource = new LeaveHoliDayResource(leaveHoliDayService);
        this.restLeaveHoliDayMockMvc = MockMvcBuilders.standaloneSetup(leaveHoliDayResource)
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
    public static LeaveHoliDay createEntity(EntityManager em) {
        LeaveHoliDay leaveHoliDay = new LeaveHoliDay()
            .label(DEFAULT_LABEL)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .memo(DEFAULT_MEMO)
            .createdAt(DEFAULT_CREATED_AT);
        return leaveHoliDay;
    }

    @Before
    public void initTest() {
        leaveHoliDay = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeaveHoliDay() throws Exception {
        int databaseSizeBeforeCreate = leaveHoliDayRepository.findAll().size();

        // Create the LeaveHoliDay
        LeaveHoliDayDTO leaveHoliDayDTO = leaveHoliDayMapper.toDto(leaveHoliDay);
        restLeaveHoliDayMockMvc.perform(post("/api/leave-holi-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveHoliDayDTO)))
            .andExpect(status().isCreated());

        // Validate the LeaveHoliDay in the database
        List<LeaveHoliDay> leaveHoliDayList = leaveHoliDayRepository.findAll();
        assertThat(leaveHoliDayList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveHoliDay testLeaveHoliDay = leaveHoliDayList.get(leaveHoliDayList.size() - 1);
        assertThat(testLeaveHoliDay.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testLeaveHoliDay.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testLeaveHoliDay.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLeaveHoliDay.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testLeaveHoliDay.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);

        // Validate the LeaveHoliDay in Elasticsearch
        verify(mockLeaveHoliDaySearchRepository, times(1)).save(testLeaveHoliDay);
    }

    @Test
    @Transactional
    public void createLeaveHoliDayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leaveHoliDayRepository.findAll().size();

        // Create the LeaveHoliDay with an existing ID
        leaveHoliDay.setId(1L);
        LeaveHoliDayDTO leaveHoliDayDTO = leaveHoliDayMapper.toDto(leaveHoliDay);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveHoliDayMockMvc.perform(post("/api/leave-holi-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveHoliDayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveHoliDay in the database
        List<LeaveHoliDay> leaveHoliDayList = leaveHoliDayRepository.findAll();
        assertThat(leaveHoliDayList).hasSize(databaseSizeBeforeCreate);

        // Validate the LeaveHoliDay in Elasticsearch
        verify(mockLeaveHoliDaySearchRepository, times(0)).save(leaveHoliDay);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveHoliDayRepository.findAll().size();
        // set the field null
        leaveHoliDay.setLabel(null);

        // Create the LeaveHoliDay, which fails.
        LeaveHoliDayDTO leaveHoliDayDTO = leaveHoliDayMapper.toDto(leaveHoliDay);

        restLeaveHoliDayMockMvc.perform(post("/api/leave-holi-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveHoliDayDTO)))
            .andExpect(status().isBadRequest());

        List<LeaveHoliDay> leaveHoliDayList = leaveHoliDayRepository.findAll();
        assertThat(leaveHoliDayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveHoliDayRepository.findAll().size();
        // set the field null
        leaveHoliDay.setStartDate(null);

        // Create the LeaveHoliDay, which fails.
        LeaveHoliDayDTO leaveHoliDayDTO = leaveHoliDayMapper.toDto(leaveHoliDay);

        restLeaveHoliDayMockMvc.perform(post("/api/leave-holi-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveHoliDayDTO)))
            .andExpect(status().isBadRequest());

        List<LeaveHoliDay> leaveHoliDayList = leaveHoliDayRepository.findAll();
        assertThat(leaveHoliDayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveHoliDayRepository.findAll().size();
        // set the field null
        leaveHoliDay.setCreatedAt(null);

        // Create the LeaveHoliDay, which fails.
        LeaveHoliDayDTO leaveHoliDayDTO = leaveHoliDayMapper.toDto(leaveHoliDay);

        restLeaveHoliDayMockMvc.perform(post("/api/leave-holi-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveHoliDayDTO)))
            .andExpect(status().isBadRequest());

        List<LeaveHoliDay> leaveHoliDayList = leaveHoliDayRepository.findAll();
        assertThat(leaveHoliDayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLeaveHoliDays() throws Exception {
        // Initialize the database
        leaveHoliDayRepository.saveAndFlush(leaveHoliDay);

        // Get all the leaveHoliDayList
        restLeaveHoliDayMockMvc.perform(get("/api/leave-holi-days?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveHoliDay.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getLeaveHoliDay() throws Exception {
        // Initialize the database
        leaveHoliDayRepository.saveAndFlush(leaveHoliDay);

        // Get the leaveHoliDay
        restLeaveHoliDayMockMvc.perform(get("/api/leave-holi-days/{id}", leaveHoliDay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(leaveHoliDay.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.memo").value(DEFAULT_MEMO.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLeaveHoliDay() throws Exception {
        // Get the leaveHoliDay
        restLeaveHoliDayMockMvc.perform(get("/api/leave-holi-days/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeaveHoliDay() throws Exception {
        // Initialize the database
        leaveHoliDayRepository.saveAndFlush(leaveHoliDay);

        int databaseSizeBeforeUpdate = leaveHoliDayRepository.findAll().size();

        // Update the leaveHoliDay
        LeaveHoliDay updatedLeaveHoliDay = leaveHoliDayRepository.findById(leaveHoliDay.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveHoliDay are not directly saved in db
        em.detach(updatedLeaveHoliDay);
        updatedLeaveHoliDay
            .label(UPDATED_LABEL)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .memo(UPDATED_MEMO)
            .createdAt(UPDATED_CREATED_AT);
        LeaveHoliDayDTO leaveHoliDayDTO = leaveHoliDayMapper.toDto(updatedLeaveHoliDay);

        restLeaveHoliDayMockMvc.perform(put("/api/leave-holi-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveHoliDayDTO)))
            .andExpect(status().isOk());

        // Validate the LeaveHoliDay in the database
        List<LeaveHoliDay> leaveHoliDayList = leaveHoliDayRepository.findAll();
        assertThat(leaveHoliDayList).hasSize(databaseSizeBeforeUpdate);
        LeaveHoliDay testLeaveHoliDay = leaveHoliDayList.get(leaveHoliDayList.size() - 1);
        assertThat(testLeaveHoliDay.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testLeaveHoliDay.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testLeaveHoliDay.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLeaveHoliDay.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testLeaveHoliDay.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);

        // Validate the LeaveHoliDay in Elasticsearch
        verify(mockLeaveHoliDaySearchRepository, times(1)).save(testLeaveHoliDay);
    }

    @Test
    @Transactional
    public void updateNonExistingLeaveHoliDay() throws Exception {
        int databaseSizeBeforeUpdate = leaveHoliDayRepository.findAll().size();

        // Create the LeaveHoliDay
        LeaveHoliDayDTO leaveHoliDayDTO = leaveHoliDayMapper.toDto(leaveHoliDay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveHoliDayMockMvc.perform(put("/api/leave-holi-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveHoliDayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveHoliDay in the database
        List<LeaveHoliDay> leaveHoliDayList = leaveHoliDayRepository.findAll();
        assertThat(leaveHoliDayList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LeaveHoliDay in Elasticsearch
        verify(mockLeaveHoliDaySearchRepository, times(0)).save(leaveHoliDay);
    }

    @Test
    @Transactional
    public void deleteLeaveHoliDay() throws Exception {
        // Initialize the database
        leaveHoliDayRepository.saveAndFlush(leaveHoliDay);

        int databaseSizeBeforeDelete = leaveHoliDayRepository.findAll().size();

        // Delete the leaveHoliDay
        restLeaveHoliDayMockMvc.perform(delete("/api/leave-holi-days/{id}", leaveHoliDay.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LeaveHoliDay> leaveHoliDayList = leaveHoliDayRepository.findAll();
        assertThat(leaveHoliDayList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LeaveHoliDay in Elasticsearch
        verify(mockLeaveHoliDaySearchRepository, times(1)).deleteById(leaveHoliDay.getId());
    }

    @Test
    @Transactional
    public void searchLeaveHoliDay() throws Exception {
        // Initialize the database
        leaveHoliDayRepository.saveAndFlush(leaveHoliDay);
        when(mockLeaveHoliDaySearchRepository.search(queryStringQuery("id:" + leaveHoliDay.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(leaveHoliDay), PageRequest.of(0, 1), 1));
        // Search the leaveHoliDay
        restLeaveHoliDayMockMvc.perform(get("/api/_search/leave-holi-days?query=id:" + leaveHoliDay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveHoliDay.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveHoliDay.class);
        LeaveHoliDay leaveHoliDay1 = new LeaveHoliDay();
        leaveHoliDay1.setId(1L);
        LeaveHoliDay leaveHoliDay2 = new LeaveHoliDay();
        leaveHoliDay2.setId(leaveHoliDay1.getId());
        assertThat(leaveHoliDay1).isEqualTo(leaveHoliDay2);
        leaveHoliDay2.setId(2L);
        assertThat(leaveHoliDay1).isNotEqualTo(leaveHoliDay2);
        leaveHoliDay1.setId(null);
        assertThat(leaveHoliDay1).isNotEqualTo(leaveHoliDay2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveHoliDayDTO.class);
        LeaveHoliDayDTO leaveHoliDayDTO1 = new LeaveHoliDayDTO();
        leaveHoliDayDTO1.setId(1L);
        LeaveHoliDayDTO leaveHoliDayDTO2 = new LeaveHoliDayDTO();
        assertThat(leaveHoliDayDTO1).isNotEqualTo(leaveHoliDayDTO2);
        leaveHoliDayDTO2.setId(leaveHoliDayDTO1.getId());
        assertThat(leaveHoliDayDTO1).isEqualTo(leaveHoliDayDTO2);
        leaveHoliDayDTO2.setId(2L);
        assertThat(leaveHoliDayDTO1).isNotEqualTo(leaveHoliDayDTO2);
        leaveHoliDayDTO1.setId(null);
        assertThat(leaveHoliDayDTO1).isNotEqualTo(leaveHoliDayDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(leaveHoliDayMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(leaveHoliDayMapper.fromId(null)).isNull();
    }
}
