package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.ClassSession;
import io.github.jhipster.application.repository.ClassSessionRepository;
import io.github.jhipster.application.repository.search.ClassSessionSearchRepository;
import io.github.jhipster.application.service.ClassSessionService;
import io.github.jhipster.application.service.dto.ClassSessionDTO;
import io.github.jhipster.application.service.mapper.ClassSessionMapper;
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
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
 * Test class for the ClassSessionResource REST controller.
 *
 * @see ClassSessionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class ClassSessionResourceIntTest {

    private static final Instant DEFAULT_START_HOUR = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_HOUR = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_HOUR = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_HOUR = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ClassSessionRepository classSessionRepository;

    @Autowired
    private ClassSessionMapper classSessionMapper;

    @Autowired
    private ClassSessionService classSessionService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.ClassSessionSearchRepositoryMockConfiguration
     */
    @Autowired
    private ClassSessionSearchRepository mockClassSessionSearchRepository;

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

    private MockMvc restClassSessionMockMvc;

    private ClassSession classSession;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClassSessionResource classSessionResource = new ClassSessionResource(classSessionService);
        this.restClassSessionMockMvc = MockMvcBuilders.standaloneSetup(classSessionResource)
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
    public static ClassSession createEntity(EntityManager em) {
        ClassSession classSession = new ClassSession()
            .startHour(DEFAULT_START_HOUR)
            .endHour(DEFAULT_END_HOUR)
            .detail(DEFAULT_DETAIL)
            .createdAt(DEFAULT_CREATED_AT);
        return classSession;
    }

    @Before
    public void initTest() {
        classSession = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassSession() throws Exception {
        int databaseSizeBeforeCreate = classSessionRepository.findAll().size();

        // Create the ClassSession
        ClassSessionDTO classSessionDTO = classSessionMapper.toDto(classSession);
        restClassSessionMockMvc.perform(post("/api/class-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classSessionDTO)))
            .andExpect(status().isCreated());

        // Validate the ClassSession in the database
        List<ClassSession> classSessionList = classSessionRepository.findAll();
        assertThat(classSessionList).hasSize(databaseSizeBeforeCreate + 1);
        ClassSession testClassSession = classSessionList.get(classSessionList.size() - 1);
        assertThat(testClassSession.getStartHour()).isEqualTo(DEFAULT_START_HOUR);
        assertThat(testClassSession.getEndHour()).isEqualTo(DEFAULT_END_HOUR);
        assertThat(testClassSession.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testClassSession.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);

        // Validate the ClassSession in Elasticsearch
        verify(mockClassSessionSearchRepository, times(1)).save(testClassSession);
    }

    @Test
    @Transactional
    public void createClassSessionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classSessionRepository.findAll().size();

        // Create the ClassSession with an existing ID
        classSession.setId(1L);
        ClassSessionDTO classSessionDTO = classSessionMapper.toDto(classSession);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassSessionMockMvc.perform(post("/api/class-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classSessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassSession in the database
        List<ClassSession> classSessionList = classSessionRepository.findAll();
        assertThat(classSessionList).hasSize(databaseSizeBeforeCreate);

        // Validate the ClassSession in Elasticsearch
        verify(mockClassSessionSearchRepository, times(0)).save(classSession);
    }

    @Test
    @Transactional
    public void checkStartHourIsRequired() throws Exception {
        int databaseSizeBeforeTest = classSessionRepository.findAll().size();
        // set the field null
        classSession.setStartHour(null);

        // Create the ClassSession, which fails.
        ClassSessionDTO classSessionDTO = classSessionMapper.toDto(classSession);

        restClassSessionMockMvc.perform(post("/api/class-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classSessionDTO)))
            .andExpect(status().isBadRequest());

        List<ClassSession> classSessionList = classSessionRepository.findAll();
        assertThat(classSessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndHourIsRequired() throws Exception {
        int databaseSizeBeforeTest = classSessionRepository.findAll().size();
        // set the field null
        classSession.setEndHour(null);

        // Create the ClassSession, which fails.
        ClassSessionDTO classSessionDTO = classSessionMapper.toDto(classSession);

        restClassSessionMockMvc.perform(post("/api/class-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classSessionDTO)))
            .andExpect(status().isBadRequest());

        List<ClassSession> classSessionList = classSessionRepository.findAll();
        assertThat(classSessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDetailIsRequired() throws Exception {
        int databaseSizeBeforeTest = classSessionRepository.findAll().size();
        // set the field null
        classSession.setDetail(null);

        // Create the ClassSession, which fails.
        ClassSessionDTO classSessionDTO = classSessionMapper.toDto(classSession);

        restClassSessionMockMvc.perform(post("/api/class-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classSessionDTO)))
            .andExpect(status().isBadRequest());

        List<ClassSession> classSessionList = classSessionRepository.findAll();
        assertThat(classSessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = classSessionRepository.findAll().size();
        // set the field null
        classSession.setCreatedAt(null);

        // Create the ClassSession, which fails.
        ClassSessionDTO classSessionDTO = classSessionMapper.toDto(classSession);

        restClassSessionMockMvc.perform(post("/api/class-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classSessionDTO)))
            .andExpect(status().isBadRequest());

        List<ClassSession> classSessionList = classSessionRepository.findAll();
        assertThat(classSessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClassSessions() throws Exception {
        // Initialize the database
        classSessionRepository.saveAndFlush(classSession);

        // Get all the classSessionList
        restClassSessionMockMvc.perform(get("/api/class-sessions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classSession.getId().intValue())))
            .andExpect(jsonPath("$.[*].startHour").value(hasItem(DEFAULT_START_HOUR.toString())))
            .andExpect(jsonPath("$.[*].endHour").value(hasItem(DEFAULT_END_HOUR.toString())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getClassSession() throws Exception {
        // Initialize the database
        classSessionRepository.saveAndFlush(classSession);

        // Get the classSession
        restClassSessionMockMvc.perform(get("/api/class-sessions/{id}", classSession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classSession.getId().intValue()))
            .andExpect(jsonPath("$.startHour").value(DEFAULT_START_HOUR.toString()))
            .andExpect(jsonPath("$.endHour").value(DEFAULT_END_HOUR.toString()))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClassSession() throws Exception {
        // Get the classSession
        restClassSessionMockMvc.perform(get("/api/class-sessions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassSession() throws Exception {
        // Initialize the database
        classSessionRepository.saveAndFlush(classSession);

        int databaseSizeBeforeUpdate = classSessionRepository.findAll().size();

        // Update the classSession
        ClassSession updatedClassSession = classSessionRepository.findById(classSession.getId()).get();
        // Disconnect from session so that the updates on updatedClassSession are not directly saved in db
        em.detach(updatedClassSession);
        updatedClassSession
            .startHour(UPDATED_START_HOUR)
            .endHour(UPDATED_END_HOUR)
            .detail(UPDATED_DETAIL)
            .createdAt(UPDATED_CREATED_AT);
        ClassSessionDTO classSessionDTO = classSessionMapper.toDto(updatedClassSession);

        restClassSessionMockMvc.perform(put("/api/class-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classSessionDTO)))
            .andExpect(status().isOk());

        // Validate the ClassSession in the database
        List<ClassSession> classSessionList = classSessionRepository.findAll();
        assertThat(classSessionList).hasSize(databaseSizeBeforeUpdate);
        ClassSession testClassSession = classSessionList.get(classSessionList.size() - 1);
        assertThat(testClassSession.getStartHour()).isEqualTo(UPDATED_START_HOUR);
        assertThat(testClassSession.getEndHour()).isEqualTo(UPDATED_END_HOUR);
        assertThat(testClassSession.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testClassSession.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);

        // Validate the ClassSession in Elasticsearch
        verify(mockClassSessionSearchRepository, times(1)).save(testClassSession);
    }

    @Test
    @Transactional
    public void updateNonExistingClassSession() throws Exception {
        int databaseSizeBeforeUpdate = classSessionRepository.findAll().size();

        // Create the ClassSession
        ClassSessionDTO classSessionDTO = classSessionMapper.toDto(classSession);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassSessionMockMvc.perform(put("/api/class-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classSessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassSession in the database
        List<ClassSession> classSessionList = classSessionRepository.findAll();
        assertThat(classSessionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassSession in Elasticsearch
        verify(mockClassSessionSearchRepository, times(0)).save(classSession);
    }

    @Test
    @Transactional
    public void deleteClassSession() throws Exception {
        // Initialize the database
        classSessionRepository.saveAndFlush(classSession);

        int databaseSizeBeforeDelete = classSessionRepository.findAll().size();

        // Delete the classSession
        restClassSessionMockMvc.perform(delete("/api/class-sessions/{id}", classSession.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClassSession> classSessionList = classSessionRepository.findAll();
        assertThat(classSessionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ClassSession in Elasticsearch
        verify(mockClassSessionSearchRepository, times(1)).deleteById(classSession.getId());
    }

    @Test
    @Transactional
    public void searchClassSession() throws Exception {
        // Initialize the database
        classSessionRepository.saveAndFlush(classSession);
        when(mockClassSessionSearchRepository.search(queryStringQuery("id:" + classSession.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(classSession), PageRequest.of(0, 1), 1));
        // Search the classSession
        restClassSessionMockMvc.perform(get("/api/_search/class-sessions?query=id:" + classSession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classSession.getId().intValue())))
            .andExpect(jsonPath("$.[*].startHour").value(hasItem(DEFAULT_START_HOUR.toString())))
            .andExpect(jsonPath("$.[*].endHour").value(hasItem(DEFAULT_END_HOUR.toString())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassSession.class);
        ClassSession classSession1 = new ClassSession();
        classSession1.setId(1L);
        ClassSession classSession2 = new ClassSession();
        classSession2.setId(classSession1.getId());
        assertThat(classSession1).isEqualTo(classSession2);
        classSession2.setId(2L);
        assertThat(classSession1).isNotEqualTo(classSession2);
        classSession1.setId(null);
        assertThat(classSession1).isNotEqualTo(classSession2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassSessionDTO.class);
        ClassSessionDTO classSessionDTO1 = new ClassSessionDTO();
        classSessionDTO1.setId(1L);
        ClassSessionDTO classSessionDTO2 = new ClassSessionDTO();
        assertThat(classSessionDTO1).isNotEqualTo(classSessionDTO2);
        classSessionDTO2.setId(classSessionDTO1.getId());
        assertThat(classSessionDTO1).isEqualTo(classSessionDTO2);
        classSessionDTO2.setId(2L);
        assertThat(classSessionDTO1).isNotEqualTo(classSessionDTO2);
        classSessionDTO1.setId(null);
        assertThat(classSessionDTO1).isNotEqualTo(classSessionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(classSessionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(classSessionMapper.fromId(null)).isNull();
    }
}
