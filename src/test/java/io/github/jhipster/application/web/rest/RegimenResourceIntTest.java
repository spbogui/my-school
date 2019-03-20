package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.Regimen;
import io.github.jhipster.application.repository.RegimenRepository;
import io.github.jhipster.application.repository.search.RegimenSearchRepository;
import io.github.jhipster.application.service.RegimenService;
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
 * Test class for the RegimenResource REST controller.
 *
 * @see RegimenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class RegimenResourceIntTest {

    private static final String DEFAULT_REGIMEN_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_REGIMEN_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RegimenRepository regimenRepository;

    @Autowired
    private RegimenService regimenService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.RegimenSearchRepositoryMockConfiguration
     */
    @Autowired
    private RegimenSearchRepository mockRegimenSearchRepository;

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

    private MockMvc restRegimenMockMvc;

    private Regimen regimen;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegimenResource regimenResource = new RegimenResource(regimenService);
        this.restRegimenMockMvc = MockMvcBuilders.standaloneSetup(regimenResource)
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
    public static Regimen createEntity(EntityManager em) {
        Regimen regimen = new Regimen()
            .regimenLabel(DEFAULT_REGIMEN_LABEL)
            .description(DEFAULT_DESCRIPTION);
        return regimen;
    }

    @Before
    public void initTest() {
        regimen = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegimen() throws Exception {
        int databaseSizeBeforeCreate = regimenRepository.findAll().size();

        // Create the Regimen
        restRegimenMockMvc.perform(post("/api/regimen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimen)))
            .andExpect(status().isCreated());

        // Validate the Regimen in the database
        List<Regimen> regimenList = regimenRepository.findAll();
        assertThat(regimenList).hasSize(databaseSizeBeforeCreate + 1);
        Regimen testRegimen = regimenList.get(regimenList.size() - 1);
        assertThat(testRegimen.getRegimenLabel()).isEqualTo(DEFAULT_REGIMEN_LABEL);
        assertThat(testRegimen.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Regimen in Elasticsearch
        verify(mockRegimenSearchRepository, times(1)).save(testRegimen);
    }

    @Test
    @Transactional
    public void createRegimenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regimenRepository.findAll().size();

        // Create the Regimen with an existing ID
        regimen.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegimenMockMvc.perform(post("/api/regimen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimen)))
            .andExpect(status().isBadRequest());

        // Validate the Regimen in the database
        List<Regimen> regimenList = regimenRepository.findAll();
        assertThat(regimenList).hasSize(databaseSizeBeforeCreate);

        // Validate the Regimen in Elasticsearch
        verify(mockRegimenSearchRepository, times(0)).save(regimen);
    }

    @Test
    @Transactional
    public void checkRegimenLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = regimenRepository.findAll().size();
        // set the field null
        regimen.setRegimenLabel(null);

        // Create the Regimen, which fails.

        restRegimenMockMvc.perform(post("/api/regimen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimen)))
            .andExpect(status().isBadRequest());

        List<Regimen> regimenList = regimenRepository.findAll();
        assertThat(regimenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegimen() throws Exception {
        // Initialize the database
        regimenRepository.saveAndFlush(regimen);

        // Get all the regimenList
        restRegimenMockMvc.perform(get("/api/regimen?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regimen.getId().intValue())))
            .andExpect(jsonPath("$.[*].regimenLabel").value(hasItem(DEFAULT_REGIMEN_LABEL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getRegimen() throws Exception {
        // Initialize the database
        regimenRepository.saveAndFlush(regimen);

        // Get the regimen
        restRegimenMockMvc.perform(get("/api/regimen/{id}", regimen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(regimen.getId().intValue()))
            .andExpect(jsonPath("$.regimenLabel").value(DEFAULT_REGIMEN_LABEL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRegimen() throws Exception {
        // Get the regimen
        restRegimenMockMvc.perform(get("/api/regimen/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegimen() throws Exception {
        // Initialize the database
        regimenService.save(regimen);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockRegimenSearchRepository);

        int databaseSizeBeforeUpdate = regimenRepository.findAll().size();

        // Update the regimen
        Regimen updatedRegimen = regimenRepository.findById(regimen.getId()).get();
        // Disconnect from session so that the updates on updatedRegimen are not directly saved in db
        em.detach(updatedRegimen);
        updatedRegimen
            .regimenLabel(UPDATED_REGIMEN_LABEL)
            .description(UPDATED_DESCRIPTION);

        restRegimenMockMvc.perform(put("/api/regimen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegimen)))
            .andExpect(status().isOk());

        // Validate the Regimen in the database
        List<Regimen> regimenList = regimenRepository.findAll();
        assertThat(regimenList).hasSize(databaseSizeBeforeUpdate);
        Regimen testRegimen = regimenList.get(regimenList.size() - 1);
        assertThat(testRegimen.getRegimenLabel()).isEqualTo(UPDATED_REGIMEN_LABEL);
        assertThat(testRegimen.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Regimen in Elasticsearch
        verify(mockRegimenSearchRepository, times(1)).save(testRegimen);
    }

    @Test
    @Transactional
    public void updateNonExistingRegimen() throws Exception {
        int databaseSizeBeforeUpdate = regimenRepository.findAll().size();

        // Create the Regimen

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegimenMockMvc.perform(put("/api/regimen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimen)))
            .andExpect(status().isBadRequest());

        // Validate the Regimen in the database
        List<Regimen> regimenList = regimenRepository.findAll();
        assertThat(regimenList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Regimen in Elasticsearch
        verify(mockRegimenSearchRepository, times(0)).save(regimen);
    }

    @Test
    @Transactional
    public void deleteRegimen() throws Exception {
        // Initialize the database
        regimenService.save(regimen);

        int databaseSizeBeforeDelete = regimenRepository.findAll().size();

        // Delete the regimen
        restRegimenMockMvc.perform(delete("/api/regimen/{id}", regimen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Regimen> regimenList = regimenRepository.findAll();
        assertThat(regimenList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Regimen in Elasticsearch
        verify(mockRegimenSearchRepository, times(1)).deleteById(regimen.getId());
    }

    @Test
    @Transactional
    public void searchRegimen() throws Exception {
        // Initialize the database
        regimenService.save(regimen);
        when(mockRegimenSearchRepository.search(queryStringQuery("id:" + regimen.getId())))
            .thenReturn(Collections.singletonList(regimen));
        // Search the regimen
        restRegimenMockMvc.perform(get("/api/_search/regimen?query=id:" + regimen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regimen.getId().intValue())))
            .andExpect(jsonPath("$.[*].regimenLabel").value(hasItem(DEFAULT_REGIMEN_LABEL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Regimen.class);
        Regimen regimen1 = new Regimen();
        regimen1.setId(1L);
        Regimen regimen2 = new Regimen();
        regimen2.setId(regimen1.getId());
        assertThat(regimen1).isEqualTo(regimen2);
        regimen2.setId(2L);
        assertThat(regimen1).isNotEqualTo(regimen2);
        regimen1.setId(null);
        assertThat(regimen1).isNotEqualTo(regimen2);
    }
}
