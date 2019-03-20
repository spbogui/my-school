package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.School;
import io.github.jhipster.application.repository.SchoolRepository;
import io.github.jhipster.application.repository.search.SchoolSearchRepository;
import io.github.jhipster.application.service.SchoolService;
import io.github.jhipster.application.service.dto.SchoolDTO;
import io.github.jhipster.application.service.mapper.SchoolMapper;
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
 * Test class for the SchoolResource REST controller.
 *
 * @see SchoolResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class SchoolResourceIntTest {

    private static final String DEFAULT_SCHOOL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SLOGAN = "AAAAAAAAAA";
    private static final String UPDATED_SLOGAN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_OPENNING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OPENNING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PHONE_NUMBER_1 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER_2 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER_2 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_SITE_LINK = "AAAAAAAAAA";
    private static final String UPDATED_WEB_SITE_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_TOWN = "AAAAAAAAAA";
    private static final String UPDATED_TOWN = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_MUNICIPALITY = "AAAAAAAAAA";
    private static final String UPDATED_MUNICIPALITY = "BBBBBBBBBB";

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private SchoolService schoolService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.SchoolSearchRepositoryMockConfiguration
     */
    @Autowired
    private SchoolSearchRepository mockSchoolSearchRepository;

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

    private MockMvc restSchoolMockMvc;

    private School school;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SchoolResource schoolResource = new SchoolResource(schoolService);
        this.restSchoolMockMvc = MockMvcBuilders.standaloneSetup(schoolResource)
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
    public static School createEntity(EntityManager em) {
        School school = new School()
            .schoolName(DEFAULT_SCHOOL_NAME)
            .slogan(DEFAULT_SLOGAN)
            .openningDate(DEFAULT_OPENNING_DATE)
            .phoneNumber1(DEFAULT_PHONE_NUMBER_1)
            .phoneNumber2(DEFAULT_PHONE_NUMBER_2)
            .email(DEFAULT_EMAIL)
            .fax(DEFAULT_FAX)
            .webSiteLink(DEFAULT_WEB_SITE_LINK)
            .address(DEFAULT_ADDRESS)
            .town(DEFAULT_TOWN)
            .region(DEFAULT_REGION)
            .municipality(DEFAULT_MUNICIPALITY);
        return school;
    }

    @Before
    public void initTest() {
        school = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchool() throws Exception {
        int databaseSizeBeforeCreate = schoolRepository.findAll().size();

        // Create the School
        SchoolDTO schoolDTO = schoolMapper.toDto(school);
        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isCreated());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeCreate + 1);
        School testSchool = schoolList.get(schoolList.size() - 1);
        assertThat(testSchool.getSchoolName()).isEqualTo(DEFAULT_SCHOOL_NAME);
        assertThat(testSchool.getSlogan()).isEqualTo(DEFAULT_SLOGAN);
        assertThat(testSchool.getOpenningDate()).isEqualTo(DEFAULT_OPENNING_DATE);
        assertThat(testSchool.getPhoneNumber1()).isEqualTo(DEFAULT_PHONE_NUMBER_1);
        assertThat(testSchool.getPhoneNumber2()).isEqualTo(DEFAULT_PHONE_NUMBER_2);
        assertThat(testSchool.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSchool.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testSchool.getWebSiteLink()).isEqualTo(DEFAULT_WEB_SITE_LINK);
        assertThat(testSchool.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testSchool.getTown()).isEqualTo(DEFAULT_TOWN);
        assertThat(testSchool.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testSchool.getMunicipality()).isEqualTo(DEFAULT_MUNICIPALITY);

        // Validate the School in Elasticsearch
        verify(mockSchoolSearchRepository, times(1)).save(testSchool);
    }

    @Test
    @Transactional
    public void createSchoolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schoolRepository.findAll().size();

        // Create the School with an existing ID
        school.setId(1L);
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeCreate);

        // Validate the School in Elasticsearch
        verify(mockSchoolSearchRepository, times(0)).save(school);
    }

    @Test
    @Transactional
    public void checkSchoolNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setSchoolName(null);

        // Create the School, which fails.
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isBadRequest());

        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneNumber1IsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setPhoneNumber1(null);

        // Create the School, which fails.
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isBadRequest());

        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchools() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList
        restSchoolMockMvc.perform(get("/api/schools?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(school.getId().intValue())))
            .andExpect(jsonPath("$.[*].schoolName").value(hasItem(DEFAULT_SCHOOL_NAME.toString())))
            .andExpect(jsonPath("$.[*].slogan").value(hasItem(DEFAULT_SLOGAN.toString())))
            .andExpect(jsonPath("$.[*].openningDate").value(hasItem(DEFAULT_OPENNING_DATE.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber1").value(hasItem(DEFAULT_PHONE_NUMBER_1.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber2").value(hasItem(DEFAULT_PHONE_NUMBER_2.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].webSiteLink").value(hasItem(DEFAULT_WEB_SITE_LINK.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].town").value(hasItem(DEFAULT_TOWN.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].municipality").value(hasItem(DEFAULT_MUNICIPALITY.toString())));
    }
    
    @Test
    @Transactional
    public void getSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get the school
        restSchoolMockMvc.perform(get("/api/schools/{id}", school.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(school.getId().intValue()))
            .andExpect(jsonPath("$.schoolName").value(DEFAULT_SCHOOL_NAME.toString()))
            .andExpect(jsonPath("$.slogan").value(DEFAULT_SLOGAN.toString()))
            .andExpect(jsonPath("$.openningDate").value(DEFAULT_OPENNING_DATE.toString()))
            .andExpect(jsonPath("$.phoneNumber1").value(DEFAULT_PHONE_NUMBER_1.toString()))
            .andExpect(jsonPath("$.phoneNumber2").value(DEFAULT_PHONE_NUMBER_2.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.webSiteLink").value(DEFAULT_WEB_SITE_LINK.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.town").value(DEFAULT_TOWN.toString()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.municipality").value(DEFAULT_MUNICIPALITY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchool() throws Exception {
        // Get the school
        restSchoolMockMvc.perform(get("/api/schools/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();

        // Update the school
        School updatedSchool = schoolRepository.findById(school.getId()).get();
        // Disconnect from session so that the updates on updatedSchool are not directly saved in db
        em.detach(updatedSchool);
        updatedSchool
            .schoolName(UPDATED_SCHOOL_NAME)
            .slogan(UPDATED_SLOGAN)
            .openningDate(UPDATED_OPENNING_DATE)
            .phoneNumber1(UPDATED_PHONE_NUMBER_1)
            .phoneNumber2(UPDATED_PHONE_NUMBER_2)
            .email(UPDATED_EMAIL)
            .fax(UPDATED_FAX)
            .webSiteLink(UPDATED_WEB_SITE_LINK)
            .address(UPDATED_ADDRESS)
            .town(UPDATED_TOWN)
            .region(UPDATED_REGION)
            .municipality(UPDATED_MUNICIPALITY);
        SchoolDTO schoolDTO = schoolMapper.toDto(updatedSchool);

        restSchoolMockMvc.perform(put("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isOk());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
        School testSchool = schoolList.get(schoolList.size() - 1);
        assertThat(testSchool.getSchoolName()).isEqualTo(UPDATED_SCHOOL_NAME);
        assertThat(testSchool.getSlogan()).isEqualTo(UPDATED_SLOGAN);
        assertThat(testSchool.getOpenningDate()).isEqualTo(UPDATED_OPENNING_DATE);
        assertThat(testSchool.getPhoneNumber1()).isEqualTo(UPDATED_PHONE_NUMBER_1);
        assertThat(testSchool.getPhoneNumber2()).isEqualTo(UPDATED_PHONE_NUMBER_2);
        assertThat(testSchool.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSchool.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testSchool.getWebSiteLink()).isEqualTo(UPDATED_WEB_SITE_LINK);
        assertThat(testSchool.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testSchool.getTown()).isEqualTo(UPDATED_TOWN);
        assertThat(testSchool.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testSchool.getMunicipality()).isEqualTo(UPDATED_MUNICIPALITY);

        // Validate the School in Elasticsearch
        verify(mockSchoolSearchRepository, times(1)).save(testSchool);
    }

    @Test
    @Transactional
    public void updateNonExistingSchool() throws Exception {
        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();

        // Create the School
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchoolMockMvc.perform(put("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);

        // Validate the School in Elasticsearch
        verify(mockSchoolSearchRepository, times(0)).save(school);
    }

    @Test
    @Transactional
    public void deleteSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        int databaseSizeBeforeDelete = schoolRepository.findAll().size();

        // Delete the school
        restSchoolMockMvc.perform(delete("/api/schools/{id}", school.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the School in Elasticsearch
        verify(mockSchoolSearchRepository, times(1)).deleteById(school.getId());
    }

    @Test
    @Transactional
    public void searchSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);
        when(mockSchoolSearchRepository.search(queryStringQuery("id:" + school.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(school), PageRequest.of(0, 1), 1));
        // Search the school
        restSchoolMockMvc.perform(get("/api/_search/schools?query=id:" + school.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(school.getId().intValue())))
            .andExpect(jsonPath("$.[*].schoolName").value(hasItem(DEFAULT_SCHOOL_NAME)))
            .andExpect(jsonPath("$.[*].slogan").value(hasItem(DEFAULT_SLOGAN)))
            .andExpect(jsonPath("$.[*].openningDate").value(hasItem(DEFAULT_OPENNING_DATE.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber1").value(hasItem(DEFAULT_PHONE_NUMBER_1)))
            .andExpect(jsonPath("$.[*].phoneNumber2").value(hasItem(DEFAULT_PHONE_NUMBER_2)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].webSiteLink").value(hasItem(DEFAULT_WEB_SITE_LINK)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].town").value(hasItem(DEFAULT_TOWN)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].municipality").value(hasItem(DEFAULT_MUNICIPALITY)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(School.class);
        School school1 = new School();
        school1.setId(1L);
        School school2 = new School();
        school2.setId(school1.getId());
        assertThat(school1).isEqualTo(school2);
        school2.setId(2L);
        assertThat(school1).isNotEqualTo(school2);
        school1.setId(null);
        assertThat(school1).isNotEqualTo(school2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchoolDTO.class);
        SchoolDTO schoolDTO1 = new SchoolDTO();
        schoolDTO1.setId(1L);
        SchoolDTO schoolDTO2 = new SchoolDTO();
        assertThat(schoolDTO1).isNotEqualTo(schoolDTO2);
        schoolDTO2.setId(schoolDTO1.getId());
        assertThat(schoolDTO1).isEqualTo(schoolDTO2);
        schoolDTO2.setId(2L);
        assertThat(schoolDTO1).isNotEqualTo(schoolDTO2);
        schoolDTO1.setId(null);
        assertThat(schoolDTO1).isNotEqualTo(schoolDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(schoolMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(schoolMapper.fromId(null)).isNull();
    }
}
