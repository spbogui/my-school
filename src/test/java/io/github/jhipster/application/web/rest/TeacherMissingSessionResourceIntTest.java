package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.TeacherMissingSession;
import io.github.jhipster.application.repository.TeacherMissingSessionRepository;
import io.github.jhipster.application.repository.search.TeacherMissingSessionSearchRepository;
import io.github.jhipster.application.service.TeacherMissingSessionService;
import io.github.jhipster.application.service.dto.TeacherMissingSessionDTO;
import io.github.jhipster.application.service.mapper.TeacherMissingSessionMapper;
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
 * Test class for the TeacherMissingSessionResource REST controller.
 *
 * @see TeacherMissingSessionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class TeacherMissingSessionResourceIntTest {

    private static final Boolean DEFAULT_IS_JUSTIFIED = false;
    private static final Boolean UPDATED_IS_JUSTIFIED = true;

    @Autowired
    private TeacherMissingSessionRepository teacherMissingSessionRepository;

    @Autowired
    private TeacherMissingSessionMapper teacherMissingSessionMapper;

    @Autowired
    private TeacherMissingSessionService teacherMissingSessionService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.TeacherMissingSessionSearchRepositoryMockConfiguration
     */
    @Autowired
    private TeacherMissingSessionSearchRepository mockTeacherMissingSessionSearchRepository;

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

    private MockMvc restTeacherMissingSessionMockMvc;

    private TeacherMissingSession teacherMissingSession;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TeacherMissingSessionResource teacherMissingSessionResource = new TeacherMissingSessionResource(teacherMissingSessionService);
        this.restTeacherMissingSessionMockMvc = MockMvcBuilders.standaloneSetup(teacherMissingSessionResource)
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
    public static TeacherMissingSession createEntity(EntityManager em) {
        TeacherMissingSession teacherMissingSession = new TeacherMissingSession()
            .isJustified(DEFAULT_IS_JUSTIFIED);
        return teacherMissingSession;
    }

    @Before
    public void initTest() {
        teacherMissingSession = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeacherMissingSession() throws Exception {
        int databaseSizeBeforeCreate = teacherMissingSessionRepository.findAll().size();

        // Create the TeacherMissingSession
        TeacherMissingSessionDTO teacherMissingSessionDTO = teacherMissingSessionMapper.toDto(teacherMissingSession);
        restTeacherMissingSessionMockMvc.perform(post("/api/teacher-missing-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherMissingSessionDTO)))
            .andExpect(status().isCreated());

        // Validate the TeacherMissingSession in the database
        List<TeacherMissingSession> teacherMissingSessionList = teacherMissingSessionRepository.findAll();
        assertThat(teacherMissingSessionList).hasSize(databaseSizeBeforeCreate + 1);
        TeacherMissingSession testTeacherMissingSession = teacherMissingSessionList.get(teacherMissingSessionList.size() - 1);
        assertThat(testTeacherMissingSession.isIsJustified()).isEqualTo(DEFAULT_IS_JUSTIFIED);

        // Validate the TeacherMissingSession in Elasticsearch
        verify(mockTeacherMissingSessionSearchRepository, times(1)).save(testTeacherMissingSession);
    }

    @Test
    @Transactional
    public void createTeacherMissingSessionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teacherMissingSessionRepository.findAll().size();

        // Create the TeacherMissingSession with an existing ID
        teacherMissingSession.setId(1L);
        TeacherMissingSessionDTO teacherMissingSessionDTO = teacherMissingSessionMapper.toDto(teacherMissingSession);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeacherMissingSessionMockMvc.perform(post("/api/teacher-missing-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherMissingSessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TeacherMissingSession in the database
        List<TeacherMissingSession> teacherMissingSessionList = teacherMissingSessionRepository.findAll();
        assertThat(teacherMissingSessionList).hasSize(databaseSizeBeforeCreate);

        // Validate the TeacherMissingSession in Elasticsearch
        verify(mockTeacherMissingSessionSearchRepository, times(0)).save(teacherMissingSession);
    }

    @Test
    @Transactional
    public void checkIsJustifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = teacherMissingSessionRepository.findAll().size();
        // set the field null
        teacherMissingSession.setIsJustified(null);

        // Create the TeacherMissingSession, which fails.
        TeacherMissingSessionDTO teacherMissingSessionDTO = teacherMissingSessionMapper.toDto(teacherMissingSession);

        restTeacherMissingSessionMockMvc.perform(post("/api/teacher-missing-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherMissingSessionDTO)))
            .andExpect(status().isBadRequest());

        List<TeacherMissingSession> teacherMissingSessionList = teacherMissingSessionRepository.findAll();
        assertThat(teacherMissingSessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTeacherMissingSessions() throws Exception {
        // Initialize the database
        teacherMissingSessionRepository.saveAndFlush(teacherMissingSession);

        // Get all the teacherMissingSessionList
        restTeacherMissingSessionMockMvc.perform(get("/api/teacher-missing-sessions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teacherMissingSession.getId().intValue())))
            .andExpect(jsonPath("$.[*].isJustified").value(hasItem(DEFAULT_IS_JUSTIFIED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTeacherMissingSession() throws Exception {
        // Initialize the database
        teacherMissingSessionRepository.saveAndFlush(teacherMissingSession);

        // Get the teacherMissingSession
        restTeacherMissingSessionMockMvc.perform(get("/api/teacher-missing-sessions/{id}", teacherMissingSession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(teacherMissingSession.getId().intValue()))
            .andExpect(jsonPath("$.isJustified").value(DEFAULT_IS_JUSTIFIED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTeacherMissingSession() throws Exception {
        // Get the teacherMissingSession
        restTeacherMissingSessionMockMvc.perform(get("/api/teacher-missing-sessions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeacherMissingSession() throws Exception {
        // Initialize the database
        teacherMissingSessionRepository.saveAndFlush(teacherMissingSession);

        int databaseSizeBeforeUpdate = teacherMissingSessionRepository.findAll().size();

        // Update the teacherMissingSession
        TeacherMissingSession updatedTeacherMissingSession = teacherMissingSessionRepository.findById(teacherMissingSession.getId()).get();
        // Disconnect from session so that the updates on updatedTeacherMissingSession are not directly saved in db
        em.detach(updatedTeacherMissingSession);
        updatedTeacherMissingSession
            .isJustified(UPDATED_IS_JUSTIFIED);
        TeacherMissingSessionDTO teacherMissingSessionDTO = teacherMissingSessionMapper.toDto(updatedTeacherMissingSession);

        restTeacherMissingSessionMockMvc.perform(put("/api/teacher-missing-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherMissingSessionDTO)))
            .andExpect(status().isOk());

        // Validate the TeacherMissingSession in the database
        List<TeacherMissingSession> teacherMissingSessionList = teacherMissingSessionRepository.findAll();
        assertThat(teacherMissingSessionList).hasSize(databaseSizeBeforeUpdate);
        TeacherMissingSession testTeacherMissingSession = teacherMissingSessionList.get(teacherMissingSessionList.size() - 1);
        assertThat(testTeacherMissingSession.isIsJustified()).isEqualTo(UPDATED_IS_JUSTIFIED);

        // Validate the TeacherMissingSession in Elasticsearch
        verify(mockTeacherMissingSessionSearchRepository, times(1)).save(testTeacherMissingSession);
    }

    @Test
    @Transactional
    public void updateNonExistingTeacherMissingSession() throws Exception {
        int databaseSizeBeforeUpdate = teacherMissingSessionRepository.findAll().size();

        // Create the TeacherMissingSession
        TeacherMissingSessionDTO teacherMissingSessionDTO = teacherMissingSessionMapper.toDto(teacherMissingSession);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeacherMissingSessionMockMvc.perform(put("/api/teacher-missing-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherMissingSessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TeacherMissingSession in the database
        List<TeacherMissingSession> teacherMissingSessionList = teacherMissingSessionRepository.findAll();
        assertThat(teacherMissingSessionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TeacherMissingSession in Elasticsearch
        verify(mockTeacherMissingSessionSearchRepository, times(0)).save(teacherMissingSession);
    }

    @Test
    @Transactional
    public void deleteTeacherMissingSession() throws Exception {
        // Initialize the database
        teacherMissingSessionRepository.saveAndFlush(teacherMissingSession);

        int databaseSizeBeforeDelete = teacherMissingSessionRepository.findAll().size();

        // Delete the teacherMissingSession
        restTeacherMissingSessionMockMvc.perform(delete("/api/teacher-missing-sessions/{id}", teacherMissingSession.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TeacherMissingSession> teacherMissingSessionList = teacherMissingSessionRepository.findAll();
        assertThat(teacherMissingSessionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TeacherMissingSession in Elasticsearch
        verify(mockTeacherMissingSessionSearchRepository, times(1)).deleteById(teacherMissingSession.getId());
    }

    @Test
    @Transactional
    public void searchTeacherMissingSession() throws Exception {
        // Initialize the database
        teacherMissingSessionRepository.saveAndFlush(teacherMissingSession);
        when(mockTeacherMissingSessionSearchRepository.search(queryStringQuery("id:" + teacherMissingSession.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(teacherMissingSession), PageRequest.of(0, 1), 1));
        // Search the teacherMissingSession
        restTeacherMissingSessionMockMvc.perform(get("/api/_search/teacher-missing-sessions?query=id:" + teacherMissingSession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teacherMissingSession.getId().intValue())))
            .andExpect(jsonPath("$.[*].isJustified").value(hasItem(DEFAULT_IS_JUSTIFIED.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeacherMissingSession.class);
        TeacherMissingSession teacherMissingSession1 = new TeacherMissingSession();
        teacherMissingSession1.setId(1L);
        TeacherMissingSession teacherMissingSession2 = new TeacherMissingSession();
        teacherMissingSession2.setId(teacherMissingSession1.getId());
        assertThat(teacherMissingSession1).isEqualTo(teacherMissingSession2);
        teacherMissingSession2.setId(2L);
        assertThat(teacherMissingSession1).isNotEqualTo(teacherMissingSession2);
        teacherMissingSession1.setId(null);
        assertThat(teacherMissingSession1).isNotEqualTo(teacherMissingSession2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeacherMissingSessionDTO.class);
        TeacherMissingSessionDTO teacherMissingSessionDTO1 = new TeacherMissingSessionDTO();
        teacherMissingSessionDTO1.setId(1L);
        TeacherMissingSessionDTO teacherMissingSessionDTO2 = new TeacherMissingSessionDTO();
        assertThat(teacherMissingSessionDTO1).isNotEqualTo(teacherMissingSessionDTO2);
        teacherMissingSessionDTO2.setId(teacherMissingSessionDTO1.getId());
        assertThat(teacherMissingSessionDTO1).isEqualTo(teacherMissingSessionDTO2);
        teacherMissingSessionDTO2.setId(2L);
        assertThat(teacherMissingSessionDTO1).isNotEqualTo(teacherMissingSessionDTO2);
        teacherMissingSessionDTO1.setId(null);
        assertThat(teacherMissingSessionDTO1).isNotEqualTo(teacherMissingSessionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(teacherMissingSessionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(teacherMissingSessionMapper.fromId(null)).isNull();
    }
}
