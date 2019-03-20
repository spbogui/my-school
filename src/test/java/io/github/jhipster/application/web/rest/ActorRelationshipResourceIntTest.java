package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.ActorRelationship;
import io.github.jhipster.application.repository.ActorRelationshipRepository;
import io.github.jhipster.application.repository.search.ActorRelationshipSearchRepository;
import io.github.jhipster.application.service.ActorRelationshipService;
import io.github.jhipster.application.service.dto.ActorRelationshipDTO;
import io.github.jhipster.application.service.mapper.ActorRelationshipMapper;
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
 * Test class for the ActorRelationshipResource REST controller.
 *
 * @see ActorRelationshipResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class ActorRelationshipResourceIntTest {

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private ActorRelationshipRepository actorRelationshipRepository;

    @Autowired
    private ActorRelationshipMapper actorRelationshipMapper;

    @Autowired
    private ActorRelationshipService actorRelationshipService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.ActorRelationshipSearchRepositoryMockConfiguration
     */
    @Autowired
    private ActorRelationshipSearchRepository mockActorRelationshipSearchRepository;

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

    private MockMvc restActorRelationshipMockMvc;

    private ActorRelationship actorRelationship;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActorRelationshipResource actorRelationshipResource = new ActorRelationshipResource(actorRelationshipService);
        this.restActorRelationshipMockMvc = MockMvcBuilders.standaloneSetup(actorRelationshipResource)
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
    public static ActorRelationship createEntity(EntityManager em) {
        ActorRelationship actorRelationship = new ActorRelationship()
            .isActive(DEFAULT_IS_ACTIVE);
        return actorRelationship;
    }

    @Before
    public void initTest() {
        actorRelationship = createEntity(em);
    }

    @Test
    @Transactional
    public void createActorRelationship() throws Exception {
        int databaseSizeBeforeCreate = actorRelationshipRepository.findAll().size();

        // Create the ActorRelationship
        ActorRelationshipDTO actorRelationshipDTO = actorRelationshipMapper.toDto(actorRelationship);
        restActorRelationshipMockMvc.perform(post("/api/actor-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actorRelationshipDTO)))
            .andExpect(status().isCreated());

        // Validate the ActorRelationship in the database
        List<ActorRelationship> actorRelationshipList = actorRelationshipRepository.findAll();
        assertThat(actorRelationshipList).hasSize(databaseSizeBeforeCreate + 1);
        ActorRelationship testActorRelationship = actorRelationshipList.get(actorRelationshipList.size() - 1);
        assertThat(testActorRelationship.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);

        // Validate the ActorRelationship in Elasticsearch
        verify(mockActorRelationshipSearchRepository, times(1)).save(testActorRelationship);
    }

    @Test
    @Transactional
    public void createActorRelationshipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actorRelationshipRepository.findAll().size();

        // Create the ActorRelationship with an existing ID
        actorRelationship.setId(1L);
        ActorRelationshipDTO actorRelationshipDTO = actorRelationshipMapper.toDto(actorRelationship);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActorRelationshipMockMvc.perform(post("/api/actor-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actorRelationshipDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActorRelationship in the database
        List<ActorRelationship> actorRelationshipList = actorRelationshipRepository.findAll();
        assertThat(actorRelationshipList).hasSize(databaseSizeBeforeCreate);

        // Validate the ActorRelationship in Elasticsearch
        verify(mockActorRelationshipSearchRepository, times(0)).save(actorRelationship);
    }

    @Test
    @Transactional
    public void getAllActorRelationships() throws Exception {
        // Initialize the database
        actorRelationshipRepository.saveAndFlush(actorRelationship);

        // Get all the actorRelationshipList
        restActorRelationshipMockMvc.perform(get("/api/actor-relationships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actorRelationship.getId().intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getActorRelationship() throws Exception {
        // Initialize the database
        actorRelationshipRepository.saveAndFlush(actorRelationship);

        // Get the actorRelationship
        restActorRelationshipMockMvc.perform(get("/api/actor-relationships/{id}", actorRelationship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(actorRelationship.getId().intValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingActorRelationship() throws Exception {
        // Get the actorRelationship
        restActorRelationshipMockMvc.perform(get("/api/actor-relationships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActorRelationship() throws Exception {
        // Initialize the database
        actorRelationshipRepository.saveAndFlush(actorRelationship);

        int databaseSizeBeforeUpdate = actorRelationshipRepository.findAll().size();

        // Update the actorRelationship
        ActorRelationship updatedActorRelationship = actorRelationshipRepository.findById(actorRelationship.getId()).get();
        // Disconnect from session so that the updates on updatedActorRelationship are not directly saved in db
        em.detach(updatedActorRelationship);
        updatedActorRelationship
            .isActive(UPDATED_IS_ACTIVE);
        ActorRelationshipDTO actorRelationshipDTO = actorRelationshipMapper.toDto(updatedActorRelationship);

        restActorRelationshipMockMvc.perform(put("/api/actor-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actorRelationshipDTO)))
            .andExpect(status().isOk());

        // Validate the ActorRelationship in the database
        List<ActorRelationship> actorRelationshipList = actorRelationshipRepository.findAll();
        assertThat(actorRelationshipList).hasSize(databaseSizeBeforeUpdate);
        ActorRelationship testActorRelationship = actorRelationshipList.get(actorRelationshipList.size() - 1);
        assertThat(testActorRelationship.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);

        // Validate the ActorRelationship in Elasticsearch
        verify(mockActorRelationshipSearchRepository, times(1)).save(testActorRelationship);
    }

    @Test
    @Transactional
    public void updateNonExistingActorRelationship() throws Exception {
        int databaseSizeBeforeUpdate = actorRelationshipRepository.findAll().size();

        // Create the ActorRelationship
        ActorRelationshipDTO actorRelationshipDTO = actorRelationshipMapper.toDto(actorRelationship);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActorRelationshipMockMvc.perform(put("/api/actor-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actorRelationshipDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActorRelationship in the database
        List<ActorRelationship> actorRelationshipList = actorRelationshipRepository.findAll();
        assertThat(actorRelationshipList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ActorRelationship in Elasticsearch
        verify(mockActorRelationshipSearchRepository, times(0)).save(actorRelationship);
    }

    @Test
    @Transactional
    public void deleteActorRelationship() throws Exception {
        // Initialize the database
        actorRelationshipRepository.saveAndFlush(actorRelationship);

        int databaseSizeBeforeDelete = actorRelationshipRepository.findAll().size();

        // Delete the actorRelationship
        restActorRelationshipMockMvc.perform(delete("/api/actor-relationships/{id}", actorRelationship.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ActorRelationship> actorRelationshipList = actorRelationshipRepository.findAll();
        assertThat(actorRelationshipList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ActorRelationship in Elasticsearch
        verify(mockActorRelationshipSearchRepository, times(1)).deleteById(actorRelationship.getId());
    }

    @Test
    @Transactional
    public void searchActorRelationship() throws Exception {
        // Initialize the database
        actorRelationshipRepository.saveAndFlush(actorRelationship);
        when(mockActorRelationshipSearchRepository.search(queryStringQuery("id:" + actorRelationship.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(actorRelationship), PageRequest.of(0, 1), 1));
        // Search the actorRelationship
        restActorRelationshipMockMvc.perform(get("/api/_search/actor-relationships?query=id:" + actorRelationship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actorRelationship.getId().intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActorRelationship.class);
        ActorRelationship actorRelationship1 = new ActorRelationship();
        actorRelationship1.setId(1L);
        ActorRelationship actorRelationship2 = new ActorRelationship();
        actorRelationship2.setId(actorRelationship1.getId());
        assertThat(actorRelationship1).isEqualTo(actorRelationship2);
        actorRelationship2.setId(2L);
        assertThat(actorRelationship1).isNotEqualTo(actorRelationship2);
        actorRelationship1.setId(null);
        assertThat(actorRelationship1).isNotEqualTo(actorRelationship2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActorRelationshipDTO.class);
        ActorRelationshipDTO actorRelationshipDTO1 = new ActorRelationshipDTO();
        actorRelationshipDTO1.setId(1L);
        ActorRelationshipDTO actorRelationshipDTO2 = new ActorRelationshipDTO();
        assertThat(actorRelationshipDTO1).isNotEqualTo(actorRelationshipDTO2);
        actorRelationshipDTO2.setId(actorRelationshipDTO1.getId());
        assertThat(actorRelationshipDTO1).isEqualTo(actorRelationshipDTO2);
        actorRelationshipDTO2.setId(2L);
        assertThat(actorRelationshipDTO1).isNotEqualTo(actorRelationshipDTO2);
        actorRelationshipDTO1.setId(null);
        assertThat(actorRelationshipDTO1).isNotEqualTo(actorRelationshipDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(actorRelationshipMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(actorRelationshipMapper.fromId(null)).isNull();
    }
}
