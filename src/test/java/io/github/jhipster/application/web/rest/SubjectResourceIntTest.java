package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.Subject;
import io.github.jhipster.application.repository.SubjectRepository;
import io.github.jhipster.application.repository.search.SubjectSearchRepository;
import io.github.jhipster.application.service.SubjectService;
import io.github.jhipster.application.service.dto.SubjectDTO;
import io.github.jhipster.application.service.mapper.SubjectMapper;
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
 * Test class for the SubjectResource REST controller.
 *
 * @see SubjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class SubjectResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private SubjectService subjectService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.SubjectSearchRepositoryMockConfiguration
     */
    @Autowired
    private SubjectSearchRepository mockSubjectSearchRepository;

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

    private MockMvc restSubjectMockMvc;

    private Subject subject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubjectResource subjectResource = new SubjectResource(subjectService);
        this.restSubjectMockMvc = MockMvcBuilders.standaloneSetup(subjectResource)
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
    public static Subject createEntity(EntityManager em) {
        Subject subject = new Subject()
            .label(DEFAULT_LABEL)
            .description(DEFAULT_DESCRIPTION);
        return subject;
    }

    @Before
    public void initTest() {
        subject = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubject() throws Exception {
        int databaseSizeBeforeCreate = subjectRepository.findAll().size();

        // Create the Subject
        SubjectDTO subjectDTO = subjectMapper.toDto(subject);
        restSubjectMockMvc.perform(post("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectDTO)))
            .andExpect(status().isCreated());

        // Validate the Subject in the database
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeCreate + 1);
        Subject testSubject = subjectList.get(subjectList.size() - 1);
        assertThat(testSubject.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testSubject.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Subject in Elasticsearch
        verify(mockSubjectSearchRepository, times(1)).save(testSubject);
    }

    @Test
    @Transactional
    public void createSubjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subjectRepository.findAll().size();

        // Create the Subject with an existing ID
        subject.setId(1L);
        SubjectDTO subjectDTO = subjectMapper.toDto(subject);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubjectMockMvc.perform(post("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Subject in the database
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeCreate);

        // Validate the Subject in Elasticsearch
        verify(mockSubjectSearchRepository, times(0)).save(subject);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = subjectRepository.findAll().size();
        // set the field null
        subject.setLabel(null);

        // Create the Subject, which fails.
        SubjectDTO subjectDTO = subjectMapper.toDto(subject);

        restSubjectMockMvc.perform(post("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectDTO)))
            .andExpect(status().isBadRequest());

        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubjects() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList
        restSubjectMockMvc.perform(get("/api/subjects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subject.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getSubject() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get the subject
        restSubjectMockMvc.perform(get("/api/subjects/{id}", subject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subject.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSubject() throws Exception {
        // Get the subject
        restSubjectMockMvc.perform(get("/api/subjects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubject() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        int databaseSizeBeforeUpdate = subjectRepository.findAll().size();

        // Update the subject
        Subject updatedSubject = subjectRepository.findById(subject.getId()).get();
        // Disconnect from session so that the updates on updatedSubject are not directly saved in db
        em.detach(updatedSubject);
        updatedSubject
            .label(UPDATED_LABEL)
            .description(UPDATED_DESCRIPTION);
        SubjectDTO subjectDTO = subjectMapper.toDto(updatedSubject);

        restSubjectMockMvc.perform(put("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectDTO)))
            .andExpect(status().isOk());

        // Validate the Subject in the database
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeUpdate);
        Subject testSubject = subjectList.get(subjectList.size() - 1);
        assertThat(testSubject.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testSubject.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Subject in Elasticsearch
        verify(mockSubjectSearchRepository, times(1)).save(testSubject);
    }

    @Test
    @Transactional
    public void updateNonExistingSubject() throws Exception {
        int databaseSizeBeforeUpdate = subjectRepository.findAll().size();

        // Create the Subject
        SubjectDTO subjectDTO = subjectMapper.toDto(subject);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubjectMockMvc.perform(put("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Subject in the database
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Subject in Elasticsearch
        verify(mockSubjectSearchRepository, times(0)).save(subject);
    }

    @Test
    @Transactional
    public void deleteSubject() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        int databaseSizeBeforeDelete = subjectRepository.findAll().size();

        // Delete the subject
        restSubjectMockMvc.perform(delete("/api/subjects/{id}", subject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Subject in Elasticsearch
        verify(mockSubjectSearchRepository, times(1)).deleteById(subject.getId());
    }

    @Test
    @Transactional
    public void searchSubject() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);
        when(mockSubjectSearchRepository.search(queryStringQuery("id:" + subject.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(subject), PageRequest.of(0, 1), 1));
        // Search the subject
        restSubjectMockMvc.perform(get("/api/_search/subjects?query=id:" + subject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subject.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subject.class);
        Subject subject1 = new Subject();
        subject1.setId(1L);
        Subject subject2 = new Subject();
        subject2.setId(subject1.getId());
        assertThat(subject1).isEqualTo(subject2);
        subject2.setId(2L);
        assertThat(subject1).isNotEqualTo(subject2);
        subject1.setId(null);
        assertThat(subject1).isNotEqualTo(subject2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubjectDTO.class);
        SubjectDTO subjectDTO1 = new SubjectDTO();
        subjectDTO1.setId(1L);
        SubjectDTO subjectDTO2 = new SubjectDTO();
        assertThat(subjectDTO1).isNotEqualTo(subjectDTO2);
        subjectDTO2.setId(subjectDTO1.getId());
        assertThat(subjectDTO1).isEqualTo(subjectDTO2);
        subjectDTO2.setId(2L);
        assertThat(subjectDTO1).isNotEqualTo(subjectDTO2);
        subjectDTO1.setId(null);
        assertThat(subjectDTO1).isNotEqualTo(subjectDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(subjectMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(subjectMapper.fromId(null)).isNull();
    }
}
