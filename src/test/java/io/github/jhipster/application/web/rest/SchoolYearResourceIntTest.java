package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.SchoolYear;
import io.github.jhipster.application.repository.SchoolYearRepository;
import io.github.jhipster.application.repository.search.SchoolYearSearchRepository;
import io.github.jhipster.application.service.SchoolYearService;
import io.github.jhipster.application.service.dto.SchoolYearDTO;
import io.github.jhipster.application.service.mapper.SchoolYearMapper;
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
 * Test class for the SchoolYearResource REST controller.
 *
 * @see SchoolYearResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class SchoolYearResourceIntTest {

    private static final String DEFAULT_SCHOOL_YEARLABEL = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL_YEARLABEL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_COURSE_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COURSE_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_COURSE_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COURSE_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_BLANK_YEAR = false;
    private static final Boolean UPDATED_IS_BLANK_YEAR = true;

    @Autowired
    private SchoolYearRepository schoolYearRepository;

    @Autowired
    private SchoolYearMapper schoolYearMapper;

    @Autowired
    private SchoolYearService schoolYearService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.SchoolYearSearchRepositoryMockConfiguration
     */
    @Autowired
    private SchoolYearSearchRepository mockSchoolYearSearchRepository;

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

    private MockMvc restSchoolYearMockMvc;

    private SchoolYear schoolYear;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SchoolYearResource schoolYearResource = new SchoolYearResource(schoolYearService);
        this.restSchoolYearMockMvc = MockMvcBuilders.standaloneSetup(schoolYearResource)
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
    public static SchoolYear createEntity(EntityManager em) {
        SchoolYear schoolYear = new SchoolYear()
            .schoolYearlabel(DEFAULT_SCHOOL_YEARLABEL)
            .courseStartDate(DEFAULT_COURSE_START_DATE)
            .courseEndDate(DEFAULT_COURSE_END_DATE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .isBlankYear(DEFAULT_IS_BLANK_YEAR);
        return schoolYear;
    }

    @Before
    public void initTest() {
        schoolYear = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchoolYear() throws Exception {
        int databaseSizeBeforeCreate = schoolYearRepository.findAll().size();

        // Create the SchoolYear
        SchoolYearDTO schoolYearDTO = schoolYearMapper.toDto(schoolYear);
        restSchoolYearMockMvc.perform(post("/api/school-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolYearDTO)))
            .andExpect(status().isCreated());

        // Validate the SchoolYear in the database
        List<SchoolYear> schoolYearList = schoolYearRepository.findAll();
        assertThat(schoolYearList).hasSize(databaseSizeBeforeCreate + 1);
        SchoolYear testSchoolYear = schoolYearList.get(schoolYearList.size() - 1);
        assertThat(testSchoolYear.getSchoolYearlabel()).isEqualTo(DEFAULT_SCHOOL_YEARLABEL);
        assertThat(testSchoolYear.getCourseStartDate()).isEqualTo(DEFAULT_COURSE_START_DATE);
        assertThat(testSchoolYear.getCourseEndDate()).isEqualTo(DEFAULT_COURSE_END_DATE);
        assertThat(testSchoolYear.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testSchoolYear.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testSchoolYear.isIsBlankYear()).isEqualTo(DEFAULT_IS_BLANK_YEAR);

        // Validate the SchoolYear in Elasticsearch
        verify(mockSchoolYearSearchRepository, times(1)).save(testSchoolYear);
    }

    @Test
    @Transactional
    public void createSchoolYearWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schoolYearRepository.findAll().size();

        // Create the SchoolYear with an existing ID
        schoolYear.setId(1L);
        SchoolYearDTO schoolYearDTO = schoolYearMapper.toDto(schoolYear);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchoolYearMockMvc.perform(post("/api/school-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolYearDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SchoolYear in the database
        List<SchoolYear> schoolYearList = schoolYearRepository.findAll();
        assertThat(schoolYearList).hasSize(databaseSizeBeforeCreate);

        // Validate the SchoolYear in Elasticsearch
        verify(mockSchoolYearSearchRepository, times(0)).save(schoolYear);
    }

    @Test
    @Transactional
    public void checkSchoolYearlabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolYearRepository.findAll().size();
        // set the field null
        schoolYear.setSchoolYearlabel(null);

        // Create the SchoolYear, which fails.
        SchoolYearDTO schoolYearDTO = schoolYearMapper.toDto(schoolYear);

        restSchoolYearMockMvc.perform(post("/api/school-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolYearDTO)))
            .andExpect(status().isBadRequest());

        List<SchoolYear> schoolYearList = schoolYearRepository.findAll();
        assertThat(schoolYearList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCourseStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolYearRepository.findAll().size();
        // set the field null
        schoolYear.setCourseStartDate(null);

        // Create the SchoolYear, which fails.
        SchoolYearDTO schoolYearDTO = schoolYearMapper.toDto(schoolYear);

        restSchoolYearMockMvc.perform(post("/api/school-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolYearDTO)))
            .andExpect(status().isBadRequest());

        List<SchoolYear> schoolYearList = schoolYearRepository.findAll();
        assertThat(schoolYearList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCourseEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolYearRepository.findAll().size();
        // set the field null
        schoolYear.setCourseEndDate(null);

        // Create the SchoolYear, which fails.
        SchoolYearDTO schoolYearDTO = schoolYearMapper.toDto(schoolYear);

        restSchoolYearMockMvc.perform(post("/api/school-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolYearDTO)))
            .andExpect(status().isBadRequest());

        List<SchoolYear> schoolYearList = schoolYearRepository.findAll();
        assertThat(schoolYearList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolYearRepository.findAll().size();
        // set the field null
        schoolYear.setStartDate(null);

        // Create the SchoolYear, which fails.
        SchoolYearDTO schoolYearDTO = schoolYearMapper.toDto(schoolYear);

        restSchoolYearMockMvc.perform(post("/api/school-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolYearDTO)))
            .andExpect(status().isBadRequest());

        List<SchoolYear> schoolYearList = schoolYearRepository.findAll();
        assertThat(schoolYearList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolYearRepository.findAll().size();
        // set the field null
        schoolYear.setEndDate(null);

        // Create the SchoolYear, which fails.
        SchoolYearDTO schoolYearDTO = schoolYearMapper.toDto(schoolYear);

        restSchoolYearMockMvc.perform(post("/api/school-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolYearDTO)))
            .andExpect(status().isBadRequest());

        List<SchoolYear> schoolYearList = schoolYearRepository.findAll();
        assertThat(schoolYearList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsBlankYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolYearRepository.findAll().size();
        // set the field null
        schoolYear.setIsBlankYear(null);

        // Create the SchoolYear, which fails.
        SchoolYearDTO schoolYearDTO = schoolYearMapper.toDto(schoolYear);

        restSchoolYearMockMvc.perform(post("/api/school-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolYearDTO)))
            .andExpect(status().isBadRequest());

        List<SchoolYear> schoolYearList = schoolYearRepository.findAll();
        assertThat(schoolYearList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchoolYears() throws Exception {
        // Initialize the database
        schoolYearRepository.saveAndFlush(schoolYear);

        // Get all the schoolYearList
        restSchoolYearMockMvc.perform(get("/api/school-years?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schoolYear.getId().intValue())))
            .andExpect(jsonPath("$.[*].schoolYearlabel").value(hasItem(DEFAULT_SCHOOL_YEARLABEL.toString())))
            .andExpect(jsonPath("$.[*].courseStartDate").value(hasItem(DEFAULT_COURSE_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].courseEndDate").value(hasItem(DEFAULT_COURSE_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].isBlankYear").value(hasItem(DEFAULT_IS_BLANK_YEAR.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSchoolYear() throws Exception {
        // Initialize the database
        schoolYearRepository.saveAndFlush(schoolYear);

        // Get the schoolYear
        restSchoolYearMockMvc.perform(get("/api/school-years/{id}", schoolYear.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(schoolYear.getId().intValue()))
            .andExpect(jsonPath("$.schoolYearlabel").value(DEFAULT_SCHOOL_YEARLABEL.toString()))
            .andExpect(jsonPath("$.courseStartDate").value(DEFAULT_COURSE_START_DATE.toString()))
            .andExpect(jsonPath("$.courseEndDate").value(DEFAULT_COURSE_END_DATE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.isBlankYear").value(DEFAULT_IS_BLANK_YEAR.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSchoolYear() throws Exception {
        // Get the schoolYear
        restSchoolYearMockMvc.perform(get("/api/school-years/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchoolYear() throws Exception {
        // Initialize the database
        schoolYearRepository.saveAndFlush(schoolYear);

        int databaseSizeBeforeUpdate = schoolYearRepository.findAll().size();

        // Update the schoolYear
        SchoolYear updatedSchoolYear = schoolYearRepository.findById(schoolYear.getId()).get();
        // Disconnect from session so that the updates on updatedSchoolYear are not directly saved in db
        em.detach(updatedSchoolYear);
        updatedSchoolYear
            .schoolYearlabel(UPDATED_SCHOOL_YEARLABEL)
            .courseStartDate(UPDATED_COURSE_START_DATE)
            .courseEndDate(UPDATED_COURSE_END_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .isBlankYear(UPDATED_IS_BLANK_YEAR);
        SchoolYearDTO schoolYearDTO = schoolYearMapper.toDto(updatedSchoolYear);

        restSchoolYearMockMvc.perform(put("/api/school-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolYearDTO)))
            .andExpect(status().isOk());

        // Validate the SchoolYear in the database
        List<SchoolYear> schoolYearList = schoolYearRepository.findAll();
        assertThat(schoolYearList).hasSize(databaseSizeBeforeUpdate);
        SchoolYear testSchoolYear = schoolYearList.get(schoolYearList.size() - 1);
        assertThat(testSchoolYear.getSchoolYearlabel()).isEqualTo(UPDATED_SCHOOL_YEARLABEL);
        assertThat(testSchoolYear.getCourseStartDate()).isEqualTo(UPDATED_COURSE_START_DATE);
        assertThat(testSchoolYear.getCourseEndDate()).isEqualTo(UPDATED_COURSE_END_DATE);
        assertThat(testSchoolYear.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSchoolYear.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSchoolYear.isIsBlankYear()).isEqualTo(UPDATED_IS_BLANK_YEAR);

        // Validate the SchoolYear in Elasticsearch
        verify(mockSchoolYearSearchRepository, times(1)).save(testSchoolYear);
    }

    @Test
    @Transactional
    public void updateNonExistingSchoolYear() throws Exception {
        int databaseSizeBeforeUpdate = schoolYearRepository.findAll().size();

        // Create the SchoolYear
        SchoolYearDTO schoolYearDTO = schoolYearMapper.toDto(schoolYear);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchoolYearMockMvc.perform(put("/api/school-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolYearDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SchoolYear in the database
        List<SchoolYear> schoolYearList = schoolYearRepository.findAll();
        assertThat(schoolYearList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SchoolYear in Elasticsearch
        verify(mockSchoolYearSearchRepository, times(0)).save(schoolYear);
    }

    @Test
    @Transactional
    public void deleteSchoolYear() throws Exception {
        // Initialize the database
        schoolYearRepository.saveAndFlush(schoolYear);

        int databaseSizeBeforeDelete = schoolYearRepository.findAll().size();

        // Delete the schoolYear
        restSchoolYearMockMvc.perform(delete("/api/school-years/{id}", schoolYear.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SchoolYear> schoolYearList = schoolYearRepository.findAll();
        assertThat(schoolYearList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SchoolYear in Elasticsearch
        verify(mockSchoolYearSearchRepository, times(1)).deleteById(schoolYear.getId());
    }

    @Test
    @Transactional
    public void searchSchoolYear() throws Exception {
        // Initialize the database
        schoolYearRepository.saveAndFlush(schoolYear);
        when(mockSchoolYearSearchRepository.search(queryStringQuery("id:" + schoolYear.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(schoolYear), PageRequest.of(0, 1), 1));
        // Search the schoolYear
        restSchoolYearMockMvc.perform(get("/api/_search/school-years?query=id:" + schoolYear.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schoolYear.getId().intValue())))
            .andExpect(jsonPath("$.[*].schoolYearlabel").value(hasItem(DEFAULT_SCHOOL_YEARLABEL)))
            .andExpect(jsonPath("$.[*].courseStartDate").value(hasItem(DEFAULT_COURSE_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].courseEndDate").value(hasItem(DEFAULT_COURSE_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].isBlankYear").value(hasItem(DEFAULT_IS_BLANK_YEAR.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchoolYear.class);
        SchoolYear schoolYear1 = new SchoolYear();
        schoolYear1.setId(1L);
        SchoolYear schoolYear2 = new SchoolYear();
        schoolYear2.setId(schoolYear1.getId());
        assertThat(schoolYear1).isEqualTo(schoolYear2);
        schoolYear2.setId(2L);
        assertThat(schoolYear1).isNotEqualTo(schoolYear2);
        schoolYear1.setId(null);
        assertThat(schoolYear1).isNotEqualTo(schoolYear2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchoolYearDTO.class);
        SchoolYearDTO schoolYearDTO1 = new SchoolYearDTO();
        schoolYearDTO1.setId(1L);
        SchoolYearDTO schoolYearDTO2 = new SchoolYearDTO();
        assertThat(schoolYearDTO1).isNotEqualTo(schoolYearDTO2);
        schoolYearDTO2.setId(schoolYearDTO1.getId());
        assertThat(schoolYearDTO1).isEqualTo(schoolYearDTO2);
        schoolYearDTO2.setId(2L);
        assertThat(schoolYearDTO1).isNotEqualTo(schoolYearDTO2);
        schoolYearDTO1.setId(null);
        assertThat(schoolYearDTO1).isNotEqualTo(schoolYearDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(schoolYearMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(schoolYearMapper.fromId(null)).isNull();
    }
}
