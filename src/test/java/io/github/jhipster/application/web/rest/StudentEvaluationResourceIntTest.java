package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.StudentEvaluation;
import io.github.jhipster.application.repository.StudentEvaluationRepository;
import io.github.jhipster.application.repository.search.StudentEvaluationSearchRepository;
import io.github.jhipster.application.service.StudentEvaluationService;
import io.github.jhipster.application.service.dto.StudentEvaluationDTO;
import io.github.jhipster.application.service.mapper.StudentEvaluationMapper;
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
 * Test class for the StudentEvaluationResource REST controller.
 *
 * @see StudentEvaluationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class StudentEvaluationResourceIntTest {

    private static final Double DEFAULT_GRADE = 1D;
    private static final Double UPDATED_GRADE = 2D;

    @Autowired
    private StudentEvaluationRepository studentEvaluationRepository;

    @Autowired
    private StudentEvaluationMapper studentEvaluationMapper;

    @Autowired
    private StudentEvaluationService studentEvaluationService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.StudentEvaluationSearchRepositoryMockConfiguration
     */
    @Autowired
    private StudentEvaluationSearchRepository mockStudentEvaluationSearchRepository;

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

    private MockMvc restStudentEvaluationMockMvc;

    private StudentEvaluation studentEvaluation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentEvaluationResource studentEvaluationResource = new StudentEvaluationResource(studentEvaluationService);
        this.restStudentEvaluationMockMvc = MockMvcBuilders.standaloneSetup(studentEvaluationResource)
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
    public static StudentEvaluation createEntity(EntityManager em) {
        StudentEvaluation studentEvaluation = new StudentEvaluation()
            .grade(DEFAULT_GRADE);
        return studentEvaluation;
    }

    @Before
    public void initTest() {
        studentEvaluation = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentEvaluation() throws Exception {
        int databaseSizeBeforeCreate = studentEvaluationRepository.findAll().size();

        // Create the StudentEvaluation
        StudentEvaluationDTO studentEvaluationDTO = studentEvaluationMapper.toDto(studentEvaluation);
        restStudentEvaluationMockMvc.perform(post("/api/student-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentEvaluationDTO)))
            .andExpect(status().isCreated());

        // Validate the StudentEvaluation in the database
        List<StudentEvaluation> studentEvaluationList = studentEvaluationRepository.findAll();
        assertThat(studentEvaluationList).hasSize(databaseSizeBeforeCreate + 1);
        StudentEvaluation testStudentEvaluation = studentEvaluationList.get(studentEvaluationList.size() - 1);
        assertThat(testStudentEvaluation.getGrade()).isEqualTo(DEFAULT_GRADE);

        // Validate the StudentEvaluation in Elasticsearch
        verify(mockStudentEvaluationSearchRepository, times(1)).save(testStudentEvaluation);
    }

    @Test
    @Transactional
    public void createStudentEvaluationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentEvaluationRepository.findAll().size();

        // Create the StudentEvaluation with an existing ID
        studentEvaluation.setId(1L);
        StudentEvaluationDTO studentEvaluationDTO = studentEvaluationMapper.toDto(studentEvaluation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentEvaluationMockMvc.perform(post("/api/student-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentEvaluationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentEvaluation in the database
        List<StudentEvaluation> studentEvaluationList = studentEvaluationRepository.findAll();
        assertThat(studentEvaluationList).hasSize(databaseSizeBeforeCreate);

        // Validate the StudentEvaluation in Elasticsearch
        verify(mockStudentEvaluationSearchRepository, times(0)).save(studentEvaluation);
    }

    @Test
    @Transactional
    public void checkGradeIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentEvaluationRepository.findAll().size();
        // set the field null
        studentEvaluation.setGrade(null);

        // Create the StudentEvaluation, which fails.
        StudentEvaluationDTO studentEvaluationDTO = studentEvaluationMapper.toDto(studentEvaluation);

        restStudentEvaluationMockMvc.perform(post("/api/student-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentEvaluationDTO)))
            .andExpect(status().isBadRequest());

        List<StudentEvaluation> studentEvaluationList = studentEvaluationRepository.findAll();
        assertThat(studentEvaluationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudentEvaluations() throws Exception {
        // Initialize the database
        studentEvaluationRepository.saveAndFlush(studentEvaluation);

        // Get all the studentEvaluationList
        restStudentEvaluationMockMvc.perform(get("/api/student-evaluations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentEvaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getStudentEvaluation() throws Exception {
        // Initialize the database
        studentEvaluationRepository.saveAndFlush(studentEvaluation);

        // Get the studentEvaluation
        restStudentEvaluationMockMvc.perform(get("/api/student-evaluations/{id}", studentEvaluation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentEvaluation.getId().intValue()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentEvaluation() throws Exception {
        // Get the studentEvaluation
        restStudentEvaluationMockMvc.perform(get("/api/student-evaluations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentEvaluation() throws Exception {
        // Initialize the database
        studentEvaluationRepository.saveAndFlush(studentEvaluation);

        int databaseSizeBeforeUpdate = studentEvaluationRepository.findAll().size();

        // Update the studentEvaluation
        StudentEvaluation updatedStudentEvaluation = studentEvaluationRepository.findById(studentEvaluation.getId()).get();
        // Disconnect from session so that the updates on updatedStudentEvaluation are not directly saved in db
        em.detach(updatedStudentEvaluation);
        updatedStudentEvaluation
            .grade(UPDATED_GRADE);
        StudentEvaluationDTO studentEvaluationDTO = studentEvaluationMapper.toDto(updatedStudentEvaluation);

        restStudentEvaluationMockMvc.perform(put("/api/student-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentEvaluationDTO)))
            .andExpect(status().isOk());

        // Validate the StudentEvaluation in the database
        List<StudentEvaluation> studentEvaluationList = studentEvaluationRepository.findAll();
        assertThat(studentEvaluationList).hasSize(databaseSizeBeforeUpdate);
        StudentEvaluation testStudentEvaluation = studentEvaluationList.get(studentEvaluationList.size() - 1);
        assertThat(testStudentEvaluation.getGrade()).isEqualTo(UPDATED_GRADE);

        // Validate the StudentEvaluation in Elasticsearch
        verify(mockStudentEvaluationSearchRepository, times(1)).save(testStudentEvaluation);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = studentEvaluationRepository.findAll().size();

        // Create the StudentEvaluation
        StudentEvaluationDTO studentEvaluationDTO = studentEvaluationMapper.toDto(studentEvaluation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentEvaluationMockMvc.perform(put("/api/student-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentEvaluationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentEvaluation in the database
        List<StudentEvaluation> studentEvaluationList = studentEvaluationRepository.findAll();
        assertThat(studentEvaluationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StudentEvaluation in Elasticsearch
        verify(mockStudentEvaluationSearchRepository, times(0)).save(studentEvaluation);
    }

    @Test
    @Transactional
    public void deleteStudentEvaluation() throws Exception {
        // Initialize the database
        studentEvaluationRepository.saveAndFlush(studentEvaluation);

        int databaseSizeBeforeDelete = studentEvaluationRepository.findAll().size();

        // Delete the studentEvaluation
        restStudentEvaluationMockMvc.perform(delete("/api/student-evaluations/{id}", studentEvaluation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentEvaluation> studentEvaluationList = studentEvaluationRepository.findAll();
        assertThat(studentEvaluationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StudentEvaluation in Elasticsearch
        verify(mockStudentEvaluationSearchRepository, times(1)).deleteById(studentEvaluation.getId());
    }

    @Test
    @Transactional
    public void searchStudentEvaluation() throws Exception {
        // Initialize the database
        studentEvaluationRepository.saveAndFlush(studentEvaluation);
        when(mockStudentEvaluationSearchRepository.search(queryStringQuery("id:" + studentEvaluation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(studentEvaluation), PageRequest.of(0, 1), 1));
        // Search the studentEvaluation
        restStudentEvaluationMockMvc.perform(get("/api/_search/student-evaluations?query=id:" + studentEvaluation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentEvaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentEvaluation.class);
        StudentEvaluation studentEvaluation1 = new StudentEvaluation();
        studentEvaluation1.setId(1L);
        StudentEvaluation studentEvaluation2 = new StudentEvaluation();
        studentEvaluation2.setId(studentEvaluation1.getId());
        assertThat(studentEvaluation1).isEqualTo(studentEvaluation2);
        studentEvaluation2.setId(2L);
        assertThat(studentEvaluation1).isNotEqualTo(studentEvaluation2);
        studentEvaluation1.setId(null);
        assertThat(studentEvaluation1).isNotEqualTo(studentEvaluation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentEvaluationDTO.class);
        StudentEvaluationDTO studentEvaluationDTO1 = new StudentEvaluationDTO();
        studentEvaluationDTO1.setId(1L);
        StudentEvaluationDTO studentEvaluationDTO2 = new StudentEvaluationDTO();
        assertThat(studentEvaluationDTO1).isNotEqualTo(studentEvaluationDTO2);
        studentEvaluationDTO2.setId(studentEvaluationDTO1.getId());
        assertThat(studentEvaluationDTO1).isEqualTo(studentEvaluationDTO2);
        studentEvaluationDTO2.setId(2L);
        assertThat(studentEvaluationDTO1).isNotEqualTo(studentEvaluationDTO2);
        studentEvaluationDTO1.setId(null);
        assertThat(studentEvaluationDTO1).isNotEqualTo(studentEvaluationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(studentEvaluationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(studentEvaluationMapper.fromId(null)).isNull();
    }
}
