package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.SchoolSchoolYear;
import io.github.jhipster.application.repository.SchoolSchoolYearRepository;
import io.github.jhipster.application.repository.search.SchoolSchoolYearSearchRepository;
import io.github.jhipster.application.service.SchoolSchoolYearService;
import io.github.jhipster.application.service.dto.SchoolSchoolYearDTO;
import io.github.jhipster.application.service.mapper.SchoolSchoolYearMapper;
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
 * Test class for the SchoolSchoolYearResource REST controller.
 *
 * @see SchoolSchoolYearResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class SchoolSchoolYearResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SchoolSchoolYearRepository schoolSchoolYearRepository;

    @Autowired
    private SchoolSchoolYearMapper schoolSchoolYearMapper;

    @Autowired
    private SchoolSchoolYearService schoolSchoolYearService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.SchoolSchoolYearSearchRepositoryMockConfiguration
     */
    @Autowired
    private SchoolSchoolYearSearchRepository mockSchoolSchoolYearSearchRepository;

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

    private MockMvc restSchoolSchoolYearMockMvc;

    private SchoolSchoolYear schoolSchoolYear;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SchoolSchoolYearResource schoolSchoolYearResource = new SchoolSchoolYearResource(schoolSchoolYearService);
        this.restSchoolSchoolYearMockMvc = MockMvcBuilders.standaloneSetup(schoolSchoolYearResource)
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
    public static SchoolSchoolYear createEntity(EntityManager em) {
        SchoolSchoolYear schoolSchoolYear = new SchoolSchoolYear()
            .description(DEFAULT_DESCRIPTION);
        return schoolSchoolYear;
    }

    @Before
    public void initTest() {
        schoolSchoolYear = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchoolSchoolYear() throws Exception {
        int databaseSizeBeforeCreate = schoolSchoolYearRepository.findAll().size();

        // Create the SchoolSchoolYear
        SchoolSchoolYearDTO schoolSchoolYearDTO = schoolSchoolYearMapper.toDto(schoolSchoolYear);
        restSchoolSchoolYearMockMvc.perform(post("/api/school-school-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolSchoolYearDTO)))
            .andExpect(status().isCreated());

        // Validate the SchoolSchoolYear in the database
        List<SchoolSchoolYear> schoolSchoolYearList = schoolSchoolYearRepository.findAll();
        assertThat(schoolSchoolYearList).hasSize(databaseSizeBeforeCreate + 1);
        SchoolSchoolYear testSchoolSchoolYear = schoolSchoolYearList.get(schoolSchoolYearList.size() - 1);
        assertThat(testSchoolSchoolYear.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the SchoolSchoolYear in Elasticsearch
        verify(mockSchoolSchoolYearSearchRepository, times(1)).save(testSchoolSchoolYear);
    }

    @Test
    @Transactional
    public void createSchoolSchoolYearWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schoolSchoolYearRepository.findAll().size();

        // Create the SchoolSchoolYear with an existing ID
        schoolSchoolYear.setId(1L);
        SchoolSchoolYearDTO schoolSchoolYearDTO = schoolSchoolYearMapper.toDto(schoolSchoolYear);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchoolSchoolYearMockMvc.perform(post("/api/school-school-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolSchoolYearDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SchoolSchoolYear in the database
        List<SchoolSchoolYear> schoolSchoolYearList = schoolSchoolYearRepository.findAll();
        assertThat(schoolSchoolYearList).hasSize(databaseSizeBeforeCreate);

        // Validate the SchoolSchoolYear in Elasticsearch
        verify(mockSchoolSchoolYearSearchRepository, times(0)).save(schoolSchoolYear);
    }

    @Test
    @Transactional
    public void getAllSchoolSchoolYears() throws Exception {
        // Initialize the database
        schoolSchoolYearRepository.saveAndFlush(schoolSchoolYear);

        // Get all the schoolSchoolYearList
        restSchoolSchoolYearMockMvc.perform(get("/api/school-school-years?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schoolSchoolYear.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getSchoolSchoolYear() throws Exception {
        // Initialize the database
        schoolSchoolYearRepository.saveAndFlush(schoolSchoolYear);

        // Get the schoolSchoolYear
        restSchoolSchoolYearMockMvc.perform(get("/api/school-school-years/{id}", schoolSchoolYear.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(schoolSchoolYear.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchoolSchoolYear() throws Exception {
        // Get the schoolSchoolYear
        restSchoolSchoolYearMockMvc.perform(get("/api/school-school-years/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchoolSchoolYear() throws Exception {
        // Initialize the database
        schoolSchoolYearRepository.saveAndFlush(schoolSchoolYear);

        int databaseSizeBeforeUpdate = schoolSchoolYearRepository.findAll().size();

        // Update the schoolSchoolYear
        SchoolSchoolYear updatedSchoolSchoolYear = schoolSchoolYearRepository.findById(schoolSchoolYear.getId()).get();
        // Disconnect from session so that the updates on updatedSchoolSchoolYear are not directly saved in db
        em.detach(updatedSchoolSchoolYear);
        updatedSchoolSchoolYear
            .description(UPDATED_DESCRIPTION);
        SchoolSchoolYearDTO schoolSchoolYearDTO = schoolSchoolYearMapper.toDto(updatedSchoolSchoolYear);

        restSchoolSchoolYearMockMvc.perform(put("/api/school-school-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolSchoolYearDTO)))
            .andExpect(status().isOk());

        // Validate the SchoolSchoolYear in the database
        List<SchoolSchoolYear> schoolSchoolYearList = schoolSchoolYearRepository.findAll();
        assertThat(schoolSchoolYearList).hasSize(databaseSizeBeforeUpdate);
        SchoolSchoolYear testSchoolSchoolYear = schoolSchoolYearList.get(schoolSchoolYearList.size() - 1);
        assertThat(testSchoolSchoolYear.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the SchoolSchoolYear in Elasticsearch
        verify(mockSchoolSchoolYearSearchRepository, times(1)).save(testSchoolSchoolYear);
    }

    @Test
    @Transactional
    public void updateNonExistingSchoolSchoolYear() throws Exception {
        int databaseSizeBeforeUpdate = schoolSchoolYearRepository.findAll().size();

        // Create the SchoolSchoolYear
        SchoolSchoolYearDTO schoolSchoolYearDTO = schoolSchoolYearMapper.toDto(schoolSchoolYear);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchoolSchoolYearMockMvc.perform(put("/api/school-school-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolSchoolYearDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SchoolSchoolYear in the database
        List<SchoolSchoolYear> schoolSchoolYearList = schoolSchoolYearRepository.findAll();
        assertThat(schoolSchoolYearList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SchoolSchoolYear in Elasticsearch
        verify(mockSchoolSchoolYearSearchRepository, times(0)).save(schoolSchoolYear);
    }

    @Test
    @Transactional
    public void deleteSchoolSchoolYear() throws Exception {
        // Initialize the database
        schoolSchoolYearRepository.saveAndFlush(schoolSchoolYear);

        int databaseSizeBeforeDelete = schoolSchoolYearRepository.findAll().size();

        // Delete the schoolSchoolYear
        restSchoolSchoolYearMockMvc.perform(delete("/api/school-school-years/{id}", schoolSchoolYear.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SchoolSchoolYear> schoolSchoolYearList = schoolSchoolYearRepository.findAll();
        assertThat(schoolSchoolYearList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SchoolSchoolYear in Elasticsearch
        verify(mockSchoolSchoolYearSearchRepository, times(1)).deleteById(schoolSchoolYear.getId());
    }

    @Test
    @Transactional
    public void searchSchoolSchoolYear() throws Exception {
        // Initialize the database
        schoolSchoolYearRepository.saveAndFlush(schoolSchoolYear);
        when(mockSchoolSchoolYearSearchRepository.search(queryStringQuery("id:" + schoolSchoolYear.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(schoolSchoolYear), PageRequest.of(0, 1), 1));
        // Search the schoolSchoolYear
        restSchoolSchoolYearMockMvc.perform(get("/api/_search/school-school-years?query=id:" + schoolSchoolYear.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schoolSchoolYear.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchoolSchoolYear.class);
        SchoolSchoolYear schoolSchoolYear1 = new SchoolSchoolYear();
        schoolSchoolYear1.setId(1L);
        SchoolSchoolYear schoolSchoolYear2 = new SchoolSchoolYear();
        schoolSchoolYear2.setId(schoolSchoolYear1.getId());
        assertThat(schoolSchoolYear1).isEqualTo(schoolSchoolYear2);
        schoolSchoolYear2.setId(2L);
        assertThat(schoolSchoolYear1).isNotEqualTo(schoolSchoolYear2);
        schoolSchoolYear1.setId(null);
        assertThat(schoolSchoolYear1).isNotEqualTo(schoolSchoolYear2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchoolSchoolYearDTO.class);
        SchoolSchoolYearDTO schoolSchoolYearDTO1 = new SchoolSchoolYearDTO();
        schoolSchoolYearDTO1.setId(1L);
        SchoolSchoolYearDTO schoolSchoolYearDTO2 = new SchoolSchoolYearDTO();
        assertThat(schoolSchoolYearDTO1).isNotEqualTo(schoolSchoolYearDTO2);
        schoolSchoolYearDTO2.setId(schoolSchoolYearDTO1.getId());
        assertThat(schoolSchoolYearDTO1).isEqualTo(schoolSchoolYearDTO2);
        schoolSchoolYearDTO2.setId(2L);
        assertThat(schoolSchoolYearDTO1).isNotEqualTo(schoolSchoolYearDTO2);
        schoolSchoolYearDTO1.setId(null);
        assertThat(schoolSchoolYearDTO1).isNotEqualTo(schoolSchoolYearDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(schoolSchoolYearMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(schoolSchoolYearMapper.fromId(null)).isNull();
    }
}
