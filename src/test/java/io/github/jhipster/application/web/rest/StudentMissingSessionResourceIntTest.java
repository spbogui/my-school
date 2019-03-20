package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.StudentMissingSession;
import io.github.jhipster.application.repository.StudentMissingSessionRepository;
import io.github.jhipster.application.repository.search.StudentMissingSessionSearchRepository;
import io.github.jhipster.application.service.StudentMissingSessionService;
import io.github.jhipster.application.service.dto.StudentMissingSessionDTO;
import io.github.jhipster.application.service.mapper.StudentMissingSessionMapper;
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
 * Test class for the StudentMissingSessionResource REST controller.
 *
 * @see StudentMissingSessionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class StudentMissingSessionResourceIntTest {

    private static final Boolean DEFAULT_IS_JUSTIFIED = false;
    private static final Boolean UPDATED_IS_JUSTIFIED = true;

    @Autowired
    private StudentMissingSessionRepository studentMissingSessionRepository;

    @Autowired
    private StudentMissingSessionMapper studentMissingSessionMapper;

    @Autowired
    private StudentMissingSessionService studentMissingSessionService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.StudentMissingSessionSearchRepositoryMockConfiguration
     */
    @Autowired
    private StudentMissingSessionSearchRepository mockStudentMissingSessionSearchRepository;

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

    private MockMvc restStudentMissingSessionMockMvc;

    private StudentMissingSession studentMissingSession;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentMissingSessionResource studentMissingSessionResource = new StudentMissingSessionResource(studentMissingSessionService);
        this.restStudentMissingSessionMockMvc = MockMvcBuilders.standaloneSetup(studentMissingSessionResource)
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
    public static StudentMissingSession createEntity(EntityManager em) {
        StudentMissingSession studentMissingSession = new StudentMissingSession()
            .isJustified(DEFAULT_IS_JUSTIFIED);
        return studentMissingSession;
    }

    @Before
    public void initTest() {
        studentMissingSession = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentMissingSession() throws Exception {
        int databaseSizeBeforeCreate = studentMissingSessionRepository.findAll().size();

        // Create the StudentMissingSession
        StudentMissingSessionDTO studentMissingSessionDTO = studentMissingSessionMapper.toDto(studentMissingSession);
        restStudentMissingSessionMockMvc.perform(post("/api/student-missing-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentMissingSessionDTO)))
            .andExpect(status().isCreated());

        // Validate the StudentMissingSession in the database
        List<StudentMissingSession> studentMissingSessionList = studentMissingSessionRepository.findAll();
        assertThat(studentMissingSessionList).hasSize(databaseSizeBeforeCreate + 1);
        StudentMissingSession testStudentMissingSession = studentMissingSessionList.get(studentMissingSessionList.size() - 1);
        assertThat(testStudentMissingSession.isIsJustified()).isEqualTo(DEFAULT_IS_JUSTIFIED);

        // Validate the StudentMissingSession in Elasticsearch
        verify(mockStudentMissingSessionSearchRepository, times(1)).save(testStudentMissingSession);
    }

    @Test
    @Transactional
    public void createStudentMissingSessionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentMissingSessionRepository.findAll().size();

        // Create the StudentMissingSession with an existing ID
        studentMissingSession.setId(1L);
        StudentMissingSessionDTO studentMissingSessionDTO = studentMissingSessionMapper.toDto(studentMissingSession);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentMissingSessionMockMvc.perform(post("/api/student-missing-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentMissingSessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentMissingSession in the database
        List<StudentMissingSession> studentMissingSessionList = studentMissingSessionRepository.findAll();
        assertThat(studentMissingSessionList).hasSize(databaseSizeBeforeCreate);

        // Validate the StudentMissingSession in Elasticsearch
        verify(mockStudentMissingSessionSearchRepository, times(0)).save(studentMissingSession);
    }

    @Test
    @Transactional
    public void checkIsJustifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentMissingSessionRepository.findAll().size();
        // set the field null
        studentMissingSession.setIsJustified(null);

        // Create the StudentMissingSession, which fails.
        StudentMissingSessionDTO studentMissingSessionDTO = studentMissingSessionMapper.toDto(studentMissingSession);

        restStudentMissingSessionMockMvc.perform(post("/api/student-missing-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentMissingSessionDTO)))
            .andExpect(status().isBadRequest());

        List<StudentMissingSession> studentMissingSessionList = studentMissingSessionRepository.findAll();
        assertThat(studentMissingSessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudentMissingSessions() throws Exception {
        // Initialize the database
        studentMissingSessionRepository.saveAndFlush(studentMissingSession);

        // Get all the studentMissingSessionList
        restStudentMissingSessionMockMvc.perform(get("/api/student-missing-sessions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentMissingSession.getId().intValue())))
            .andExpect(jsonPath("$.[*].isJustified").value(hasItem(DEFAULT_IS_JUSTIFIED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getStudentMissingSession() throws Exception {
        // Initialize the database
        studentMissingSessionRepository.saveAndFlush(studentMissingSession);

        // Get the studentMissingSession
        restStudentMissingSessionMockMvc.perform(get("/api/student-missing-sessions/{id}", studentMissingSession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentMissingSession.getId().intValue()))
            .andExpect(jsonPath("$.isJustified").value(DEFAULT_IS_JUSTIFIED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentMissingSession() throws Exception {
        // Get the studentMissingSession
        restStudentMissingSessionMockMvc.perform(get("/api/student-missing-sessions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentMissingSession() throws Exception {
        // Initialize the database
        studentMissingSessionRepository.saveAndFlush(studentMissingSession);

        int databaseSizeBeforeUpdate = studentMissingSessionRepository.findAll().size();

        // Update the studentMissingSession
        StudentMissingSession updatedStudentMissingSession = studentMissingSessionRepository.findById(studentMissingSession.getId()).get();
        // Disconnect from session so that the updates on updatedStudentMissingSession are not directly saved in db
        em.detach(updatedStudentMissingSession);
        updatedStudentMissingSession
            .isJustified(UPDATED_IS_JUSTIFIED);
        StudentMissingSessionDTO studentMissingSessionDTO = studentMissingSessionMapper.toDto(updatedStudentMissingSession);

        restStudentMissingSessionMockMvc.perform(put("/api/student-missing-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentMissingSessionDTO)))
            .andExpect(status().isOk());

        // Validate the StudentMissingSession in the database
        List<StudentMissingSession> studentMissingSessionList = studentMissingSessionRepository.findAll();
        assertThat(studentMissingSessionList).hasSize(databaseSizeBeforeUpdate);
        StudentMissingSession testStudentMissingSession = studentMissingSessionList.get(studentMissingSessionList.size() - 1);
        assertThat(testStudentMissingSession.isIsJustified()).isEqualTo(UPDATED_IS_JUSTIFIED);

        // Validate the StudentMissingSession in Elasticsearch
        verify(mockStudentMissingSessionSearchRepository, times(1)).save(testStudentMissingSession);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentMissingSession() throws Exception {
        int databaseSizeBeforeUpdate = studentMissingSessionRepository.findAll().size();

        // Create the StudentMissingSession
        StudentMissingSessionDTO studentMissingSessionDTO = studentMissingSessionMapper.toDto(studentMissingSession);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentMissingSessionMockMvc.perform(put("/api/student-missing-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentMissingSessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentMissingSession in the database
        List<StudentMissingSession> studentMissingSessionList = studentMissingSessionRepository.findAll();
        assertThat(studentMissingSessionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StudentMissingSession in Elasticsearch
        verify(mockStudentMissingSessionSearchRepository, times(0)).save(studentMissingSession);
    }

    @Test
    @Transactional
    public void deleteStudentMissingSession() throws Exception {
        // Initialize the database
        studentMissingSessionRepository.saveAndFlush(studentMissingSession);

        int databaseSizeBeforeDelete = studentMissingSessionRepository.findAll().size();

        // Delete the studentMissingSession
        restStudentMissingSessionMockMvc.perform(delete("/api/student-missing-sessions/{id}", studentMissingSession.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentMissingSession> studentMissingSessionList = studentMissingSessionRepository.findAll();
        assertThat(studentMissingSessionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StudentMissingSession in Elasticsearch
        verify(mockStudentMissingSessionSearchRepository, times(1)).deleteById(studentMissingSession.getId());
    }

    @Test
    @Transactional
    public void searchStudentMissingSession() throws Exception {
        // Initialize the database
        studentMissingSessionRepository.saveAndFlush(studentMissingSession);
        when(mockStudentMissingSessionSearchRepository.search(queryStringQuery("id:" + studentMissingSession.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(studentMissingSession), PageRequest.of(0, 1), 1));
        // Search the studentMissingSession
        restStudentMissingSessionMockMvc.perform(get("/api/_search/student-missing-sessions?query=id:" + studentMissingSession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentMissingSession.getId().intValue())))
            .andExpect(jsonPath("$.[*].isJustified").value(hasItem(DEFAULT_IS_JUSTIFIED.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentMissingSession.class);
        StudentMissingSession studentMissingSession1 = new StudentMissingSession();
        studentMissingSession1.setId(1L);
        StudentMissingSession studentMissingSession2 = new StudentMissingSession();
        studentMissingSession2.setId(studentMissingSession1.getId());
        assertThat(studentMissingSession1).isEqualTo(studentMissingSession2);
        studentMissingSession2.setId(2L);
        assertThat(studentMissingSession1).isNotEqualTo(studentMissingSession2);
        studentMissingSession1.setId(null);
        assertThat(studentMissingSession1).isNotEqualTo(studentMissingSession2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentMissingSessionDTO.class);
        StudentMissingSessionDTO studentMissingSessionDTO1 = new StudentMissingSessionDTO();
        studentMissingSessionDTO1.setId(1L);
        StudentMissingSessionDTO studentMissingSessionDTO2 = new StudentMissingSessionDTO();
        assertThat(studentMissingSessionDTO1).isNotEqualTo(studentMissingSessionDTO2);
        studentMissingSessionDTO2.setId(studentMissingSessionDTO1.getId());
        assertThat(studentMissingSessionDTO1).isEqualTo(studentMissingSessionDTO2);
        studentMissingSessionDTO2.setId(2L);
        assertThat(studentMissingSessionDTO1).isNotEqualTo(studentMissingSessionDTO2);
        studentMissingSessionDTO1.setId(null);
        assertThat(studentMissingSessionDTO1).isNotEqualTo(studentMissingSessionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(studentMissingSessionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(studentMissingSessionMapper.fromId(null)).isNull();
    }
}
