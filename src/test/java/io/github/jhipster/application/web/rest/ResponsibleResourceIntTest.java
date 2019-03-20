package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.Responsible;
import io.github.jhipster.application.repository.ResponsibleRepository;
import io.github.jhipster.application.repository.search.ResponsibleSearchRepository;
import io.github.jhipster.application.service.ResponsibleService;
import io.github.jhipster.application.service.dto.ResponsibleDTO;
import io.github.jhipster.application.service.mapper.ResponsibleMapper;
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
 * Test class for the ResponsibleResource REST controller.
 *
 * @see ResponsibleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class ResponsibleResourceIntTest {

    private static final String DEFAULT_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION = "BBBBBBBBBB";

    @Autowired
    private ResponsibleRepository responsibleRepository;

    @Autowired
    private ResponsibleMapper responsibleMapper;

    @Autowired
    private ResponsibleService responsibleService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.ResponsibleSearchRepositoryMockConfiguration
     */
    @Autowired
    private ResponsibleSearchRepository mockResponsibleSearchRepository;

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

    private MockMvc restResponsibleMockMvc;

    private Responsible responsible;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResponsibleResource responsibleResource = new ResponsibleResource(responsibleService);
        this.restResponsibleMockMvc = MockMvcBuilders.standaloneSetup(responsibleResource)
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
    public static Responsible createEntity(EntityManager em) {
        Responsible responsible = new Responsible()
            .profession(DEFAULT_PROFESSION);
        return responsible;
    }

    @Before
    public void initTest() {
        responsible = createEntity(em);
    }

    @Test
    @Transactional
    public void createResponsible() throws Exception {
        int databaseSizeBeforeCreate = responsibleRepository.findAll().size();

        // Create the Responsible
        ResponsibleDTO responsibleDTO = responsibleMapper.toDto(responsible);
        restResponsibleMockMvc.perform(post("/api/responsibles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsibleDTO)))
            .andExpect(status().isCreated());

        // Validate the Responsible in the database
        List<Responsible> responsibleList = responsibleRepository.findAll();
        assertThat(responsibleList).hasSize(databaseSizeBeforeCreate + 1);
        Responsible testResponsible = responsibleList.get(responsibleList.size() - 1);
        assertThat(testResponsible.getProfession()).isEqualTo(DEFAULT_PROFESSION);

        // Validate the Responsible in Elasticsearch
        verify(mockResponsibleSearchRepository, times(1)).save(testResponsible);
    }

    @Test
    @Transactional
    public void createResponsibleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = responsibleRepository.findAll().size();

        // Create the Responsible with an existing ID
        responsible.setId(1L);
        ResponsibleDTO responsibleDTO = responsibleMapper.toDto(responsible);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsibleMockMvc.perform(post("/api/responsibles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsibleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Responsible in the database
        List<Responsible> responsibleList = responsibleRepository.findAll();
        assertThat(responsibleList).hasSize(databaseSizeBeforeCreate);

        // Validate the Responsible in Elasticsearch
        verify(mockResponsibleSearchRepository, times(0)).save(responsible);
    }

    @Test
    @Transactional
    public void getAllResponsibles() throws Exception {
        // Initialize the database
        responsibleRepository.saveAndFlush(responsible);

        // Get all the responsibleList
        restResponsibleMockMvc.perform(get("/api/responsibles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsible.getId().intValue())))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION.toString())));
    }
    
    @Test
    @Transactional
    public void getResponsible() throws Exception {
        // Initialize the database
        responsibleRepository.saveAndFlush(responsible);

        // Get the responsible
        restResponsibleMockMvc.perform(get("/api/responsibles/{id}", responsible.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(responsible.getId().intValue()))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResponsible() throws Exception {
        // Get the responsible
        restResponsibleMockMvc.perform(get("/api/responsibles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResponsible() throws Exception {
        // Initialize the database
        responsibleRepository.saveAndFlush(responsible);

        int databaseSizeBeforeUpdate = responsibleRepository.findAll().size();

        // Update the responsible
        Responsible updatedResponsible = responsibleRepository.findById(responsible.getId()).get();
        // Disconnect from session so that the updates on updatedResponsible are not directly saved in db
        em.detach(updatedResponsible);
        updatedResponsible
            .profession(UPDATED_PROFESSION);
        ResponsibleDTO responsibleDTO = responsibleMapper.toDto(updatedResponsible);

        restResponsibleMockMvc.perform(put("/api/responsibles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsibleDTO)))
            .andExpect(status().isOk());

        // Validate the Responsible in the database
        List<Responsible> responsibleList = responsibleRepository.findAll();
        assertThat(responsibleList).hasSize(databaseSizeBeforeUpdate);
        Responsible testResponsible = responsibleList.get(responsibleList.size() - 1);
        assertThat(testResponsible.getProfession()).isEqualTo(UPDATED_PROFESSION);

        // Validate the Responsible in Elasticsearch
        verify(mockResponsibleSearchRepository, times(1)).save(testResponsible);
    }

    @Test
    @Transactional
    public void updateNonExistingResponsible() throws Exception {
        int databaseSizeBeforeUpdate = responsibleRepository.findAll().size();

        // Create the Responsible
        ResponsibleDTO responsibleDTO = responsibleMapper.toDto(responsible);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsibleMockMvc.perform(put("/api/responsibles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsibleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Responsible in the database
        List<Responsible> responsibleList = responsibleRepository.findAll();
        assertThat(responsibleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Responsible in Elasticsearch
        verify(mockResponsibleSearchRepository, times(0)).save(responsible);
    }

    @Test
    @Transactional
    public void deleteResponsible() throws Exception {
        // Initialize the database
        responsibleRepository.saveAndFlush(responsible);

        int databaseSizeBeforeDelete = responsibleRepository.findAll().size();

        // Delete the responsible
        restResponsibleMockMvc.perform(delete("/api/responsibles/{id}", responsible.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Responsible> responsibleList = responsibleRepository.findAll();
        assertThat(responsibleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Responsible in Elasticsearch
        verify(mockResponsibleSearchRepository, times(1)).deleteById(responsible.getId());
    }

    @Test
    @Transactional
    public void searchResponsible() throws Exception {
        // Initialize the database
        responsibleRepository.saveAndFlush(responsible);
        when(mockResponsibleSearchRepository.search(queryStringQuery("id:" + responsible.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(responsible), PageRequest.of(0, 1), 1));
        // Search the responsible
        restResponsibleMockMvc.perform(get("/api/_search/responsibles?query=id:" + responsible.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsible.getId().intValue())))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Responsible.class);
        Responsible responsible1 = new Responsible();
        responsible1.setId(1L);
        Responsible responsible2 = new Responsible();
        responsible2.setId(responsible1.getId());
        assertThat(responsible1).isEqualTo(responsible2);
        responsible2.setId(2L);
        assertThat(responsible1).isNotEqualTo(responsible2);
        responsible1.setId(null);
        assertThat(responsible1).isNotEqualTo(responsible2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponsibleDTO.class);
        ResponsibleDTO responsibleDTO1 = new ResponsibleDTO();
        responsibleDTO1.setId(1L);
        ResponsibleDTO responsibleDTO2 = new ResponsibleDTO();
        assertThat(responsibleDTO1).isNotEqualTo(responsibleDTO2);
        responsibleDTO2.setId(responsibleDTO1.getId());
        assertThat(responsibleDTO1).isEqualTo(responsibleDTO2);
        responsibleDTO2.setId(2L);
        assertThat(responsibleDTO1).isNotEqualTo(responsibleDTO2);
        responsibleDTO1.setId(null);
        assertThat(responsibleDTO1).isNotEqualTo(responsibleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(responsibleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(responsibleMapper.fromId(null)).isNull();
    }
}
