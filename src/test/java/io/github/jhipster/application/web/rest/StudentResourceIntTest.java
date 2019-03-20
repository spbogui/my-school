package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.Student;
import io.github.jhipster.application.repository.StudentRepository;
import io.github.jhipster.application.repository.search.StudentSearchRepository;
import io.github.jhipster.application.service.StudentService;
import io.github.jhipster.application.service.dto.StudentDTO;
import io.github.jhipster.application.service.mapper.StudentMapper;
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
 * Test class for the StudentResource REST controller.
 *
 * @see StudentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class StudentResourceIntTest {

    private static final Boolean DEFAULT_PAREANT_SEPARATED = false;
    private static final Boolean UPDATED_PAREANT_SEPARATED = true;

    private static final Boolean DEFAULT_FATHER_IS_ALIVE = false;
    private static final Boolean UPDATED_FATHER_IS_ALIVE = true;

    private static final Boolean DEFAULT_LIVING_WITH_FATHER = false;
    private static final Boolean UPDATED_LIVING_WITH_FATHER = true;

    private static final Boolean DEFAULT_MOTHER_IS_ALIVE = false;
    private static final Boolean UPDATED_MOTHER_IS_ALIVE = true;

    private static final Boolean DEFAULT_LIVING_WITH_MOTHER = false;
    private static final Boolean UPDATED_LIVING_WITH_MOTHER = true;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentService studentService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.StudentSearchRepositoryMockConfiguration
     */
    @Autowired
    private StudentSearchRepository mockStudentSearchRepository;

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

    private MockMvc restStudentMockMvc;

    private Student student;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentResource studentResource = new StudentResource(studentService);
        this.restStudentMockMvc = MockMvcBuilders.standaloneSetup(studentResource)
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
    public static Student createEntity(EntityManager em) {
        Student student = new Student()
            .pareantSeparated(DEFAULT_PAREANT_SEPARATED)
            .fatherIsAlive(DEFAULT_FATHER_IS_ALIVE)
            .livingWithFather(DEFAULT_LIVING_WITH_FATHER)
            .motherIsAlive(DEFAULT_MOTHER_IS_ALIVE)
            .livingWithMother(DEFAULT_LIVING_WITH_MOTHER);
        return student;
    }

    @Before
    public void initTest() {
        student = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudent() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);
        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isCreated());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate + 1);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.isPareantSeparated()).isEqualTo(DEFAULT_PAREANT_SEPARATED);
        assertThat(testStudent.isFatherIsAlive()).isEqualTo(DEFAULT_FATHER_IS_ALIVE);
        assertThat(testStudent.isLivingWithFather()).isEqualTo(DEFAULT_LIVING_WITH_FATHER);
        assertThat(testStudent.isMotherIsAlive()).isEqualTo(DEFAULT_MOTHER_IS_ALIVE);
        assertThat(testStudent.isLivingWithMother()).isEqualTo(DEFAULT_LIVING_WITH_MOTHER);

        // Validate the Student in Elasticsearch
        verify(mockStudentSearchRepository, times(1)).save(testStudent);
    }

    @Test
    @Transactional
    public void createStudentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student with an existing ID
        student.setId(1L);
        StudentDTO studentDTO = studentMapper.toDto(student);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate);

        // Validate the Student in Elasticsearch
        verify(mockStudentSearchRepository, times(0)).save(student);
    }

    @Test
    @Transactional
    public void checkFatherIsAliveIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setFatherIsAlive(null);

        // Create the Student, which fails.
        StudentDTO studentDTO = studentMapper.toDto(student);

        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLivingWithFatherIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setLivingWithFather(null);

        // Create the Student, which fails.
        StudentDTO studentDTO = studentMapper.toDto(student);

        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMotherIsAliveIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setMotherIsAlive(null);

        // Create the Student, which fails.
        StudentDTO studentDTO = studentMapper.toDto(student);

        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLivingWithMotherIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setLivingWithMother(null);

        // Create the Student, which fails.
        StudentDTO studentDTO = studentMapper.toDto(student);

        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudents() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList
        restStudentMockMvc.perform(get("/api/students?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].pareantSeparated").value(hasItem(DEFAULT_PAREANT_SEPARATED.booleanValue())))
            .andExpect(jsonPath("$.[*].fatherIsAlive").value(hasItem(DEFAULT_FATHER_IS_ALIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].livingWithFather").value(hasItem(DEFAULT_LIVING_WITH_FATHER.booleanValue())))
            .andExpect(jsonPath("$.[*].motherIsAlive").value(hasItem(DEFAULT_MOTHER_IS_ALIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].livingWithMother").value(hasItem(DEFAULT_LIVING_WITH_MOTHER.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", student.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(student.getId().intValue()))
            .andExpect(jsonPath("$.pareantSeparated").value(DEFAULT_PAREANT_SEPARATED.booleanValue()))
            .andExpect(jsonPath("$.fatherIsAlive").value(DEFAULT_FATHER_IS_ALIVE.booleanValue()))
            .andExpect(jsonPath("$.livingWithFather").value(DEFAULT_LIVING_WITH_FATHER.booleanValue()))
            .andExpect(jsonPath("$.motherIsAlive").value(DEFAULT_MOTHER_IS_ALIVE.booleanValue()))
            .andExpect(jsonPath("$.livingWithMother").value(DEFAULT_LIVING_WITH_MOTHER.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStudent() throws Exception {
        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student
        Student updatedStudent = studentRepository.findById(student.getId()).get();
        // Disconnect from session so that the updates on updatedStudent are not directly saved in db
        em.detach(updatedStudent);
        updatedStudent
            .pareantSeparated(UPDATED_PAREANT_SEPARATED)
            .fatherIsAlive(UPDATED_FATHER_IS_ALIVE)
            .livingWithFather(UPDATED_LIVING_WITH_FATHER)
            .motherIsAlive(UPDATED_MOTHER_IS_ALIVE)
            .livingWithMother(UPDATED_LIVING_WITH_MOTHER);
        StudentDTO studentDTO = studentMapper.toDto(updatedStudent);

        restStudentMockMvc.perform(put("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.isPareantSeparated()).isEqualTo(UPDATED_PAREANT_SEPARATED);
        assertThat(testStudent.isFatherIsAlive()).isEqualTo(UPDATED_FATHER_IS_ALIVE);
        assertThat(testStudent.isLivingWithFather()).isEqualTo(UPDATED_LIVING_WITH_FATHER);
        assertThat(testStudent.isMotherIsAlive()).isEqualTo(UPDATED_MOTHER_IS_ALIVE);
        assertThat(testStudent.isLivingWithMother()).isEqualTo(UPDATED_LIVING_WITH_MOTHER);

        // Validate the Student in Elasticsearch
        verify(mockStudentSearchRepository, times(1)).save(testStudent);
    }

    @Test
    @Transactional
    public void updateNonExistingStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentMockMvc.perform(put("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Student in Elasticsearch
        verify(mockStudentSearchRepository, times(0)).save(student);
    }

    @Test
    @Transactional
    public void deleteStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeDelete = studentRepository.findAll().size();

        // Delete the student
        restStudentMockMvc.perform(delete("/api/students/{id}", student.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Student in Elasticsearch
        verify(mockStudentSearchRepository, times(1)).deleteById(student.getId());
    }

    @Test
    @Transactional
    public void searchStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);
        when(mockStudentSearchRepository.search(queryStringQuery("id:" + student.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(student), PageRequest.of(0, 1), 1));
        // Search the student
        restStudentMockMvc.perform(get("/api/_search/students?query=id:" + student.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].pareantSeparated").value(hasItem(DEFAULT_PAREANT_SEPARATED.booleanValue())))
            .andExpect(jsonPath("$.[*].fatherIsAlive").value(hasItem(DEFAULT_FATHER_IS_ALIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].livingWithFather").value(hasItem(DEFAULT_LIVING_WITH_FATHER.booleanValue())))
            .andExpect(jsonPath("$.[*].motherIsAlive").value(hasItem(DEFAULT_MOTHER_IS_ALIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].livingWithMother").value(hasItem(DEFAULT_LIVING_WITH_MOTHER.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Student.class);
        Student student1 = new Student();
        student1.setId(1L);
        Student student2 = new Student();
        student2.setId(student1.getId());
        assertThat(student1).isEqualTo(student2);
        student2.setId(2L);
        assertThat(student1).isNotEqualTo(student2);
        student1.setId(null);
        assertThat(student1).isNotEqualTo(student2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentDTO.class);
        StudentDTO studentDTO1 = new StudentDTO();
        studentDTO1.setId(1L);
        StudentDTO studentDTO2 = new StudentDTO();
        assertThat(studentDTO1).isNotEqualTo(studentDTO2);
        studentDTO2.setId(studentDTO1.getId());
        assertThat(studentDTO1).isEqualTo(studentDTO2);
        studentDTO2.setId(2L);
        assertThat(studentDTO1).isNotEqualTo(studentDTO2);
        studentDTO1.setId(null);
        assertThat(studentDTO1).isNotEqualTo(studentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(studentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(studentMapper.fromId(null)).isNull();
    }
}
