package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.Enrolment;
import io.github.jhipster.application.repository.EnrolmentRepository;
import io.github.jhipster.application.repository.search.EnrolmentSearchRepository;
import io.github.jhipster.application.service.EnrolmentService;
import io.github.jhipster.application.service.dto.EnrolmentDTO;
import io.github.jhipster.application.service.mapper.EnrolmentMapper;
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
 * Test class for the EnrolmentResource REST controller.
 *
 * @see EnrolmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class EnrolmentResourceIntTest {

    private static final LocalDate DEFAULT_ENROLMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENROLMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_REGIMEN_RATE = 1D;
    private static final Double UPDATED_REGIMEN_RATE = 2D;

    private static final Boolean DEFAULT_REPEATING = false;
    private static final Boolean UPDATED_REPEATING = true;

    private static final String DEFAULT_MODERN_LANGUAGE_1 = "AAAAAAAAAA";
    private static final String UPDATED_MODERN_LANGUAGE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_MODERN_LANGUAGE_2 = "AAAAAAAAAA";
    private static final String UPDATED_MODERN_LANGUAGE_2 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EXEMPTION = false;
    private static final Boolean UPDATED_EXEMPTION = true;

    private static final Boolean DEFAULT_WITH_INSURANCE = false;
    private static final Boolean UPDATED_WITH_INSURANCE = true;

    @Autowired
    private EnrolmentRepository enrolmentRepository;

    @Autowired
    private EnrolmentMapper enrolmentMapper;

    @Autowired
    private EnrolmentService enrolmentService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.EnrolmentSearchRepositoryMockConfiguration
     */
    @Autowired
    private EnrolmentSearchRepository mockEnrolmentSearchRepository;

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

    private MockMvc restEnrolmentMockMvc;

    private Enrolment enrolment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnrolmentResource enrolmentResource = new EnrolmentResource(enrolmentService);
        this.restEnrolmentMockMvc = MockMvcBuilders.standaloneSetup(enrolmentResource)
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
    public static Enrolment createEntity(EntityManager em) {
        Enrolment enrolment = new Enrolment()
            .enrolmentDate(DEFAULT_ENROLMENT_DATE)
            .regimenRate(DEFAULT_REGIMEN_RATE)
            .repeating(DEFAULT_REPEATING)
            .modernLanguage1(DEFAULT_MODERN_LANGUAGE_1)
            .modernLanguage2(DEFAULT_MODERN_LANGUAGE_2)
            .exemption(DEFAULT_EXEMPTION)
            .withInsurance(DEFAULT_WITH_INSURANCE);
        return enrolment;
    }

    @Before
    public void initTest() {
        enrolment = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnrolment() throws Exception {
        int databaseSizeBeforeCreate = enrolmentRepository.findAll().size();

        // Create the Enrolment
        EnrolmentDTO enrolmentDTO = enrolmentMapper.toDto(enrolment);
        restEnrolmentMockMvc.perform(post("/api/enrolments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrolmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Enrolment in the database
        List<Enrolment> enrolmentList = enrolmentRepository.findAll();
        assertThat(enrolmentList).hasSize(databaseSizeBeforeCreate + 1);
        Enrolment testEnrolment = enrolmentList.get(enrolmentList.size() - 1);
        assertThat(testEnrolment.getEnrolmentDate()).isEqualTo(DEFAULT_ENROLMENT_DATE);
        assertThat(testEnrolment.getRegimenRate()).isEqualTo(DEFAULT_REGIMEN_RATE);
        assertThat(testEnrolment.isRepeating()).isEqualTo(DEFAULT_REPEATING);
        assertThat(testEnrolment.getModernLanguage1()).isEqualTo(DEFAULT_MODERN_LANGUAGE_1);
        assertThat(testEnrolment.getModernLanguage2()).isEqualTo(DEFAULT_MODERN_LANGUAGE_2);
        assertThat(testEnrolment.isExemption()).isEqualTo(DEFAULT_EXEMPTION);
        assertThat(testEnrolment.isWithInsurance()).isEqualTo(DEFAULT_WITH_INSURANCE);

        // Validate the Enrolment in Elasticsearch
        verify(mockEnrolmentSearchRepository, times(1)).save(testEnrolment);
    }

    @Test
    @Transactional
    public void createEnrolmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enrolmentRepository.findAll().size();

        // Create the Enrolment with an existing ID
        enrolment.setId(1L);
        EnrolmentDTO enrolmentDTO = enrolmentMapper.toDto(enrolment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnrolmentMockMvc.perform(post("/api/enrolments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrolmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enrolment in the database
        List<Enrolment> enrolmentList = enrolmentRepository.findAll();
        assertThat(enrolmentList).hasSize(databaseSizeBeforeCreate);

        // Validate the Enrolment in Elasticsearch
        verify(mockEnrolmentSearchRepository, times(0)).save(enrolment);
    }

    @Test
    @Transactional
    public void checkEnrolmentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = enrolmentRepository.findAll().size();
        // set the field null
        enrolment.setEnrolmentDate(null);

        // Create the Enrolment, which fails.
        EnrolmentDTO enrolmentDTO = enrolmentMapper.toDto(enrolment);

        restEnrolmentMockMvc.perform(post("/api/enrolments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrolmentDTO)))
            .andExpect(status().isBadRequest());

        List<Enrolment> enrolmentList = enrolmentRepository.findAll();
        assertThat(enrolmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModernLanguage1IsRequired() throws Exception {
        int databaseSizeBeforeTest = enrolmentRepository.findAll().size();
        // set the field null
        enrolment.setModernLanguage1(null);

        // Create the Enrolment, which fails.
        EnrolmentDTO enrolmentDTO = enrolmentMapper.toDto(enrolment);

        restEnrolmentMockMvc.perform(post("/api/enrolments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrolmentDTO)))
            .andExpect(status().isBadRequest());

        List<Enrolment> enrolmentList = enrolmentRepository.findAll();
        assertThat(enrolmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExemptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = enrolmentRepository.findAll().size();
        // set the field null
        enrolment.setExemption(null);

        // Create the Enrolment, which fails.
        EnrolmentDTO enrolmentDTO = enrolmentMapper.toDto(enrolment);

        restEnrolmentMockMvc.perform(post("/api/enrolments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrolmentDTO)))
            .andExpect(status().isBadRequest());

        List<Enrolment> enrolmentList = enrolmentRepository.findAll();
        assertThat(enrolmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWithInsuranceIsRequired() throws Exception {
        int databaseSizeBeforeTest = enrolmentRepository.findAll().size();
        // set the field null
        enrolment.setWithInsurance(null);

        // Create the Enrolment, which fails.
        EnrolmentDTO enrolmentDTO = enrolmentMapper.toDto(enrolment);

        restEnrolmentMockMvc.perform(post("/api/enrolments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrolmentDTO)))
            .andExpect(status().isBadRequest());

        List<Enrolment> enrolmentList = enrolmentRepository.findAll();
        assertThat(enrolmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnrolments() throws Exception {
        // Initialize the database
        enrolmentRepository.saveAndFlush(enrolment);

        // Get all the enrolmentList
        restEnrolmentMockMvc.perform(get("/api/enrolments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enrolment.getId().intValue())))
            .andExpect(jsonPath("$.[*].enrolmentDate").value(hasItem(DEFAULT_ENROLMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].regimenRate").value(hasItem(DEFAULT_REGIMEN_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].repeating").value(hasItem(DEFAULT_REPEATING.booleanValue())))
            .andExpect(jsonPath("$.[*].modernLanguage1").value(hasItem(DEFAULT_MODERN_LANGUAGE_1.toString())))
            .andExpect(jsonPath("$.[*].modernLanguage2").value(hasItem(DEFAULT_MODERN_LANGUAGE_2.toString())))
            .andExpect(jsonPath("$.[*].exemption").value(hasItem(DEFAULT_EXEMPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].withInsurance").value(hasItem(DEFAULT_WITH_INSURANCE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getEnrolment() throws Exception {
        // Initialize the database
        enrolmentRepository.saveAndFlush(enrolment);

        // Get the enrolment
        restEnrolmentMockMvc.perform(get("/api/enrolments/{id}", enrolment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enrolment.getId().intValue()))
            .andExpect(jsonPath("$.enrolmentDate").value(DEFAULT_ENROLMENT_DATE.toString()))
            .andExpect(jsonPath("$.regimenRate").value(DEFAULT_REGIMEN_RATE.doubleValue()))
            .andExpect(jsonPath("$.repeating").value(DEFAULT_REPEATING.booleanValue()))
            .andExpect(jsonPath("$.modernLanguage1").value(DEFAULT_MODERN_LANGUAGE_1.toString()))
            .andExpect(jsonPath("$.modernLanguage2").value(DEFAULT_MODERN_LANGUAGE_2.toString()))
            .andExpect(jsonPath("$.exemption").value(DEFAULT_EXEMPTION.booleanValue()))
            .andExpect(jsonPath("$.withInsurance").value(DEFAULT_WITH_INSURANCE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEnrolment() throws Exception {
        // Get the enrolment
        restEnrolmentMockMvc.perform(get("/api/enrolments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnrolment() throws Exception {
        // Initialize the database
        enrolmentRepository.saveAndFlush(enrolment);

        int databaseSizeBeforeUpdate = enrolmentRepository.findAll().size();

        // Update the enrolment
        Enrolment updatedEnrolment = enrolmentRepository.findById(enrolment.getId()).get();
        // Disconnect from session so that the updates on updatedEnrolment are not directly saved in db
        em.detach(updatedEnrolment);
        updatedEnrolment
            .enrolmentDate(UPDATED_ENROLMENT_DATE)
            .regimenRate(UPDATED_REGIMEN_RATE)
            .repeating(UPDATED_REPEATING)
            .modernLanguage1(UPDATED_MODERN_LANGUAGE_1)
            .modernLanguage2(UPDATED_MODERN_LANGUAGE_2)
            .exemption(UPDATED_EXEMPTION)
            .withInsurance(UPDATED_WITH_INSURANCE);
        EnrolmentDTO enrolmentDTO = enrolmentMapper.toDto(updatedEnrolment);

        restEnrolmentMockMvc.perform(put("/api/enrolments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrolmentDTO)))
            .andExpect(status().isOk());

        // Validate the Enrolment in the database
        List<Enrolment> enrolmentList = enrolmentRepository.findAll();
        assertThat(enrolmentList).hasSize(databaseSizeBeforeUpdate);
        Enrolment testEnrolment = enrolmentList.get(enrolmentList.size() - 1);
        assertThat(testEnrolment.getEnrolmentDate()).isEqualTo(UPDATED_ENROLMENT_DATE);
        assertThat(testEnrolment.getRegimenRate()).isEqualTo(UPDATED_REGIMEN_RATE);
        assertThat(testEnrolment.isRepeating()).isEqualTo(UPDATED_REPEATING);
        assertThat(testEnrolment.getModernLanguage1()).isEqualTo(UPDATED_MODERN_LANGUAGE_1);
        assertThat(testEnrolment.getModernLanguage2()).isEqualTo(UPDATED_MODERN_LANGUAGE_2);
        assertThat(testEnrolment.isExemption()).isEqualTo(UPDATED_EXEMPTION);
        assertThat(testEnrolment.isWithInsurance()).isEqualTo(UPDATED_WITH_INSURANCE);

        // Validate the Enrolment in Elasticsearch
        verify(mockEnrolmentSearchRepository, times(1)).save(testEnrolment);
    }

    @Test
    @Transactional
    public void updateNonExistingEnrolment() throws Exception {
        int databaseSizeBeforeUpdate = enrolmentRepository.findAll().size();

        // Create the Enrolment
        EnrolmentDTO enrolmentDTO = enrolmentMapper.toDto(enrolment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnrolmentMockMvc.perform(put("/api/enrolments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrolmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enrolment in the database
        List<Enrolment> enrolmentList = enrolmentRepository.findAll();
        assertThat(enrolmentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Enrolment in Elasticsearch
        verify(mockEnrolmentSearchRepository, times(0)).save(enrolment);
    }

    @Test
    @Transactional
    public void deleteEnrolment() throws Exception {
        // Initialize the database
        enrolmentRepository.saveAndFlush(enrolment);

        int databaseSizeBeforeDelete = enrolmentRepository.findAll().size();

        // Delete the enrolment
        restEnrolmentMockMvc.perform(delete("/api/enrolments/{id}", enrolment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Enrolment> enrolmentList = enrolmentRepository.findAll();
        assertThat(enrolmentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Enrolment in Elasticsearch
        verify(mockEnrolmentSearchRepository, times(1)).deleteById(enrolment.getId());
    }

    @Test
    @Transactional
    public void searchEnrolment() throws Exception {
        // Initialize the database
        enrolmentRepository.saveAndFlush(enrolment);
        when(mockEnrolmentSearchRepository.search(queryStringQuery("id:" + enrolment.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(enrolment), PageRequest.of(0, 1), 1));
        // Search the enrolment
        restEnrolmentMockMvc.perform(get("/api/_search/enrolments?query=id:" + enrolment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enrolment.getId().intValue())))
            .andExpect(jsonPath("$.[*].enrolmentDate").value(hasItem(DEFAULT_ENROLMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].regimenRate").value(hasItem(DEFAULT_REGIMEN_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].repeating").value(hasItem(DEFAULT_REPEATING.booleanValue())))
            .andExpect(jsonPath("$.[*].modernLanguage1").value(hasItem(DEFAULT_MODERN_LANGUAGE_1)))
            .andExpect(jsonPath("$.[*].modernLanguage2").value(hasItem(DEFAULT_MODERN_LANGUAGE_2)))
            .andExpect(jsonPath("$.[*].exemption").value(hasItem(DEFAULT_EXEMPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].withInsurance").value(hasItem(DEFAULT_WITH_INSURANCE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enrolment.class);
        Enrolment enrolment1 = new Enrolment();
        enrolment1.setId(1L);
        Enrolment enrolment2 = new Enrolment();
        enrolment2.setId(enrolment1.getId());
        assertThat(enrolment1).isEqualTo(enrolment2);
        enrolment2.setId(2L);
        assertThat(enrolment1).isNotEqualTo(enrolment2);
        enrolment1.setId(null);
        assertThat(enrolment1).isNotEqualTo(enrolment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnrolmentDTO.class);
        EnrolmentDTO enrolmentDTO1 = new EnrolmentDTO();
        enrolmentDTO1.setId(1L);
        EnrolmentDTO enrolmentDTO2 = new EnrolmentDTO();
        assertThat(enrolmentDTO1).isNotEqualTo(enrolmentDTO2);
        enrolmentDTO2.setId(enrolmentDTO1.getId());
        assertThat(enrolmentDTO1).isEqualTo(enrolmentDTO2);
        enrolmentDTO2.setId(2L);
        assertThat(enrolmentDTO1).isNotEqualTo(enrolmentDTO2);
        enrolmentDTO1.setId(null);
        assertThat(enrolmentDTO1).isNotEqualTo(enrolmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(enrolmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(enrolmentMapper.fromId(null)).isNull();
    }
}
