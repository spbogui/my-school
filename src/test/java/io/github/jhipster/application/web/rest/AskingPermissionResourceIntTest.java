package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.AskingPermission;
import io.github.jhipster.application.repository.AskingPermissionRepository;
import io.github.jhipster.application.repository.search.AskingPermissionSearchRepository;
import io.github.jhipster.application.service.AskingPermissionService;
import io.github.jhipster.application.service.dto.AskingPermissionDTO;
import io.github.jhipster.application.service.mapper.AskingPermissionMapper;
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
 * Test class for the AskingPermissionResource REST controller.
 *
 * @see AskingPermissionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class AskingPermissionResourceIntTest {

    private static final LocalDate DEFAULT_ASKING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ASKING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NUMBER_OF_DAY = 1;
    private static final Integer UPDATED_NUMBER_OF_DAY = 2;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    @Autowired
    private AskingPermissionRepository askingPermissionRepository;

    @Autowired
    private AskingPermissionMapper askingPermissionMapper;

    @Autowired
    private AskingPermissionService askingPermissionService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.AskingPermissionSearchRepositoryMockConfiguration
     */
    @Autowired
    private AskingPermissionSearchRepository mockAskingPermissionSearchRepository;

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

    private MockMvc restAskingPermissionMockMvc;

    private AskingPermission askingPermission;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AskingPermissionResource askingPermissionResource = new AskingPermissionResource(askingPermissionService);
        this.restAskingPermissionMockMvc = MockMvcBuilders.standaloneSetup(askingPermissionResource)
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
    public static AskingPermission createEntity(EntityManager em) {
        AskingPermission askingPermission = new AskingPermission()
            .askingDate(DEFAULT_ASKING_DATE)
            .numberOfDay(DEFAULT_NUMBER_OF_DAY)
            .reason(DEFAULT_REASON);
        return askingPermission;
    }

    @Before
    public void initTest() {
        askingPermission = createEntity(em);
    }

    @Test
    @Transactional
    public void createAskingPermission() throws Exception {
        int databaseSizeBeforeCreate = askingPermissionRepository.findAll().size();

        // Create the AskingPermission
        AskingPermissionDTO askingPermissionDTO = askingPermissionMapper.toDto(askingPermission);
        restAskingPermissionMockMvc.perform(post("/api/asking-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(askingPermissionDTO)))
            .andExpect(status().isCreated());

        // Validate the AskingPermission in the database
        List<AskingPermission> askingPermissionList = askingPermissionRepository.findAll();
        assertThat(askingPermissionList).hasSize(databaseSizeBeforeCreate + 1);
        AskingPermission testAskingPermission = askingPermissionList.get(askingPermissionList.size() - 1);
        assertThat(testAskingPermission.getAskingDate()).isEqualTo(DEFAULT_ASKING_DATE);
        assertThat(testAskingPermission.getNumberOfDay()).isEqualTo(DEFAULT_NUMBER_OF_DAY);
        assertThat(testAskingPermission.getReason()).isEqualTo(DEFAULT_REASON);

        // Validate the AskingPermission in Elasticsearch
        verify(mockAskingPermissionSearchRepository, times(1)).save(testAskingPermission);
    }

    @Test
    @Transactional
    public void createAskingPermissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = askingPermissionRepository.findAll().size();

        // Create the AskingPermission with an existing ID
        askingPermission.setId(1L);
        AskingPermissionDTO askingPermissionDTO = askingPermissionMapper.toDto(askingPermission);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAskingPermissionMockMvc.perform(post("/api/asking-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(askingPermissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AskingPermission in the database
        List<AskingPermission> askingPermissionList = askingPermissionRepository.findAll();
        assertThat(askingPermissionList).hasSize(databaseSizeBeforeCreate);

        // Validate the AskingPermission in Elasticsearch
        verify(mockAskingPermissionSearchRepository, times(0)).save(askingPermission);
    }

    @Test
    @Transactional
    public void checkAskingDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = askingPermissionRepository.findAll().size();
        // set the field null
        askingPermission.setAskingDate(null);

        // Create the AskingPermission, which fails.
        AskingPermissionDTO askingPermissionDTO = askingPermissionMapper.toDto(askingPermission);

        restAskingPermissionMockMvc.perform(post("/api/asking-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(askingPermissionDTO)))
            .andExpect(status().isBadRequest());

        List<AskingPermission> askingPermissionList = askingPermissionRepository.findAll();
        assertThat(askingPermissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberOfDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = askingPermissionRepository.findAll().size();
        // set the field null
        askingPermission.setNumberOfDay(null);

        // Create the AskingPermission, which fails.
        AskingPermissionDTO askingPermissionDTO = askingPermissionMapper.toDto(askingPermission);

        restAskingPermissionMockMvc.perform(post("/api/asking-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(askingPermissionDTO)))
            .andExpect(status().isBadRequest());

        List<AskingPermission> askingPermissionList = askingPermissionRepository.findAll();
        assertThat(askingPermissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReasonIsRequired() throws Exception {
        int databaseSizeBeforeTest = askingPermissionRepository.findAll().size();
        // set the field null
        askingPermission.setReason(null);

        // Create the AskingPermission, which fails.
        AskingPermissionDTO askingPermissionDTO = askingPermissionMapper.toDto(askingPermission);

        restAskingPermissionMockMvc.perform(post("/api/asking-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(askingPermissionDTO)))
            .andExpect(status().isBadRequest());

        List<AskingPermission> askingPermissionList = askingPermissionRepository.findAll();
        assertThat(askingPermissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAskingPermissions() throws Exception {
        // Initialize the database
        askingPermissionRepository.saveAndFlush(askingPermission);

        // Get all the askingPermissionList
        restAskingPermissionMockMvc.perform(get("/api/asking-permissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(askingPermission.getId().intValue())))
            .andExpect(jsonPath("$.[*].askingDate").value(hasItem(DEFAULT_ASKING_DATE.toString())))
            .andExpect(jsonPath("$.[*].numberOfDay").value(hasItem(DEFAULT_NUMBER_OF_DAY)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())));
    }
    
    @Test
    @Transactional
    public void getAskingPermission() throws Exception {
        // Initialize the database
        askingPermissionRepository.saveAndFlush(askingPermission);

        // Get the askingPermission
        restAskingPermissionMockMvc.perform(get("/api/asking-permissions/{id}", askingPermission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(askingPermission.getId().intValue()))
            .andExpect(jsonPath("$.askingDate").value(DEFAULT_ASKING_DATE.toString()))
            .andExpect(jsonPath("$.numberOfDay").value(DEFAULT_NUMBER_OF_DAY))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAskingPermission() throws Exception {
        // Get the askingPermission
        restAskingPermissionMockMvc.perform(get("/api/asking-permissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAskingPermission() throws Exception {
        // Initialize the database
        askingPermissionRepository.saveAndFlush(askingPermission);

        int databaseSizeBeforeUpdate = askingPermissionRepository.findAll().size();

        // Update the askingPermission
        AskingPermission updatedAskingPermission = askingPermissionRepository.findById(askingPermission.getId()).get();
        // Disconnect from session so that the updates on updatedAskingPermission are not directly saved in db
        em.detach(updatedAskingPermission);
        updatedAskingPermission
            .askingDate(UPDATED_ASKING_DATE)
            .numberOfDay(UPDATED_NUMBER_OF_DAY)
            .reason(UPDATED_REASON);
        AskingPermissionDTO askingPermissionDTO = askingPermissionMapper.toDto(updatedAskingPermission);

        restAskingPermissionMockMvc.perform(put("/api/asking-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(askingPermissionDTO)))
            .andExpect(status().isOk());

        // Validate the AskingPermission in the database
        List<AskingPermission> askingPermissionList = askingPermissionRepository.findAll();
        assertThat(askingPermissionList).hasSize(databaseSizeBeforeUpdate);
        AskingPermission testAskingPermission = askingPermissionList.get(askingPermissionList.size() - 1);
        assertThat(testAskingPermission.getAskingDate()).isEqualTo(UPDATED_ASKING_DATE);
        assertThat(testAskingPermission.getNumberOfDay()).isEqualTo(UPDATED_NUMBER_OF_DAY);
        assertThat(testAskingPermission.getReason()).isEqualTo(UPDATED_REASON);

        // Validate the AskingPermission in Elasticsearch
        verify(mockAskingPermissionSearchRepository, times(1)).save(testAskingPermission);
    }

    @Test
    @Transactional
    public void updateNonExistingAskingPermission() throws Exception {
        int databaseSizeBeforeUpdate = askingPermissionRepository.findAll().size();

        // Create the AskingPermission
        AskingPermissionDTO askingPermissionDTO = askingPermissionMapper.toDto(askingPermission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAskingPermissionMockMvc.perform(put("/api/asking-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(askingPermissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AskingPermission in the database
        List<AskingPermission> askingPermissionList = askingPermissionRepository.findAll();
        assertThat(askingPermissionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AskingPermission in Elasticsearch
        verify(mockAskingPermissionSearchRepository, times(0)).save(askingPermission);
    }

    @Test
    @Transactional
    public void deleteAskingPermission() throws Exception {
        // Initialize the database
        askingPermissionRepository.saveAndFlush(askingPermission);

        int databaseSizeBeforeDelete = askingPermissionRepository.findAll().size();

        // Delete the askingPermission
        restAskingPermissionMockMvc.perform(delete("/api/asking-permissions/{id}", askingPermission.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AskingPermission> askingPermissionList = askingPermissionRepository.findAll();
        assertThat(askingPermissionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AskingPermission in Elasticsearch
        verify(mockAskingPermissionSearchRepository, times(1)).deleteById(askingPermission.getId());
    }

    @Test
    @Transactional
    public void searchAskingPermission() throws Exception {
        // Initialize the database
        askingPermissionRepository.saveAndFlush(askingPermission);
        when(mockAskingPermissionSearchRepository.search(queryStringQuery("id:" + askingPermission.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(askingPermission), PageRequest.of(0, 1), 1));
        // Search the askingPermission
        restAskingPermissionMockMvc.perform(get("/api/_search/asking-permissions?query=id:" + askingPermission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(askingPermission.getId().intValue())))
            .andExpect(jsonPath("$.[*].askingDate").value(hasItem(DEFAULT_ASKING_DATE.toString())))
            .andExpect(jsonPath("$.[*].numberOfDay").value(hasItem(DEFAULT_NUMBER_OF_DAY)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AskingPermission.class);
        AskingPermission askingPermission1 = new AskingPermission();
        askingPermission1.setId(1L);
        AskingPermission askingPermission2 = new AskingPermission();
        askingPermission2.setId(askingPermission1.getId());
        assertThat(askingPermission1).isEqualTo(askingPermission2);
        askingPermission2.setId(2L);
        assertThat(askingPermission1).isNotEqualTo(askingPermission2);
        askingPermission1.setId(null);
        assertThat(askingPermission1).isNotEqualTo(askingPermission2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AskingPermissionDTO.class);
        AskingPermissionDTO askingPermissionDTO1 = new AskingPermissionDTO();
        askingPermissionDTO1.setId(1L);
        AskingPermissionDTO askingPermissionDTO2 = new AskingPermissionDTO();
        assertThat(askingPermissionDTO1).isNotEqualTo(askingPermissionDTO2);
        askingPermissionDTO2.setId(askingPermissionDTO1.getId());
        assertThat(askingPermissionDTO1).isEqualTo(askingPermissionDTO2);
        askingPermissionDTO2.setId(2L);
        assertThat(askingPermissionDTO1).isNotEqualTo(askingPermissionDTO2);
        askingPermissionDTO1.setId(null);
        assertThat(askingPermissionDTO1).isNotEqualTo(askingPermissionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(askingPermissionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(askingPermissionMapper.fromId(null)).isNull();
    }
}
