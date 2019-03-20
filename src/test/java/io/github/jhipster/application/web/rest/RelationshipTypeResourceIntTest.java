package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.RelationshipType;
import io.github.jhipster.application.repository.RelationshipTypeRepository;
import io.github.jhipster.application.repository.search.RelationshipTypeSearchRepository;
import io.github.jhipster.application.service.RelationshipTypeService;
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
 * Test class for the RelationshipTypeResource REST controller.
 *
 * @see RelationshipTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class RelationshipTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RelationshipTypeRepository relationshipTypeRepository;

    @Autowired
    private RelationshipTypeService relationshipTypeService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.RelationshipTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private RelationshipTypeSearchRepository mockRelationshipTypeSearchRepository;

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

    private MockMvc restRelationshipTypeMockMvc;

    private RelationshipType relationshipType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RelationshipTypeResource relationshipTypeResource = new RelationshipTypeResource(relationshipTypeService);
        this.restRelationshipTypeMockMvc = MockMvcBuilders.standaloneSetup(relationshipTypeResource)
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
    public static RelationshipType createEntity(EntityManager em) {
        RelationshipType relationshipType = new RelationshipType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return relationshipType;
    }

    @Before
    public void initTest() {
        relationshipType = createEntity(em);
    }

    @Test
    @Transactional
    public void createRelationshipType() throws Exception {
        int databaseSizeBeforeCreate = relationshipTypeRepository.findAll().size();

        // Create the RelationshipType
        restRelationshipTypeMockMvc.perform(post("/api/relationship-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipType)))
            .andExpect(status().isCreated());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeCreate + 1);
        RelationshipType testRelationshipType = relationshipTypeList.get(relationshipTypeList.size() - 1);
        assertThat(testRelationshipType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRelationshipType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the RelationshipType in Elasticsearch
        verify(mockRelationshipTypeSearchRepository, times(1)).save(testRelationshipType);
    }

    @Test
    @Transactional
    public void createRelationshipTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = relationshipTypeRepository.findAll().size();

        // Create the RelationshipType with an existing ID
        relationshipType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelationshipTypeMockMvc.perform(post("/api/relationship-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipType)))
            .andExpect(status().isBadRequest());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the RelationshipType in Elasticsearch
        verify(mockRelationshipTypeSearchRepository, times(0)).save(relationshipType);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = relationshipTypeRepository.findAll().size();
        // set the field null
        relationshipType.setName(null);

        // Create the RelationshipType, which fails.

        restRelationshipTypeMockMvc.perform(post("/api/relationship-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipType)))
            .andExpect(status().isBadRequest());

        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRelationshipTypes() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get all the relationshipTypeList
        restRelationshipTypeMockMvc.perform(get("/api/relationship-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relationshipType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getRelationshipType() throws Exception {
        // Initialize the database
        relationshipTypeRepository.saveAndFlush(relationshipType);

        // Get the relationshipType
        restRelationshipTypeMockMvc.perform(get("/api/relationship-types/{id}", relationshipType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(relationshipType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRelationshipType() throws Exception {
        // Get the relationshipType
        restRelationshipTypeMockMvc.perform(get("/api/relationship-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRelationshipType() throws Exception {
        // Initialize the database
        relationshipTypeService.save(relationshipType);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockRelationshipTypeSearchRepository);

        int databaseSizeBeforeUpdate = relationshipTypeRepository.findAll().size();

        // Update the relationshipType
        RelationshipType updatedRelationshipType = relationshipTypeRepository.findById(relationshipType.getId()).get();
        // Disconnect from session so that the updates on updatedRelationshipType are not directly saved in db
        em.detach(updatedRelationshipType);
        updatedRelationshipType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restRelationshipTypeMockMvc.perform(put("/api/relationship-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRelationshipType)))
            .andExpect(status().isOk());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeUpdate);
        RelationshipType testRelationshipType = relationshipTypeList.get(relationshipTypeList.size() - 1);
        assertThat(testRelationshipType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRelationshipType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the RelationshipType in Elasticsearch
        verify(mockRelationshipTypeSearchRepository, times(1)).save(testRelationshipType);
    }

    @Test
    @Transactional
    public void updateNonExistingRelationshipType() throws Exception {
        int databaseSizeBeforeUpdate = relationshipTypeRepository.findAll().size();

        // Create the RelationshipType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelationshipTypeMockMvc.perform(put("/api/relationship-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipType)))
            .andExpect(status().isBadRequest());

        // Validate the RelationshipType in the database
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RelationshipType in Elasticsearch
        verify(mockRelationshipTypeSearchRepository, times(0)).save(relationshipType);
    }

    @Test
    @Transactional
    public void deleteRelationshipType() throws Exception {
        // Initialize the database
        relationshipTypeService.save(relationshipType);

        int databaseSizeBeforeDelete = relationshipTypeRepository.findAll().size();

        // Delete the relationshipType
        restRelationshipTypeMockMvc.perform(delete("/api/relationship-types/{id}", relationshipType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RelationshipType> relationshipTypeList = relationshipTypeRepository.findAll();
        assertThat(relationshipTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RelationshipType in Elasticsearch
        verify(mockRelationshipTypeSearchRepository, times(1)).deleteById(relationshipType.getId());
    }

    @Test
    @Transactional
    public void searchRelationshipType() throws Exception {
        // Initialize the database
        relationshipTypeService.save(relationshipType);
        when(mockRelationshipTypeSearchRepository.search(queryStringQuery("id:" + relationshipType.getId())))
            .thenReturn(Collections.singletonList(relationshipType));
        // Search the relationshipType
        restRelationshipTypeMockMvc.perform(get("/api/_search/relationship-types?query=id:" + relationshipType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relationshipType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelationshipType.class);
        RelationshipType relationshipType1 = new RelationshipType();
        relationshipType1.setId(1L);
        RelationshipType relationshipType2 = new RelationshipType();
        relationshipType2.setId(relationshipType1.getId());
        assertThat(relationshipType1).isEqualTo(relationshipType2);
        relationshipType2.setId(2L);
        assertThat(relationshipType1).isNotEqualTo(relationshipType2);
        relationshipType1.setId(null);
        assertThat(relationshipType1).isNotEqualTo(relationshipType2);
    }
}
