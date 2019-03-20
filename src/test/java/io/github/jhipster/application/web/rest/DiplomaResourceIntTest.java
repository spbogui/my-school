package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.Diploma;
import io.github.jhipster.application.repository.DiplomaRepository;
import io.github.jhipster.application.repository.search.DiplomaSearchRepository;
import io.github.jhipster.application.service.DiplomaService;
import io.github.jhipster.application.service.dto.DiplomaDTO;
import io.github.jhipster.application.service.mapper.DiplomaMapper;
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
 * Test class for the DiplomaResource REST controller.
 *
 * @see DiplomaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class DiplomaResourceIntTest {

    private static final String DEFAULT_DIPLOMA_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_DIPLOMA_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private DiplomaRepository diplomaRepository;

    @Autowired
    private DiplomaMapper diplomaMapper;

    @Autowired
    private DiplomaService diplomaService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.DiplomaSearchRepositoryMockConfiguration
     */
    @Autowired
    private DiplomaSearchRepository mockDiplomaSearchRepository;

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

    private MockMvc restDiplomaMockMvc;

    private Diploma diploma;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiplomaResource diplomaResource = new DiplomaResource(diplomaService);
        this.restDiplomaMockMvc = MockMvcBuilders.standaloneSetup(diplomaResource)
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
    public static Diploma createEntity(EntityManager em) {
        Diploma diploma = new Diploma()
            .diplomaLabel(DEFAULT_DIPLOMA_LABEL)
            .description(DEFAULT_DESCRIPTION);
        return diploma;
    }

    @Before
    public void initTest() {
        diploma = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiploma() throws Exception {
        int databaseSizeBeforeCreate = diplomaRepository.findAll().size();

        // Create the Diploma
        DiplomaDTO diplomaDTO = diplomaMapper.toDto(diploma);
        restDiplomaMockMvc.perform(post("/api/diplomas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diplomaDTO)))
            .andExpect(status().isCreated());

        // Validate the Diploma in the database
        List<Diploma> diplomaList = diplomaRepository.findAll();
        assertThat(diplomaList).hasSize(databaseSizeBeforeCreate + 1);
        Diploma testDiploma = diplomaList.get(diplomaList.size() - 1);
        assertThat(testDiploma.getDiplomaLabel()).isEqualTo(DEFAULT_DIPLOMA_LABEL);
        assertThat(testDiploma.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Diploma in Elasticsearch
        verify(mockDiplomaSearchRepository, times(1)).save(testDiploma);
    }

    @Test
    @Transactional
    public void createDiplomaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diplomaRepository.findAll().size();

        // Create the Diploma with an existing ID
        diploma.setId(1L);
        DiplomaDTO diplomaDTO = diplomaMapper.toDto(diploma);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiplomaMockMvc.perform(post("/api/diplomas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diplomaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Diploma in the database
        List<Diploma> diplomaList = diplomaRepository.findAll();
        assertThat(diplomaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Diploma in Elasticsearch
        verify(mockDiplomaSearchRepository, times(0)).save(diploma);
    }

    @Test
    @Transactional
    public void checkDiplomaLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = diplomaRepository.findAll().size();
        // set the field null
        diploma.setDiplomaLabel(null);

        // Create the Diploma, which fails.
        DiplomaDTO diplomaDTO = diplomaMapper.toDto(diploma);

        restDiplomaMockMvc.perform(post("/api/diplomas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diplomaDTO)))
            .andExpect(status().isBadRequest());

        List<Diploma> diplomaList = diplomaRepository.findAll();
        assertThat(diplomaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiplomas() throws Exception {
        // Initialize the database
        diplomaRepository.saveAndFlush(diploma);

        // Get all the diplomaList
        restDiplomaMockMvc.perform(get("/api/diplomas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diploma.getId().intValue())))
            .andExpect(jsonPath("$.[*].diplomaLabel").value(hasItem(DEFAULT_DIPLOMA_LABEL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getDiploma() throws Exception {
        // Initialize the database
        diplomaRepository.saveAndFlush(diploma);

        // Get the diploma
        restDiplomaMockMvc.perform(get("/api/diplomas/{id}", diploma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(diploma.getId().intValue()))
            .andExpect(jsonPath("$.diplomaLabel").value(DEFAULT_DIPLOMA_LABEL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDiploma() throws Exception {
        // Get the diploma
        restDiplomaMockMvc.perform(get("/api/diplomas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiploma() throws Exception {
        // Initialize the database
        diplomaRepository.saveAndFlush(diploma);

        int databaseSizeBeforeUpdate = diplomaRepository.findAll().size();

        // Update the diploma
        Diploma updatedDiploma = diplomaRepository.findById(diploma.getId()).get();
        // Disconnect from session so that the updates on updatedDiploma are not directly saved in db
        em.detach(updatedDiploma);
        updatedDiploma
            .diplomaLabel(UPDATED_DIPLOMA_LABEL)
            .description(UPDATED_DESCRIPTION);
        DiplomaDTO diplomaDTO = diplomaMapper.toDto(updatedDiploma);

        restDiplomaMockMvc.perform(put("/api/diplomas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diplomaDTO)))
            .andExpect(status().isOk());

        // Validate the Diploma in the database
        List<Diploma> diplomaList = diplomaRepository.findAll();
        assertThat(diplomaList).hasSize(databaseSizeBeforeUpdate);
        Diploma testDiploma = diplomaList.get(diplomaList.size() - 1);
        assertThat(testDiploma.getDiplomaLabel()).isEqualTo(UPDATED_DIPLOMA_LABEL);
        assertThat(testDiploma.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Diploma in Elasticsearch
        verify(mockDiplomaSearchRepository, times(1)).save(testDiploma);
    }

    @Test
    @Transactional
    public void updateNonExistingDiploma() throws Exception {
        int databaseSizeBeforeUpdate = diplomaRepository.findAll().size();

        // Create the Diploma
        DiplomaDTO diplomaDTO = diplomaMapper.toDto(diploma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiplomaMockMvc.perform(put("/api/diplomas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diplomaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Diploma in the database
        List<Diploma> diplomaList = diplomaRepository.findAll();
        assertThat(diplomaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Diploma in Elasticsearch
        verify(mockDiplomaSearchRepository, times(0)).save(diploma);
    }

    @Test
    @Transactional
    public void deleteDiploma() throws Exception {
        // Initialize the database
        diplomaRepository.saveAndFlush(diploma);

        int databaseSizeBeforeDelete = diplomaRepository.findAll().size();

        // Delete the diploma
        restDiplomaMockMvc.perform(delete("/api/diplomas/{id}", diploma.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Diploma> diplomaList = diplomaRepository.findAll();
        assertThat(diplomaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Diploma in Elasticsearch
        verify(mockDiplomaSearchRepository, times(1)).deleteById(diploma.getId());
    }

    @Test
    @Transactional
    public void searchDiploma() throws Exception {
        // Initialize the database
        diplomaRepository.saveAndFlush(diploma);
        when(mockDiplomaSearchRepository.search(queryStringQuery("id:" + diploma.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(diploma), PageRequest.of(0, 1), 1));
        // Search the diploma
        restDiplomaMockMvc.perform(get("/api/_search/diplomas?query=id:" + diploma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diploma.getId().intValue())))
            .andExpect(jsonPath("$.[*].diplomaLabel").value(hasItem(DEFAULT_DIPLOMA_LABEL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Diploma.class);
        Diploma diploma1 = new Diploma();
        diploma1.setId(1L);
        Diploma diploma2 = new Diploma();
        diploma2.setId(diploma1.getId());
        assertThat(diploma1).isEqualTo(diploma2);
        diploma2.setId(2L);
        assertThat(diploma1).isNotEqualTo(diploma2);
        diploma1.setId(null);
        assertThat(diploma1).isNotEqualTo(diploma2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiplomaDTO.class);
        DiplomaDTO diplomaDTO1 = new DiplomaDTO();
        diplomaDTO1.setId(1L);
        DiplomaDTO diplomaDTO2 = new DiplomaDTO();
        assertThat(diplomaDTO1).isNotEqualTo(diplomaDTO2);
        diplomaDTO2.setId(diplomaDTO1.getId());
        assertThat(diplomaDTO1).isEqualTo(diplomaDTO2);
        diplomaDTO2.setId(2L);
        assertThat(diplomaDTO1).isNotEqualTo(diplomaDTO2);
        diplomaDTO1.setId(null);
        assertThat(diplomaDTO1).isNotEqualTo(diplomaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(diplomaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(diplomaMapper.fromId(null)).isNull();
    }
}
