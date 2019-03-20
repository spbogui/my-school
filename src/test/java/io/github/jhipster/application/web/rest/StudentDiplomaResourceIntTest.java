package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.StudentDiploma;
import io.github.jhipster.application.repository.StudentDiplomaRepository;
import io.github.jhipster.application.repository.search.StudentDiplomaSearchRepository;
import io.github.jhipster.application.service.StudentDiplomaService;
import io.github.jhipster.application.service.dto.StudentDiplomaDTO;
import io.github.jhipster.application.service.mapper.StudentDiplomaMapper;
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
 * Test class for the StudentDiplomaResource REST controller.
 *
 * @see StudentDiplomaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class StudentDiplomaResourceIntTest {

    private static final String DEFAULT_MENTION = "AAAAAAAAAA";
    private static final String UPDATED_MENTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_GRADUATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GRADUATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private StudentDiplomaRepository studentDiplomaRepository;

    @Autowired
    private StudentDiplomaMapper studentDiplomaMapper;

    @Autowired
    private StudentDiplomaService studentDiplomaService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.StudentDiplomaSearchRepositoryMockConfiguration
     */
    @Autowired
    private StudentDiplomaSearchRepository mockStudentDiplomaSearchRepository;

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

    private MockMvc restStudentDiplomaMockMvc;

    private StudentDiploma studentDiploma;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentDiplomaResource studentDiplomaResource = new StudentDiplomaResource(studentDiplomaService);
        this.restStudentDiplomaMockMvc = MockMvcBuilders.standaloneSetup(studentDiplomaResource)
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
    public static StudentDiploma createEntity(EntityManager em) {
        StudentDiploma studentDiploma = new StudentDiploma()
            .mention(DEFAULT_MENTION)
            .graduationDate(DEFAULT_GRADUATION_DATE);
        return studentDiploma;
    }

    @Before
    public void initTest() {
        studentDiploma = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentDiploma() throws Exception {
        int databaseSizeBeforeCreate = studentDiplomaRepository.findAll().size();

        // Create the StudentDiploma
        StudentDiplomaDTO studentDiplomaDTO = studentDiplomaMapper.toDto(studentDiploma);
        restStudentDiplomaMockMvc.perform(post("/api/student-diplomas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentDiplomaDTO)))
            .andExpect(status().isCreated());

        // Validate the StudentDiploma in the database
        List<StudentDiploma> studentDiplomaList = studentDiplomaRepository.findAll();
        assertThat(studentDiplomaList).hasSize(databaseSizeBeforeCreate + 1);
        StudentDiploma testStudentDiploma = studentDiplomaList.get(studentDiplomaList.size() - 1);
        assertThat(testStudentDiploma.getMention()).isEqualTo(DEFAULT_MENTION);
        assertThat(testStudentDiploma.getGraduationDate()).isEqualTo(DEFAULT_GRADUATION_DATE);

        // Validate the StudentDiploma in Elasticsearch
        verify(mockStudentDiplomaSearchRepository, times(1)).save(testStudentDiploma);
    }

    @Test
    @Transactional
    public void createStudentDiplomaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentDiplomaRepository.findAll().size();

        // Create the StudentDiploma with an existing ID
        studentDiploma.setId(1L);
        StudentDiplomaDTO studentDiplomaDTO = studentDiplomaMapper.toDto(studentDiploma);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentDiplomaMockMvc.perform(post("/api/student-diplomas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentDiplomaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentDiploma in the database
        List<StudentDiploma> studentDiplomaList = studentDiplomaRepository.findAll();
        assertThat(studentDiplomaList).hasSize(databaseSizeBeforeCreate);

        // Validate the StudentDiploma in Elasticsearch
        verify(mockStudentDiplomaSearchRepository, times(0)).save(studentDiploma);
    }

    @Test
    @Transactional
    public void checkMentionIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentDiplomaRepository.findAll().size();
        // set the field null
        studentDiploma.setMention(null);

        // Create the StudentDiploma, which fails.
        StudentDiplomaDTO studentDiplomaDTO = studentDiplomaMapper.toDto(studentDiploma);

        restStudentDiplomaMockMvc.perform(post("/api/student-diplomas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentDiplomaDTO)))
            .andExpect(status().isBadRequest());

        List<StudentDiploma> studentDiplomaList = studentDiplomaRepository.findAll();
        assertThat(studentDiplomaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGraduationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentDiplomaRepository.findAll().size();
        // set the field null
        studentDiploma.setGraduationDate(null);

        // Create the StudentDiploma, which fails.
        StudentDiplomaDTO studentDiplomaDTO = studentDiplomaMapper.toDto(studentDiploma);

        restStudentDiplomaMockMvc.perform(post("/api/student-diplomas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentDiplomaDTO)))
            .andExpect(status().isBadRequest());

        List<StudentDiploma> studentDiplomaList = studentDiplomaRepository.findAll();
        assertThat(studentDiplomaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudentDiplomas() throws Exception {
        // Initialize the database
        studentDiplomaRepository.saveAndFlush(studentDiploma);

        // Get all the studentDiplomaList
        restStudentDiplomaMockMvc.perform(get("/api/student-diplomas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentDiploma.getId().intValue())))
            .andExpect(jsonPath("$.[*].mention").value(hasItem(DEFAULT_MENTION.toString())))
            .andExpect(jsonPath("$.[*].graduationDate").value(hasItem(DEFAULT_GRADUATION_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getStudentDiploma() throws Exception {
        // Initialize the database
        studentDiplomaRepository.saveAndFlush(studentDiploma);

        // Get the studentDiploma
        restStudentDiplomaMockMvc.perform(get("/api/student-diplomas/{id}", studentDiploma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentDiploma.getId().intValue()))
            .andExpect(jsonPath("$.mention").value(DEFAULT_MENTION.toString()))
            .andExpect(jsonPath("$.graduationDate").value(DEFAULT_GRADUATION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentDiploma() throws Exception {
        // Get the studentDiploma
        restStudentDiplomaMockMvc.perform(get("/api/student-diplomas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentDiploma() throws Exception {
        // Initialize the database
        studentDiplomaRepository.saveAndFlush(studentDiploma);

        int databaseSizeBeforeUpdate = studentDiplomaRepository.findAll().size();

        // Update the studentDiploma
        StudentDiploma updatedStudentDiploma = studentDiplomaRepository.findById(studentDiploma.getId()).get();
        // Disconnect from session so that the updates on updatedStudentDiploma are not directly saved in db
        em.detach(updatedStudentDiploma);
        updatedStudentDiploma
            .mention(UPDATED_MENTION)
            .graduationDate(UPDATED_GRADUATION_DATE);
        StudentDiplomaDTO studentDiplomaDTO = studentDiplomaMapper.toDto(updatedStudentDiploma);

        restStudentDiplomaMockMvc.perform(put("/api/student-diplomas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentDiplomaDTO)))
            .andExpect(status().isOk());

        // Validate the StudentDiploma in the database
        List<StudentDiploma> studentDiplomaList = studentDiplomaRepository.findAll();
        assertThat(studentDiplomaList).hasSize(databaseSizeBeforeUpdate);
        StudentDiploma testStudentDiploma = studentDiplomaList.get(studentDiplomaList.size() - 1);
        assertThat(testStudentDiploma.getMention()).isEqualTo(UPDATED_MENTION);
        assertThat(testStudentDiploma.getGraduationDate()).isEqualTo(UPDATED_GRADUATION_DATE);

        // Validate the StudentDiploma in Elasticsearch
        verify(mockStudentDiplomaSearchRepository, times(1)).save(testStudentDiploma);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentDiploma() throws Exception {
        int databaseSizeBeforeUpdate = studentDiplomaRepository.findAll().size();

        // Create the StudentDiploma
        StudentDiplomaDTO studentDiplomaDTO = studentDiplomaMapper.toDto(studentDiploma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentDiplomaMockMvc.perform(put("/api/student-diplomas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentDiplomaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentDiploma in the database
        List<StudentDiploma> studentDiplomaList = studentDiplomaRepository.findAll();
        assertThat(studentDiplomaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StudentDiploma in Elasticsearch
        verify(mockStudentDiplomaSearchRepository, times(0)).save(studentDiploma);
    }

    @Test
    @Transactional
    public void deleteStudentDiploma() throws Exception {
        // Initialize the database
        studentDiplomaRepository.saveAndFlush(studentDiploma);

        int databaseSizeBeforeDelete = studentDiplomaRepository.findAll().size();

        // Delete the studentDiploma
        restStudentDiplomaMockMvc.perform(delete("/api/student-diplomas/{id}", studentDiploma.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentDiploma> studentDiplomaList = studentDiplomaRepository.findAll();
        assertThat(studentDiplomaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StudentDiploma in Elasticsearch
        verify(mockStudentDiplomaSearchRepository, times(1)).deleteById(studentDiploma.getId());
    }

    @Test
    @Transactional
    public void searchStudentDiploma() throws Exception {
        // Initialize the database
        studentDiplomaRepository.saveAndFlush(studentDiploma);
        when(mockStudentDiplomaSearchRepository.search(queryStringQuery("id:" + studentDiploma.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(studentDiploma), PageRequest.of(0, 1), 1));
        // Search the studentDiploma
        restStudentDiplomaMockMvc.perform(get("/api/_search/student-diplomas?query=id:" + studentDiploma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentDiploma.getId().intValue())))
            .andExpect(jsonPath("$.[*].mention").value(hasItem(DEFAULT_MENTION)))
            .andExpect(jsonPath("$.[*].graduationDate").value(hasItem(DEFAULT_GRADUATION_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentDiploma.class);
        StudentDiploma studentDiploma1 = new StudentDiploma();
        studentDiploma1.setId(1L);
        StudentDiploma studentDiploma2 = new StudentDiploma();
        studentDiploma2.setId(studentDiploma1.getId());
        assertThat(studentDiploma1).isEqualTo(studentDiploma2);
        studentDiploma2.setId(2L);
        assertThat(studentDiploma1).isNotEqualTo(studentDiploma2);
        studentDiploma1.setId(null);
        assertThat(studentDiploma1).isNotEqualTo(studentDiploma2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentDiplomaDTO.class);
        StudentDiplomaDTO studentDiplomaDTO1 = new StudentDiplomaDTO();
        studentDiplomaDTO1.setId(1L);
        StudentDiplomaDTO studentDiplomaDTO2 = new StudentDiplomaDTO();
        assertThat(studentDiplomaDTO1).isNotEqualTo(studentDiplomaDTO2);
        studentDiplomaDTO2.setId(studentDiplomaDTO1.getId());
        assertThat(studentDiplomaDTO1).isEqualTo(studentDiplomaDTO2);
        studentDiplomaDTO2.setId(2L);
        assertThat(studentDiplomaDTO1).isNotEqualTo(studentDiplomaDTO2);
        studentDiplomaDTO1.setId(null);
        assertThat(studentDiplomaDTO1).isNotEqualTo(studentDiplomaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(studentDiplomaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(studentDiplomaMapper.fromId(null)).isNull();
    }
}
