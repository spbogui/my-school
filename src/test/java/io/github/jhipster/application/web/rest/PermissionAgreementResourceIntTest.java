package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.PermissionAgreement;
import io.github.jhipster.application.repository.PermissionAgreementRepository;
import io.github.jhipster.application.repository.search.PermissionAgreementSearchRepository;
import io.github.jhipster.application.service.PermissionAgreementService;
import io.github.jhipster.application.service.dto.PermissionAgreementDTO;
import io.github.jhipster.application.service.mapper.PermissionAgreementMapper;
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
 * Test class for the PermissionAgreementResource REST controller.
 *
 * @see PermissionAgreementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class PermissionAgreementResourceIntTest {

    private static final Boolean DEFAULT_PERMISSION_ALLOWED = false;
    private static final Boolean UPDATED_PERMISSION_ALLOWED = true;

    private static final LocalDate DEFAULT_ALLOWANCE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ALLOWANCE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PERMISSION_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PERMISSION_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PERMISSION_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PERMISSION_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RETURN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RETURN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EFFECTIVE_RETURN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EFFECTIVE_RETURN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MEMO = "AAAAAAAAAA";
    private static final String UPDATED_MEMO = "BBBBBBBBBB";

    @Autowired
    private PermissionAgreementRepository permissionAgreementRepository;

    @Autowired
    private PermissionAgreementMapper permissionAgreementMapper;

    @Autowired
    private PermissionAgreementService permissionAgreementService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.PermissionAgreementSearchRepositoryMockConfiguration
     */
    @Autowired
    private PermissionAgreementSearchRepository mockPermissionAgreementSearchRepository;

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

    private MockMvc restPermissionAgreementMockMvc;

    private PermissionAgreement permissionAgreement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PermissionAgreementResource permissionAgreementResource = new PermissionAgreementResource(permissionAgreementService);
        this.restPermissionAgreementMockMvc = MockMvcBuilders.standaloneSetup(permissionAgreementResource)
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
    public static PermissionAgreement createEntity(EntityManager em) {
        PermissionAgreement permissionAgreement = new PermissionAgreement()
            .permissionAllowed(DEFAULT_PERMISSION_ALLOWED)
            .allowanceDate(DEFAULT_ALLOWANCE_DATE)
            .permissionStartDate(DEFAULT_PERMISSION_START_DATE)
            .permissionEndDate(DEFAULT_PERMISSION_END_DATE)
            .returnDate(DEFAULT_RETURN_DATE)
            .effectiveReturnDate(DEFAULT_EFFECTIVE_RETURN_DATE)
            .memo(DEFAULT_MEMO);
        return permissionAgreement;
    }

    @Before
    public void initTest() {
        permissionAgreement = createEntity(em);
    }

    @Test
    @Transactional
    public void createPermissionAgreement() throws Exception {
        int databaseSizeBeforeCreate = permissionAgreementRepository.findAll().size();

        // Create the PermissionAgreement
        PermissionAgreementDTO permissionAgreementDTO = permissionAgreementMapper.toDto(permissionAgreement);
        restPermissionAgreementMockMvc.perform(post("/api/permission-agreements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(permissionAgreementDTO)))
            .andExpect(status().isCreated());

        // Validate the PermissionAgreement in the database
        List<PermissionAgreement> permissionAgreementList = permissionAgreementRepository.findAll();
        assertThat(permissionAgreementList).hasSize(databaseSizeBeforeCreate + 1);
        PermissionAgreement testPermissionAgreement = permissionAgreementList.get(permissionAgreementList.size() - 1);
        assertThat(testPermissionAgreement.isPermissionAllowed()).isEqualTo(DEFAULT_PERMISSION_ALLOWED);
        assertThat(testPermissionAgreement.getAllowanceDate()).isEqualTo(DEFAULT_ALLOWANCE_DATE);
        assertThat(testPermissionAgreement.getPermissionStartDate()).isEqualTo(DEFAULT_PERMISSION_START_DATE);
        assertThat(testPermissionAgreement.getPermissionEndDate()).isEqualTo(DEFAULT_PERMISSION_END_DATE);
        assertThat(testPermissionAgreement.getReturnDate()).isEqualTo(DEFAULT_RETURN_DATE);
        assertThat(testPermissionAgreement.getEffectiveReturnDate()).isEqualTo(DEFAULT_EFFECTIVE_RETURN_DATE);
        assertThat(testPermissionAgreement.getMemo()).isEqualTo(DEFAULT_MEMO);

        // Validate the PermissionAgreement in Elasticsearch
        verify(mockPermissionAgreementSearchRepository, times(1)).save(testPermissionAgreement);
    }

    @Test
    @Transactional
    public void createPermissionAgreementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = permissionAgreementRepository.findAll().size();

        // Create the PermissionAgreement with an existing ID
        permissionAgreement.setId(1L);
        PermissionAgreementDTO permissionAgreementDTO = permissionAgreementMapper.toDto(permissionAgreement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPermissionAgreementMockMvc.perform(post("/api/permission-agreements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(permissionAgreementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PermissionAgreement in the database
        List<PermissionAgreement> permissionAgreementList = permissionAgreementRepository.findAll();
        assertThat(permissionAgreementList).hasSize(databaseSizeBeforeCreate);

        // Validate the PermissionAgreement in Elasticsearch
        verify(mockPermissionAgreementSearchRepository, times(0)).save(permissionAgreement);
    }

    @Test
    @Transactional
    public void checkPermissionAllowedIsRequired() throws Exception {
        int databaseSizeBeforeTest = permissionAgreementRepository.findAll().size();
        // set the field null
        permissionAgreement.setPermissionAllowed(null);

        // Create the PermissionAgreement, which fails.
        PermissionAgreementDTO permissionAgreementDTO = permissionAgreementMapper.toDto(permissionAgreement);

        restPermissionAgreementMockMvc.perform(post("/api/permission-agreements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(permissionAgreementDTO)))
            .andExpect(status().isBadRequest());

        List<PermissionAgreement> permissionAgreementList = permissionAgreementRepository.findAll();
        assertThat(permissionAgreementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPermissionAgreements() throws Exception {
        // Initialize the database
        permissionAgreementRepository.saveAndFlush(permissionAgreement);

        // Get all the permissionAgreementList
        restPermissionAgreementMockMvc.perform(get("/api/permission-agreements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permissionAgreement.getId().intValue())))
            .andExpect(jsonPath("$.[*].permissionAllowed").value(hasItem(DEFAULT_PERMISSION_ALLOWED.booleanValue())))
            .andExpect(jsonPath("$.[*].allowanceDate").value(hasItem(DEFAULT_ALLOWANCE_DATE.toString())))
            .andExpect(jsonPath("$.[*].permissionStartDate").value(hasItem(DEFAULT_PERMISSION_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].permissionEndDate").value(hasItem(DEFAULT_PERMISSION_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].returnDate").value(hasItem(DEFAULT_RETURN_DATE.toString())))
            .andExpect(jsonPath("$.[*].effectiveReturnDate").value(hasItem(DEFAULT_EFFECTIVE_RETURN_DATE.toString())))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO.toString())));
    }
    
    @Test
    @Transactional
    public void getPermissionAgreement() throws Exception {
        // Initialize the database
        permissionAgreementRepository.saveAndFlush(permissionAgreement);

        // Get the permissionAgreement
        restPermissionAgreementMockMvc.perform(get("/api/permission-agreements/{id}", permissionAgreement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(permissionAgreement.getId().intValue()))
            .andExpect(jsonPath("$.permissionAllowed").value(DEFAULT_PERMISSION_ALLOWED.booleanValue()))
            .andExpect(jsonPath("$.allowanceDate").value(DEFAULT_ALLOWANCE_DATE.toString()))
            .andExpect(jsonPath("$.permissionStartDate").value(DEFAULT_PERMISSION_START_DATE.toString()))
            .andExpect(jsonPath("$.permissionEndDate").value(DEFAULT_PERMISSION_END_DATE.toString()))
            .andExpect(jsonPath("$.returnDate").value(DEFAULT_RETURN_DATE.toString()))
            .andExpect(jsonPath("$.effectiveReturnDate").value(DEFAULT_EFFECTIVE_RETURN_DATE.toString()))
            .andExpect(jsonPath("$.memo").value(DEFAULT_MEMO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPermissionAgreement() throws Exception {
        // Get the permissionAgreement
        restPermissionAgreementMockMvc.perform(get("/api/permission-agreements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePermissionAgreement() throws Exception {
        // Initialize the database
        permissionAgreementRepository.saveAndFlush(permissionAgreement);

        int databaseSizeBeforeUpdate = permissionAgreementRepository.findAll().size();

        // Update the permissionAgreement
        PermissionAgreement updatedPermissionAgreement = permissionAgreementRepository.findById(permissionAgreement.getId()).get();
        // Disconnect from session so that the updates on updatedPermissionAgreement are not directly saved in db
        em.detach(updatedPermissionAgreement);
        updatedPermissionAgreement
            .permissionAllowed(UPDATED_PERMISSION_ALLOWED)
            .allowanceDate(UPDATED_ALLOWANCE_DATE)
            .permissionStartDate(UPDATED_PERMISSION_START_DATE)
            .permissionEndDate(UPDATED_PERMISSION_END_DATE)
            .returnDate(UPDATED_RETURN_DATE)
            .effectiveReturnDate(UPDATED_EFFECTIVE_RETURN_DATE)
            .memo(UPDATED_MEMO);
        PermissionAgreementDTO permissionAgreementDTO = permissionAgreementMapper.toDto(updatedPermissionAgreement);

        restPermissionAgreementMockMvc.perform(put("/api/permission-agreements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(permissionAgreementDTO)))
            .andExpect(status().isOk());

        // Validate the PermissionAgreement in the database
        List<PermissionAgreement> permissionAgreementList = permissionAgreementRepository.findAll();
        assertThat(permissionAgreementList).hasSize(databaseSizeBeforeUpdate);
        PermissionAgreement testPermissionAgreement = permissionAgreementList.get(permissionAgreementList.size() - 1);
        assertThat(testPermissionAgreement.isPermissionAllowed()).isEqualTo(UPDATED_PERMISSION_ALLOWED);
        assertThat(testPermissionAgreement.getAllowanceDate()).isEqualTo(UPDATED_ALLOWANCE_DATE);
        assertThat(testPermissionAgreement.getPermissionStartDate()).isEqualTo(UPDATED_PERMISSION_START_DATE);
        assertThat(testPermissionAgreement.getPermissionEndDate()).isEqualTo(UPDATED_PERMISSION_END_DATE);
        assertThat(testPermissionAgreement.getReturnDate()).isEqualTo(UPDATED_RETURN_DATE);
        assertThat(testPermissionAgreement.getEffectiveReturnDate()).isEqualTo(UPDATED_EFFECTIVE_RETURN_DATE);
        assertThat(testPermissionAgreement.getMemo()).isEqualTo(UPDATED_MEMO);

        // Validate the PermissionAgreement in Elasticsearch
        verify(mockPermissionAgreementSearchRepository, times(1)).save(testPermissionAgreement);
    }

    @Test
    @Transactional
    public void updateNonExistingPermissionAgreement() throws Exception {
        int databaseSizeBeforeUpdate = permissionAgreementRepository.findAll().size();

        // Create the PermissionAgreement
        PermissionAgreementDTO permissionAgreementDTO = permissionAgreementMapper.toDto(permissionAgreement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPermissionAgreementMockMvc.perform(put("/api/permission-agreements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(permissionAgreementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PermissionAgreement in the database
        List<PermissionAgreement> permissionAgreementList = permissionAgreementRepository.findAll();
        assertThat(permissionAgreementList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PermissionAgreement in Elasticsearch
        verify(mockPermissionAgreementSearchRepository, times(0)).save(permissionAgreement);
    }

    @Test
    @Transactional
    public void deletePermissionAgreement() throws Exception {
        // Initialize the database
        permissionAgreementRepository.saveAndFlush(permissionAgreement);

        int databaseSizeBeforeDelete = permissionAgreementRepository.findAll().size();

        // Delete the permissionAgreement
        restPermissionAgreementMockMvc.perform(delete("/api/permission-agreements/{id}", permissionAgreement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PermissionAgreement> permissionAgreementList = permissionAgreementRepository.findAll();
        assertThat(permissionAgreementList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PermissionAgreement in Elasticsearch
        verify(mockPermissionAgreementSearchRepository, times(1)).deleteById(permissionAgreement.getId());
    }

    @Test
    @Transactional
    public void searchPermissionAgreement() throws Exception {
        // Initialize the database
        permissionAgreementRepository.saveAndFlush(permissionAgreement);
        when(mockPermissionAgreementSearchRepository.search(queryStringQuery("id:" + permissionAgreement.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(permissionAgreement), PageRequest.of(0, 1), 1));
        // Search the permissionAgreement
        restPermissionAgreementMockMvc.perform(get("/api/_search/permission-agreements?query=id:" + permissionAgreement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permissionAgreement.getId().intValue())))
            .andExpect(jsonPath("$.[*].permissionAllowed").value(hasItem(DEFAULT_PERMISSION_ALLOWED.booleanValue())))
            .andExpect(jsonPath("$.[*].allowanceDate").value(hasItem(DEFAULT_ALLOWANCE_DATE.toString())))
            .andExpect(jsonPath("$.[*].permissionStartDate").value(hasItem(DEFAULT_PERMISSION_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].permissionEndDate").value(hasItem(DEFAULT_PERMISSION_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].returnDate").value(hasItem(DEFAULT_RETURN_DATE.toString())))
            .andExpect(jsonPath("$.[*].effectiveReturnDate").value(hasItem(DEFAULT_EFFECTIVE_RETURN_DATE.toString())))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PermissionAgreement.class);
        PermissionAgreement permissionAgreement1 = new PermissionAgreement();
        permissionAgreement1.setId(1L);
        PermissionAgreement permissionAgreement2 = new PermissionAgreement();
        permissionAgreement2.setId(permissionAgreement1.getId());
        assertThat(permissionAgreement1).isEqualTo(permissionAgreement2);
        permissionAgreement2.setId(2L);
        assertThat(permissionAgreement1).isNotEqualTo(permissionAgreement2);
        permissionAgreement1.setId(null);
        assertThat(permissionAgreement1).isNotEqualTo(permissionAgreement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PermissionAgreementDTO.class);
        PermissionAgreementDTO permissionAgreementDTO1 = new PermissionAgreementDTO();
        permissionAgreementDTO1.setId(1L);
        PermissionAgreementDTO permissionAgreementDTO2 = new PermissionAgreementDTO();
        assertThat(permissionAgreementDTO1).isNotEqualTo(permissionAgreementDTO2);
        permissionAgreementDTO2.setId(permissionAgreementDTO1.getId());
        assertThat(permissionAgreementDTO1).isEqualTo(permissionAgreementDTO2);
        permissionAgreementDTO2.setId(2L);
        assertThat(permissionAgreementDTO1).isNotEqualTo(permissionAgreementDTO2);
        permissionAgreementDTO1.setId(null);
        assertThat(permissionAgreementDTO1).isNotEqualTo(permissionAgreementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(permissionAgreementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(permissionAgreementMapper.fromId(null)).isNull();
    }
}
