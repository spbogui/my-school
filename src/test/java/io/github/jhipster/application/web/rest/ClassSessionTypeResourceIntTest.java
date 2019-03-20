package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.ClassSessionType;
import io.github.jhipster.application.repository.ClassSessionTypeRepository;
import io.github.jhipster.application.repository.search.ClassSessionTypeSearchRepository;
import io.github.jhipster.application.service.ClassSessionTypeService;
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
 * Test class for the ClassSessionTypeResource REST controller.
 *
 * @see ClassSessionTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class ClassSessionTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ClassSessionTypeRepository classSessionTypeRepository;

    @Autowired
    private ClassSessionTypeService classSessionTypeService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.ClassSessionTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private ClassSessionTypeSearchRepository mockClassSessionTypeSearchRepository;

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

    private MockMvc restClassSessionTypeMockMvc;

    private ClassSessionType classSessionType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClassSessionTypeResource classSessionTypeResource = new ClassSessionTypeResource(classSessionTypeService);
        this.restClassSessionTypeMockMvc = MockMvcBuilders.standaloneSetup(classSessionTypeResource)
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
    public static ClassSessionType createEntity(EntityManager em) {
        ClassSessionType classSessionType = new ClassSessionType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return classSessionType;
    }

    @Before
    public void initTest() {
        classSessionType = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassSessionType() throws Exception {
        int databaseSizeBeforeCreate = classSessionTypeRepository.findAll().size();

        // Create the ClassSessionType
        restClassSessionTypeMockMvc.perform(post("/api/class-session-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classSessionType)))
            .andExpect(status().isCreated());

        // Validate the ClassSessionType in the database
        List<ClassSessionType> classSessionTypeList = classSessionTypeRepository.findAll();
        assertThat(classSessionTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ClassSessionType testClassSessionType = classSessionTypeList.get(classSessionTypeList.size() - 1);
        assertThat(testClassSessionType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClassSessionType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the ClassSessionType in Elasticsearch
        verify(mockClassSessionTypeSearchRepository, times(1)).save(testClassSessionType);
    }

    @Test
    @Transactional
    public void createClassSessionTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classSessionTypeRepository.findAll().size();

        // Create the ClassSessionType with an existing ID
        classSessionType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassSessionTypeMockMvc.perform(post("/api/class-session-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classSessionType)))
            .andExpect(status().isBadRequest());

        // Validate the ClassSessionType in the database
        List<ClassSessionType> classSessionTypeList = classSessionTypeRepository.findAll();
        assertThat(classSessionTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the ClassSessionType in Elasticsearch
        verify(mockClassSessionTypeSearchRepository, times(0)).save(classSessionType);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = classSessionTypeRepository.findAll().size();
        // set the field null
        classSessionType.setName(null);

        // Create the ClassSessionType, which fails.

        restClassSessionTypeMockMvc.perform(post("/api/class-session-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classSessionType)))
            .andExpect(status().isBadRequest());

        List<ClassSessionType> classSessionTypeList = classSessionTypeRepository.findAll();
        assertThat(classSessionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClassSessionTypes() throws Exception {
        // Initialize the database
        classSessionTypeRepository.saveAndFlush(classSessionType);

        // Get all the classSessionTypeList
        restClassSessionTypeMockMvc.perform(get("/api/class-session-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classSessionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getClassSessionType() throws Exception {
        // Initialize the database
        classSessionTypeRepository.saveAndFlush(classSessionType);

        // Get the classSessionType
        restClassSessionTypeMockMvc.perform(get("/api/class-session-types/{id}", classSessionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classSessionType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClassSessionType() throws Exception {
        // Get the classSessionType
        restClassSessionTypeMockMvc.perform(get("/api/class-session-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassSessionType() throws Exception {
        // Initialize the database
        classSessionTypeService.save(classSessionType);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockClassSessionTypeSearchRepository);

        int databaseSizeBeforeUpdate = classSessionTypeRepository.findAll().size();

        // Update the classSessionType
        ClassSessionType updatedClassSessionType = classSessionTypeRepository.findById(classSessionType.getId()).get();
        // Disconnect from session so that the updates on updatedClassSessionType are not directly saved in db
        em.detach(updatedClassSessionType);
        updatedClassSessionType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restClassSessionTypeMockMvc.perform(put("/api/class-session-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClassSessionType)))
            .andExpect(status().isOk());

        // Validate the ClassSessionType in the database
        List<ClassSessionType> classSessionTypeList = classSessionTypeRepository.findAll();
        assertThat(classSessionTypeList).hasSize(databaseSizeBeforeUpdate);
        ClassSessionType testClassSessionType = classSessionTypeList.get(classSessionTypeList.size() - 1);
        assertThat(testClassSessionType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClassSessionType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the ClassSessionType in Elasticsearch
        verify(mockClassSessionTypeSearchRepository, times(1)).save(testClassSessionType);
    }

    @Test
    @Transactional
    public void updateNonExistingClassSessionType() throws Exception {
        int databaseSizeBeforeUpdate = classSessionTypeRepository.findAll().size();

        // Create the ClassSessionType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassSessionTypeMockMvc.perform(put("/api/class-session-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classSessionType)))
            .andExpect(status().isBadRequest());

        // Validate the ClassSessionType in the database
        List<ClassSessionType> classSessionTypeList = classSessionTypeRepository.findAll();
        assertThat(classSessionTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassSessionType in Elasticsearch
        verify(mockClassSessionTypeSearchRepository, times(0)).save(classSessionType);
    }

    @Test
    @Transactional
    public void deleteClassSessionType() throws Exception {
        // Initialize the database
        classSessionTypeService.save(classSessionType);

        int databaseSizeBeforeDelete = classSessionTypeRepository.findAll().size();

        // Delete the classSessionType
        restClassSessionTypeMockMvc.perform(delete("/api/class-session-types/{id}", classSessionType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClassSessionType> classSessionTypeList = classSessionTypeRepository.findAll();
        assertThat(classSessionTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ClassSessionType in Elasticsearch
        verify(mockClassSessionTypeSearchRepository, times(1)).deleteById(classSessionType.getId());
    }

    @Test
    @Transactional
    public void searchClassSessionType() throws Exception {
        // Initialize the database
        classSessionTypeService.save(classSessionType);
        when(mockClassSessionTypeSearchRepository.search(queryStringQuery("id:" + classSessionType.getId())))
            .thenReturn(Collections.singletonList(classSessionType));
        // Search the classSessionType
        restClassSessionTypeMockMvc.perform(get("/api/_search/class-session-types?query=id:" + classSessionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classSessionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassSessionType.class);
        ClassSessionType classSessionType1 = new ClassSessionType();
        classSessionType1.setId(1L);
        ClassSessionType classSessionType2 = new ClassSessionType();
        classSessionType2.setId(classSessionType1.getId());
        assertThat(classSessionType1).isEqualTo(classSessionType2);
        classSessionType2.setId(2L);
        assertThat(classSessionType1).isNotEqualTo(classSessionType2);
        classSessionType1.setId(null);
        assertThat(classSessionType1).isNotEqualTo(classSessionType2);
    }
}
