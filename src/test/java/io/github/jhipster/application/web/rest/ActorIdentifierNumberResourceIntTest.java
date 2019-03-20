package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.ActorIdentifierNumber;
import io.github.jhipster.application.repository.ActorIdentifierNumberRepository;
import io.github.jhipster.application.repository.search.ActorIdentifierNumberSearchRepository;
import io.github.jhipster.application.service.ActorIdentifierNumberService;
import io.github.jhipster.application.service.dto.ActorIdentifierNumberDTO;
import io.github.jhipster.application.service.mapper.ActorIdentifierNumberMapper;
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
 * Test class for the ActorIdentifierNumberResource REST controller.
 *
 * @see ActorIdentifierNumberResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class ActorIdentifierNumberResourceIntTest {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    @Autowired
    private ActorIdentifierNumberRepository actorIdentifierNumberRepository;

    @Autowired
    private ActorIdentifierNumberMapper actorIdentifierNumberMapper;

    @Autowired
    private ActorIdentifierNumberService actorIdentifierNumberService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.ActorIdentifierNumberSearchRepositoryMockConfiguration
     */
    @Autowired
    private ActorIdentifierNumberSearchRepository mockActorIdentifierNumberSearchRepository;

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

    private MockMvc restActorIdentifierNumberMockMvc;

    private ActorIdentifierNumber actorIdentifierNumber;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActorIdentifierNumberResource actorIdentifierNumberResource = new ActorIdentifierNumberResource(actorIdentifierNumberService);
        this.restActorIdentifierNumberMockMvc = MockMvcBuilders.standaloneSetup(actorIdentifierNumberResource)
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
    public static ActorIdentifierNumber createEntity(EntityManager em) {
        ActorIdentifierNumber actorIdentifierNumber = new ActorIdentifierNumber()
            .identifier(DEFAULT_IDENTIFIER);
        return actorIdentifierNumber;
    }

    @Before
    public void initTest() {
        actorIdentifierNumber = createEntity(em);
    }

    @Test
    @Transactional
    public void createActorIdentifierNumber() throws Exception {
        int databaseSizeBeforeCreate = actorIdentifierNumberRepository.findAll().size();

        // Create the ActorIdentifierNumber
        ActorIdentifierNumberDTO actorIdentifierNumberDTO = actorIdentifierNumberMapper.toDto(actorIdentifierNumber);
        restActorIdentifierNumberMockMvc.perform(post("/api/actor-identifier-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actorIdentifierNumberDTO)))
            .andExpect(status().isCreated());

        // Validate the ActorIdentifierNumber in the database
        List<ActorIdentifierNumber> actorIdentifierNumberList = actorIdentifierNumberRepository.findAll();
        assertThat(actorIdentifierNumberList).hasSize(databaseSizeBeforeCreate + 1);
        ActorIdentifierNumber testActorIdentifierNumber = actorIdentifierNumberList.get(actorIdentifierNumberList.size() - 1);
        assertThat(testActorIdentifierNumber.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);

        // Validate the ActorIdentifierNumber in Elasticsearch
        verify(mockActorIdentifierNumberSearchRepository, times(1)).save(testActorIdentifierNumber);
    }

    @Test
    @Transactional
    public void createActorIdentifierNumberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actorIdentifierNumberRepository.findAll().size();

        // Create the ActorIdentifierNumber with an existing ID
        actorIdentifierNumber.setId(1L);
        ActorIdentifierNumberDTO actorIdentifierNumberDTO = actorIdentifierNumberMapper.toDto(actorIdentifierNumber);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActorIdentifierNumberMockMvc.perform(post("/api/actor-identifier-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actorIdentifierNumberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActorIdentifierNumber in the database
        List<ActorIdentifierNumber> actorIdentifierNumberList = actorIdentifierNumberRepository.findAll();
        assertThat(actorIdentifierNumberList).hasSize(databaseSizeBeforeCreate);

        // Validate the ActorIdentifierNumber in Elasticsearch
        verify(mockActorIdentifierNumberSearchRepository, times(0)).save(actorIdentifierNumber);
    }

    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = actorIdentifierNumberRepository.findAll().size();
        // set the field null
        actorIdentifierNumber.setIdentifier(null);

        // Create the ActorIdentifierNumber, which fails.
        ActorIdentifierNumberDTO actorIdentifierNumberDTO = actorIdentifierNumberMapper.toDto(actorIdentifierNumber);

        restActorIdentifierNumberMockMvc.perform(post("/api/actor-identifier-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actorIdentifierNumberDTO)))
            .andExpect(status().isBadRequest());

        List<ActorIdentifierNumber> actorIdentifierNumberList = actorIdentifierNumberRepository.findAll();
        assertThat(actorIdentifierNumberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActorIdentifierNumbers() throws Exception {
        // Initialize the database
        actorIdentifierNumberRepository.saveAndFlush(actorIdentifierNumber);

        // Get all the actorIdentifierNumberList
        restActorIdentifierNumberMockMvc.perform(get("/api/actor-identifier-numbers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actorIdentifierNumber.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())));
    }
    
    @Test
    @Transactional
    public void getActorIdentifierNumber() throws Exception {
        // Initialize the database
        actorIdentifierNumberRepository.saveAndFlush(actorIdentifierNumber);

        // Get the actorIdentifierNumber
        restActorIdentifierNumberMockMvc.perform(get("/api/actor-identifier-numbers/{id}", actorIdentifierNumber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(actorIdentifierNumber.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActorIdentifierNumber() throws Exception {
        // Get the actorIdentifierNumber
        restActorIdentifierNumberMockMvc.perform(get("/api/actor-identifier-numbers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActorIdentifierNumber() throws Exception {
        // Initialize the database
        actorIdentifierNumberRepository.saveAndFlush(actorIdentifierNumber);

        int databaseSizeBeforeUpdate = actorIdentifierNumberRepository.findAll().size();

        // Update the actorIdentifierNumber
        ActorIdentifierNumber updatedActorIdentifierNumber = actorIdentifierNumberRepository.findById(actorIdentifierNumber.getId()).get();
        // Disconnect from session so that the updates on updatedActorIdentifierNumber are not directly saved in db
        em.detach(updatedActorIdentifierNumber);
        updatedActorIdentifierNumber
            .identifier(UPDATED_IDENTIFIER);
        ActorIdentifierNumberDTO actorIdentifierNumberDTO = actorIdentifierNumberMapper.toDto(updatedActorIdentifierNumber);

        restActorIdentifierNumberMockMvc.perform(put("/api/actor-identifier-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actorIdentifierNumberDTO)))
            .andExpect(status().isOk());

        // Validate the ActorIdentifierNumber in the database
        List<ActorIdentifierNumber> actorIdentifierNumberList = actorIdentifierNumberRepository.findAll();
        assertThat(actorIdentifierNumberList).hasSize(databaseSizeBeforeUpdate);
        ActorIdentifierNumber testActorIdentifierNumber = actorIdentifierNumberList.get(actorIdentifierNumberList.size() - 1);
        assertThat(testActorIdentifierNumber.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);

        // Validate the ActorIdentifierNumber in Elasticsearch
        verify(mockActorIdentifierNumberSearchRepository, times(1)).save(testActorIdentifierNumber);
    }

    @Test
    @Transactional
    public void updateNonExistingActorIdentifierNumber() throws Exception {
        int databaseSizeBeforeUpdate = actorIdentifierNumberRepository.findAll().size();

        // Create the ActorIdentifierNumber
        ActorIdentifierNumberDTO actorIdentifierNumberDTO = actorIdentifierNumberMapper.toDto(actorIdentifierNumber);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActorIdentifierNumberMockMvc.perform(put("/api/actor-identifier-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actorIdentifierNumberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActorIdentifierNumber in the database
        List<ActorIdentifierNumber> actorIdentifierNumberList = actorIdentifierNumberRepository.findAll();
        assertThat(actorIdentifierNumberList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ActorIdentifierNumber in Elasticsearch
        verify(mockActorIdentifierNumberSearchRepository, times(0)).save(actorIdentifierNumber);
    }

    @Test
    @Transactional
    public void deleteActorIdentifierNumber() throws Exception {
        // Initialize the database
        actorIdentifierNumberRepository.saveAndFlush(actorIdentifierNumber);

        int databaseSizeBeforeDelete = actorIdentifierNumberRepository.findAll().size();

        // Delete the actorIdentifierNumber
        restActorIdentifierNumberMockMvc.perform(delete("/api/actor-identifier-numbers/{id}", actorIdentifierNumber.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ActorIdentifierNumber> actorIdentifierNumberList = actorIdentifierNumberRepository.findAll();
        assertThat(actorIdentifierNumberList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ActorIdentifierNumber in Elasticsearch
        verify(mockActorIdentifierNumberSearchRepository, times(1)).deleteById(actorIdentifierNumber.getId());
    }

    @Test
    @Transactional
    public void searchActorIdentifierNumber() throws Exception {
        // Initialize the database
        actorIdentifierNumberRepository.saveAndFlush(actorIdentifierNumber);
        when(mockActorIdentifierNumberSearchRepository.search(queryStringQuery("id:" + actorIdentifierNumber.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(actorIdentifierNumber), PageRequest.of(0, 1), 1));
        // Search the actorIdentifierNumber
        restActorIdentifierNumberMockMvc.perform(get("/api/_search/actor-identifier-numbers?query=id:" + actorIdentifierNumber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actorIdentifierNumber.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActorIdentifierNumber.class);
        ActorIdentifierNumber actorIdentifierNumber1 = new ActorIdentifierNumber();
        actorIdentifierNumber1.setId(1L);
        ActorIdentifierNumber actorIdentifierNumber2 = new ActorIdentifierNumber();
        actorIdentifierNumber2.setId(actorIdentifierNumber1.getId());
        assertThat(actorIdentifierNumber1).isEqualTo(actorIdentifierNumber2);
        actorIdentifierNumber2.setId(2L);
        assertThat(actorIdentifierNumber1).isNotEqualTo(actorIdentifierNumber2);
        actorIdentifierNumber1.setId(null);
        assertThat(actorIdentifierNumber1).isNotEqualTo(actorIdentifierNumber2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActorIdentifierNumberDTO.class);
        ActorIdentifierNumberDTO actorIdentifierNumberDTO1 = new ActorIdentifierNumberDTO();
        actorIdentifierNumberDTO1.setId(1L);
        ActorIdentifierNumberDTO actorIdentifierNumberDTO2 = new ActorIdentifierNumberDTO();
        assertThat(actorIdentifierNumberDTO1).isNotEqualTo(actorIdentifierNumberDTO2);
        actorIdentifierNumberDTO2.setId(actorIdentifierNumberDTO1.getId());
        assertThat(actorIdentifierNumberDTO1).isEqualTo(actorIdentifierNumberDTO2);
        actorIdentifierNumberDTO2.setId(2L);
        assertThat(actorIdentifierNumberDTO1).isNotEqualTo(actorIdentifierNumberDTO2);
        actorIdentifierNumberDTO1.setId(null);
        assertThat(actorIdentifierNumberDTO1).isNotEqualTo(actorIdentifierNumberDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(actorIdentifierNumberMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(actorIdentifierNumberMapper.fromId(null)).isNull();
    }
}
