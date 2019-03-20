package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.TeacherSubjectSchoolYear;
import io.github.jhipster.application.repository.TeacherSubjectSchoolYearRepository;
import io.github.jhipster.application.repository.search.TeacherSubjectSchoolYearSearchRepository;
import io.github.jhipster.application.service.TeacherSubjectSchoolYearService;
import io.github.jhipster.application.service.dto.TeacherSubjectSchoolYearDTO;
import io.github.jhipster.application.service.mapper.TeacherSubjectSchoolYearMapper;
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
 * Test class for the TeacherSubjectSchoolYearResource REST controller.
 *
 * @see TeacherSubjectSchoolYearResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class TeacherSubjectSchoolYearResourceIntTest {

    private static final Boolean DEFAULT_IS_PRINCIPAL = false;
    private static final Boolean UPDATED_IS_PRINCIPAL = true;

    @Autowired
    private TeacherSubjectSchoolYearRepository teacherSubjectSchoolYearRepository;

    @Autowired
    private TeacherSubjectSchoolYearMapper teacherSubjectSchoolYearMapper;

    @Autowired
    private TeacherSubjectSchoolYearService teacherSubjectSchoolYearService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.TeacherSubjectSchoolYearSearchRepositoryMockConfiguration
     */
    @Autowired
    private TeacherSubjectSchoolYearSearchRepository mockTeacherSubjectSchoolYearSearchRepository;

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

    private MockMvc restTeacherSubjectSchoolYearMockMvc;

    private TeacherSubjectSchoolYear teacherSubjectSchoolYear;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TeacherSubjectSchoolYearResource teacherSubjectSchoolYearResource = new TeacherSubjectSchoolYearResource(teacherSubjectSchoolYearService);
        this.restTeacherSubjectSchoolYearMockMvc = MockMvcBuilders.standaloneSetup(teacherSubjectSchoolYearResource)
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
    public static TeacherSubjectSchoolYear createEntity(EntityManager em) {
        TeacherSubjectSchoolYear teacherSubjectSchoolYear = new TeacherSubjectSchoolYear()
            .isPrincipal(DEFAULT_IS_PRINCIPAL);
        return teacherSubjectSchoolYear;
    }

    @Before
    public void initTest() {
        teacherSubjectSchoolYear = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeacherSubjectSchoolYear() throws Exception {
        int databaseSizeBeforeCreate = teacherSubjectSchoolYearRepository.findAll().size();

        // Create the TeacherSubjectSchoolYear
        TeacherSubjectSchoolYearDTO teacherSubjectSchoolYearDTO = teacherSubjectSchoolYearMapper.toDto(teacherSubjectSchoolYear);
        restTeacherSubjectSchoolYearMockMvc.perform(post("/api/teacher-subject-school-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherSubjectSchoolYearDTO)))
            .andExpect(status().isCreated());

        // Validate the TeacherSubjectSchoolYear in the database
        List<TeacherSubjectSchoolYear> teacherSubjectSchoolYearList = teacherSubjectSchoolYearRepository.findAll();
        assertThat(teacherSubjectSchoolYearList).hasSize(databaseSizeBeforeCreate + 1);
        TeacherSubjectSchoolYear testTeacherSubjectSchoolYear = teacherSubjectSchoolYearList.get(teacherSubjectSchoolYearList.size() - 1);
        assertThat(testTeacherSubjectSchoolYear.isIsPrincipal()).isEqualTo(DEFAULT_IS_PRINCIPAL);

        // Validate the TeacherSubjectSchoolYear in Elasticsearch
        verify(mockTeacherSubjectSchoolYearSearchRepository, times(1)).save(testTeacherSubjectSchoolYear);
    }

    @Test
    @Transactional
    public void createTeacherSubjectSchoolYearWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teacherSubjectSchoolYearRepository.findAll().size();

        // Create the TeacherSubjectSchoolYear with an existing ID
        teacherSubjectSchoolYear.setId(1L);
        TeacherSubjectSchoolYearDTO teacherSubjectSchoolYearDTO = teacherSubjectSchoolYearMapper.toDto(teacherSubjectSchoolYear);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeacherSubjectSchoolYearMockMvc.perform(post("/api/teacher-subject-school-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherSubjectSchoolYearDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TeacherSubjectSchoolYear in the database
        List<TeacherSubjectSchoolYear> teacherSubjectSchoolYearList = teacherSubjectSchoolYearRepository.findAll();
        assertThat(teacherSubjectSchoolYearList).hasSize(databaseSizeBeforeCreate);

        // Validate the TeacherSubjectSchoolYear in Elasticsearch
        verify(mockTeacherSubjectSchoolYearSearchRepository, times(0)).save(teacherSubjectSchoolYear);
    }

    @Test
    @Transactional
    public void getAllTeacherSubjectSchoolYears() throws Exception {
        // Initialize the database
        teacherSubjectSchoolYearRepository.saveAndFlush(teacherSubjectSchoolYear);

        // Get all the teacherSubjectSchoolYearList
        restTeacherSubjectSchoolYearMockMvc.perform(get("/api/teacher-subject-school-years?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teacherSubjectSchoolYear.getId().intValue())))
            .andExpect(jsonPath("$.[*].isPrincipal").value(hasItem(DEFAULT_IS_PRINCIPAL.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTeacherSubjectSchoolYear() throws Exception {
        // Initialize the database
        teacherSubjectSchoolYearRepository.saveAndFlush(teacherSubjectSchoolYear);

        // Get the teacherSubjectSchoolYear
        restTeacherSubjectSchoolYearMockMvc.perform(get("/api/teacher-subject-school-years/{id}", teacherSubjectSchoolYear.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(teacherSubjectSchoolYear.getId().intValue()))
            .andExpect(jsonPath("$.isPrincipal").value(DEFAULT_IS_PRINCIPAL.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTeacherSubjectSchoolYear() throws Exception {
        // Get the teacherSubjectSchoolYear
        restTeacherSubjectSchoolYearMockMvc.perform(get("/api/teacher-subject-school-years/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeacherSubjectSchoolYear() throws Exception {
        // Initialize the database
        teacherSubjectSchoolYearRepository.saveAndFlush(teacherSubjectSchoolYear);

        int databaseSizeBeforeUpdate = teacherSubjectSchoolYearRepository.findAll().size();

        // Update the teacherSubjectSchoolYear
        TeacherSubjectSchoolYear updatedTeacherSubjectSchoolYear = teacherSubjectSchoolYearRepository.findById(teacherSubjectSchoolYear.getId()).get();
        // Disconnect from session so that the updates on updatedTeacherSubjectSchoolYear are not directly saved in db
        em.detach(updatedTeacherSubjectSchoolYear);
        updatedTeacherSubjectSchoolYear
            .isPrincipal(UPDATED_IS_PRINCIPAL);
        TeacherSubjectSchoolYearDTO teacherSubjectSchoolYearDTO = teacherSubjectSchoolYearMapper.toDto(updatedTeacherSubjectSchoolYear);

        restTeacherSubjectSchoolYearMockMvc.perform(put("/api/teacher-subject-school-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherSubjectSchoolYearDTO)))
            .andExpect(status().isOk());

        // Validate the TeacherSubjectSchoolYear in the database
        List<TeacherSubjectSchoolYear> teacherSubjectSchoolYearList = teacherSubjectSchoolYearRepository.findAll();
        assertThat(teacherSubjectSchoolYearList).hasSize(databaseSizeBeforeUpdate);
        TeacherSubjectSchoolYear testTeacherSubjectSchoolYear = teacherSubjectSchoolYearList.get(teacherSubjectSchoolYearList.size() - 1);
        assertThat(testTeacherSubjectSchoolYear.isIsPrincipal()).isEqualTo(UPDATED_IS_PRINCIPAL);

        // Validate the TeacherSubjectSchoolYear in Elasticsearch
        verify(mockTeacherSubjectSchoolYearSearchRepository, times(1)).save(testTeacherSubjectSchoolYear);
    }

    @Test
    @Transactional
    public void updateNonExistingTeacherSubjectSchoolYear() throws Exception {
        int databaseSizeBeforeUpdate = teacherSubjectSchoolYearRepository.findAll().size();

        // Create the TeacherSubjectSchoolYear
        TeacherSubjectSchoolYearDTO teacherSubjectSchoolYearDTO = teacherSubjectSchoolYearMapper.toDto(teacherSubjectSchoolYear);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeacherSubjectSchoolYearMockMvc.perform(put("/api/teacher-subject-school-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherSubjectSchoolYearDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TeacherSubjectSchoolYear in the database
        List<TeacherSubjectSchoolYear> teacherSubjectSchoolYearList = teacherSubjectSchoolYearRepository.findAll();
        assertThat(teacherSubjectSchoolYearList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TeacherSubjectSchoolYear in Elasticsearch
        verify(mockTeacherSubjectSchoolYearSearchRepository, times(0)).save(teacherSubjectSchoolYear);
    }

    @Test
    @Transactional
    public void deleteTeacherSubjectSchoolYear() throws Exception {
        // Initialize the database
        teacherSubjectSchoolYearRepository.saveAndFlush(teacherSubjectSchoolYear);

        int databaseSizeBeforeDelete = teacherSubjectSchoolYearRepository.findAll().size();

        // Delete the teacherSubjectSchoolYear
        restTeacherSubjectSchoolYearMockMvc.perform(delete("/api/teacher-subject-school-years/{id}", teacherSubjectSchoolYear.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TeacherSubjectSchoolYear> teacherSubjectSchoolYearList = teacherSubjectSchoolYearRepository.findAll();
        assertThat(teacherSubjectSchoolYearList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TeacherSubjectSchoolYear in Elasticsearch
        verify(mockTeacherSubjectSchoolYearSearchRepository, times(1)).deleteById(teacherSubjectSchoolYear.getId());
    }

    @Test
    @Transactional
    public void searchTeacherSubjectSchoolYear() throws Exception {
        // Initialize the database
        teacherSubjectSchoolYearRepository.saveAndFlush(teacherSubjectSchoolYear);
        when(mockTeacherSubjectSchoolYearSearchRepository.search(queryStringQuery("id:" + teacherSubjectSchoolYear.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(teacherSubjectSchoolYear), PageRequest.of(0, 1), 1));
        // Search the teacherSubjectSchoolYear
        restTeacherSubjectSchoolYearMockMvc.perform(get("/api/_search/teacher-subject-school-years?query=id:" + teacherSubjectSchoolYear.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teacherSubjectSchoolYear.getId().intValue())))
            .andExpect(jsonPath("$.[*].isPrincipal").value(hasItem(DEFAULT_IS_PRINCIPAL.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeacherSubjectSchoolYear.class);
        TeacherSubjectSchoolYear teacherSubjectSchoolYear1 = new TeacherSubjectSchoolYear();
        teacherSubjectSchoolYear1.setId(1L);
        TeacherSubjectSchoolYear teacherSubjectSchoolYear2 = new TeacherSubjectSchoolYear();
        teacherSubjectSchoolYear2.setId(teacherSubjectSchoolYear1.getId());
        assertThat(teacherSubjectSchoolYear1).isEqualTo(teacherSubjectSchoolYear2);
        teacherSubjectSchoolYear2.setId(2L);
        assertThat(teacherSubjectSchoolYear1).isNotEqualTo(teacherSubjectSchoolYear2);
        teacherSubjectSchoolYear1.setId(null);
        assertThat(teacherSubjectSchoolYear1).isNotEqualTo(teacherSubjectSchoolYear2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeacherSubjectSchoolYearDTO.class);
        TeacherSubjectSchoolYearDTO teacherSubjectSchoolYearDTO1 = new TeacherSubjectSchoolYearDTO();
        teacherSubjectSchoolYearDTO1.setId(1L);
        TeacherSubjectSchoolYearDTO teacherSubjectSchoolYearDTO2 = new TeacherSubjectSchoolYearDTO();
        assertThat(teacherSubjectSchoolYearDTO1).isNotEqualTo(teacherSubjectSchoolYearDTO2);
        teacherSubjectSchoolYearDTO2.setId(teacherSubjectSchoolYearDTO1.getId());
        assertThat(teacherSubjectSchoolYearDTO1).isEqualTo(teacherSubjectSchoolYearDTO2);
        teacherSubjectSchoolYearDTO2.setId(2L);
        assertThat(teacherSubjectSchoolYearDTO1).isNotEqualTo(teacherSubjectSchoolYearDTO2);
        teacherSubjectSchoolYearDTO1.setId(null);
        assertThat(teacherSubjectSchoolYearDTO1).isNotEqualTo(teacherSubjectSchoolYearDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(teacherSubjectSchoolYearMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(teacherSubjectSchoolYearMapper.fromId(null)).isNull();
    }
}
