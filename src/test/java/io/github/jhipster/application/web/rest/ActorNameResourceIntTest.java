package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.ActorName;
import io.github.jhipster.application.repository.ActorNameRepository;
import io.github.jhipster.application.repository.search.ActorNameSearchRepository;
import io.github.jhipster.application.service.ActorNameService;
import io.github.jhipster.application.service.dto.ActorNameDTO;
import io.github.jhipster.application.service.mapper.ActorNameMapper;
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
 * Test class for the ActorNameResource REST controller.
 *
 * @see ActorNameResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class ActorNameResourceIntTest {

    private static final String DEFAULT_CIVILITY = "AAAAAAAAAA";
    private static final String UPDATED_CIVILITY = "BBBBBBBBBB";

    private static final String DEFAULT_FAMILY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FAMILY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GIVEN_NAME_1 = "AAAAAAAAAA";
    private static final String UPDATED_GIVEN_NAME_1 = "BBBBBBBBBB";

    private static final String DEFAULT_GIVEN_NAME_2 = "AAAAAAAAAA";
    private static final String UPDATED_GIVEN_NAME_2 = "BBBBBBBBBB";

    private static final String DEFAULT_GIVEN_NAME_3 = "AAAAAAAAAA";
    private static final String UPDATED_GIVEN_NAME_3 = "BBBBBBBBBB";

    private static final String DEFAULT_MAIDEN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MAIDEN_NAME = "BBBBBBBBBB";

    @Autowired
    private ActorNameRepository actorNameRepository;

    @Autowired
    private ActorNameMapper actorNameMapper;

    @Autowired
    private ActorNameService actorNameService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.ActorNameSearchRepositoryMockConfiguration
     */
    @Autowired
    private ActorNameSearchRepository mockActorNameSearchRepository;

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

    private MockMvc restActorNameMockMvc;

    private ActorName actorName;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActorNameResource actorNameResource = new ActorNameResource(actorNameService);
        this.restActorNameMockMvc = MockMvcBuilders.standaloneSetup(actorNameResource)
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
    public static ActorName createEntity(EntityManager em) {
        ActorName actorName = new ActorName()
            .civility(DEFAULT_CIVILITY)
            .familyName(DEFAULT_FAMILY_NAME)
            .givenName1(DEFAULT_GIVEN_NAME_1)
            .givenName2(DEFAULT_GIVEN_NAME_2)
            .givenName3(DEFAULT_GIVEN_NAME_3)
            .maidenName(DEFAULT_MAIDEN_NAME);
        return actorName;
    }

    @Before
    public void initTest() {
        actorName = createEntity(em);
    }

    @Test
    @Transactional
    public void createActorName() throws Exception {
        int databaseSizeBeforeCreate = actorNameRepository.findAll().size();

        // Create the ActorName
        ActorNameDTO actorNameDTO = actorNameMapper.toDto(actorName);
        restActorNameMockMvc.perform(post("/api/actor-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actorNameDTO)))
            .andExpect(status().isCreated());

        // Validate the ActorName in the database
        List<ActorName> actorNameList = actorNameRepository.findAll();
        assertThat(actorNameList).hasSize(databaseSizeBeforeCreate + 1);
        ActorName testActorName = actorNameList.get(actorNameList.size() - 1);
        assertThat(testActorName.getCivility()).isEqualTo(DEFAULT_CIVILITY);
        assertThat(testActorName.getFamilyName()).isEqualTo(DEFAULT_FAMILY_NAME);
        assertThat(testActorName.getGivenName1()).isEqualTo(DEFAULT_GIVEN_NAME_1);
        assertThat(testActorName.getGivenName2()).isEqualTo(DEFAULT_GIVEN_NAME_2);
        assertThat(testActorName.getGivenName3()).isEqualTo(DEFAULT_GIVEN_NAME_3);
        assertThat(testActorName.getMaidenName()).isEqualTo(DEFAULT_MAIDEN_NAME);

        // Validate the ActorName in Elasticsearch
        verify(mockActorNameSearchRepository, times(1)).save(testActorName);
    }

    @Test
    @Transactional
    public void createActorNameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actorNameRepository.findAll().size();

        // Create the ActorName with an existing ID
        actorName.setId(1L);
        ActorNameDTO actorNameDTO = actorNameMapper.toDto(actorName);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActorNameMockMvc.perform(post("/api/actor-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actorNameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActorName in the database
        List<ActorName> actorNameList = actorNameRepository.findAll();
        assertThat(actorNameList).hasSize(databaseSizeBeforeCreate);

        // Validate the ActorName in Elasticsearch
        verify(mockActorNameSearchRepository, times(0)).save(actorName);
    }

    @Test
    @Transactional
    public void checkCivilityIsRequired() throws Exception {
        int databaseSizeBeforeTest = actorNameRepository.findAll().size();
        // set the field null
        actorName.setCivility(null);

        // Create the ActorName, which fails.
        ActorNameDTO actorNameDTO = actorNameMapper.toDto(actorName);

        restActorNameMockMvc.perform(post("/api/actor-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actorNameDTO)))
            .andExpect(status().isBadRequest());

        List<ActorName> actorNameList = actorNameRepository.findAll();
        assertThat(actorNameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFamilyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = actorNameRepository.findAll().size();
        // set the field null
        actorName.setFamilyName(null);

        // Create the ActorName, which fails.
        ActorNameDTO actorNameDTO = actorNameMapper.toDto(actorName);

        restActorNameMockMvc.perform(post("/api/actor-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actorNameDTO)))
            .andExpect(status().isBadRequest());

        List<ActorName> actorNameList = actorNameRepository.findAll();
        assertThat(actorNameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGivenName1IsRequired() throws Exception {
        int databaseSizeBeforeTest = actorNameRepository.findAll().size();
        // set the field null
        actorName.setGivenName1(null);

        // Create the ActorName, which fails.
        ActorNameDTO actorNameDTO = actorNameMapper.toDto(actorName);

        restActorNameMockMvc.perform(post("/api/actor-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actorNameDTO)))
            .andExpect(status().isBadRequest());

        List<ActorName> actorNameList = actorNameRepository.findAll();
        assertThat(actorNameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActorNames() throws Exception {
        // Initialize the database
        actorNameRepository.saveAndFlush(actorName);

        // Get all the actorNameList
        restActorNameMockMvc.perform(get("/api/actor-names?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actorName.getId().intValue())))
            .andExpect(jsonPath("$.[*].civility").value(hasItem(DEFAULT_CIVILITY.toString())))
            .andExpect(jsonPath("$.[*].familyName").value(hasItem(DEFAULT_FAMILY_NAME.toString())))
            .andExpect(jsonPath("$.[*].givenName1").value(hasItem(DEFAULT_GIVEN_NAME_1.toString())))
            .andExpect(jsonPath("$.[*].givenName2").value(hasItem(DEFAULT_GIVEN_NAME_2.toString())))
            .andExpect(jsonPath("$.[*].givenName3").value(hasItem(DEFAULT_GIVEN_NAME_3.toString())))
            .andExpect(jsonPath("$.[*].maidenName").value(hasItem(DEFAULT_MAIDEN_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getActorName() throws Exception {
        // Initialize the database
        actorNameRepository.saveAndFlush(actorName);

        // Get the actorName
        restActorNameMockMvc.perform(get("/api/actor-names/{id}", actorName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(actorName.getId().intValue()))
            .andExpect(jsonPath("$.civility").value(DEFAULT_CIVILITY.toString()))
            .andExpect(jsonPath("$.familyName").value(DEFAULT_FAMILY_NAME.toString()))
            .andExpect(jsonPath("$.givenName1").value(DEFAULT_GIVEN_NAME_1.toString()))
            .andExpect(jsonPath("$.givenName2").value(DEFAULT_GIVEN_NAME_2.toString()))
            .andExpect(jsonPath("$.givenName3").value(DEFAULT_GIVEN_NAME_3.toString()))
            .andExpect(jsonPath("$.maidenName").value(DEFAULT_MAIDEN_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActorName() throws Exception {
        // Get the actorName
        restActorNameMockMvc.perform(get("/api/actor-names/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActorName() throws Exception {
        // Initialize the database
        actorNameRepository.saveAndFlush(actorName);

        int databaseSizeBeforeUpdate = actorNameRepository.findAll().size();

        // Update the actorName
        ActorName updatedActorName = actorNameRepository.findById(actorName.getId()).get();
        // Disconnect from session so that the updates on updatedActorName are not directly saved in db
        em.detach(updatedActorName);
        updatedActorName
            .civility(UPDATED_CIVILITY)
            .familyName(UPDATED_FAMILY_NAME)
            .givenName1(UPDATED_GIVEN_NAME_1)
            .givenName2(UPDATED_GIVEN_NAME_2)
            .givenName3(UPDATED_GIVEN_NAME_3)
            .maidenName(UPDATED_MAIDEN_NAME);
        ActorNameDTO actorNameDTO = actorNameMapper.toDto(updatedActorName);

        restActorNameMockMvc.perform(put("/api/actor-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actorNameDTO)))
            .andExpect(status().isOk());

        // Validate the ActorName in the database
        List<ActorName> actorNameList = actorNameRepository.findAll();
        assertThat(actorNameList).hasSize(databaseSizeBeforeUpdate);
        ActorName testActorName = actorNameList.get(actorNameList.size() - 1);
        assertThat(testActorName.getCivility()).isEqualTo(UPDATED_CIVILITY);
        assertThat(testActorName.getFamilyName()).isEqualTo(UPDATED_FAMILY_NAME);
        assertThat(testActorName.getGivenName1()).isEqualTo(UPDATED_GIVEN_NAME_1);
        assertThat(testActorName.getGivenName2()).isEqualTo(UPDATED_GIVEN_NAME_2);
        assertThat(testActorName.getGivenName3()).isEqualTo(UPDATED_GIVEN_NAME_3);
        assertThat(testActorName.getMaidenName()).isEqualTo(UPDATED_MAIDEN_NAME);

        // Validate the ActorName in Elasticsearch
        verify(mockActorNameSearchRepository, times(1)).save(testActorName);
    }

    @Test
    @Transactional
    public void updateNonExistingActorName() throws Exception {
        int databaseSizeBeforeUpdate = actorNameRepository.findAll().size();

        // Create the ActorName
        ActorNameDTO actorNameDTO = actorNameMapper.toDto(actorName);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActorNameMockMvc.perform(put("/api/actor-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actorNameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActorName in the database
        List<ActorName> actorNameList = actorNameRepository.findAll();
        assertThat(actorNameList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ActorName in Elasticsearch
        verify(mockActorNameSearchRepository, times(0)).save(actorName);
    }

    @Test
    @Transactional
    public void deleteActorName() throws Exception {
        // Initialize the database
        actorNameRepository.saveAndFlush(actorName);

        int databaseSizeBeforeDelete = actorNameRepository.findAll().size();

        // Delete the actorName
        restActorNameMockMvc.perform(delete("/api/actor-names/{id}", actorName.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ActorName> actorNameList = actorNameRepository.findAll();
        assertThat(actorNameList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ActorName in Elasticsearch
        verify(mockActorNameSearchRepository, times(1)).deleteById(actorName.getId());
    }

    @Test
    @Transactional
    public void searchActorName() throws Exception {
        // Initialize the database
        actorNameRepository.saveAndFlush(actorName);
        when(mockActorNameSearchRepository.search(queryStringQuery("id:" + actorName.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(actorName), PageRequest.of(0, 1), 1));
        // Search the actorName
        restActorNameMockMvc.perform(get("/api/_search/actor-names?query=id:" + actorName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actorName.getId().intValue())))
            .andExpect(jsonPath("$.[*].civility").value(hasItem(DEFAULT_CIVILITY)))
            .andExpect(jsonPath("$.[*].familyName").value(hasItem(DEFAULT_FAMILY_NAME)))
            .andExpect(jsonPath("$.[*].givenName1").value(hasItem(DEFAULT_GIVEN_NAME_1)))
            .andExpect(jsonPath("$.[*].givenName2").value(hasItem(DEFAULT_GIVEN_NAME_2)))
            .andExpect(jsonPath("$.[*].givenName3").value(hasItem(DEFAULT_GIVEN_NAME_3)))
            .andExpect(jsonPath("$.[*].maidenName").value(hasItem(DEFAULT_MAIDEN_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActorName.class);
        ActorName actorName1 = new ActorName();
        actorName1.setId(1L);
        ActorName actorName2 = new ActorName();
        actorName2.setId(actorName1.getId());
        assertThat(actorName1).isEqualTo(actorName2);
        actorName2.setId(2L);
        assertThat(actorName1).isNotEqualTo(actorName2);
        actorName1.setId(null);
        assertThat(actorName1).isNotEqualTo(actorName2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActorNameDTO.class);
        ActorNameDTO actorNameDTO1 = new ActorNameDTO();
        actorNameDTO1.setId(1L);
        ActorNameDTO actorNameDTO2 = new ActorNameDTO();
        assertThat(actorNameDTO1).isNotEqualTo(actorNameDTO2);
        actorNameDTO2.setId(actorNameDTO1.getId());
        assertThat(actorNameDTO1).isEqualTo(actorNameDTO2);
        actorNameDTO2.setId(2L);
        assertThat(actorNameDTO1).isNotEqualTo(actorNameDTO2);
        actorNameDTO1.setId(null);
        assertThat(actorNameDTO1).isNotEqualTo(actorNameDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(actorNameMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(actorNameMapper.fromId(null)).isNull();
    }
}
