package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.ClassRoom;
import io.github.jhipster.application.repository.ClassRoomRepository;
import io.github.jhipster.application.repository.search.ClassRoomSearchRepository;
import io.github.jhipster.application.service.ClassRoomService;
import io.github.jhipster.application.service.dto.ClassRoomDTO;
import io.github.jhipster.application.service.mapper.ClassRoomMapper;
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
 * Test class for the ClassRoomResource REST controller.
 *
 * @see ClassRoomResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class ClassRoomResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private ClassRoomMapper classRoomMapper;

    @Autowired
    private ClassRoomService classRoomService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.ClassRoomSearchRepositoryMockConfiguration
     */
    @Autowired
    private ClassRoomSearchRepository mockClassRoomSearchRepository;

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

    private MockMvc restClassRoomMockMvc;

    private ClassRoom classRoom;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClassRoomResource classRoomResource = new ClassRoomResource(classRoomService);
        this.restClassRoomMockMvc = MockMvcBuilders.standaloneSetup(classRoomResource)
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
    public static ClassRoom createEntity(EntityManager em) {
        ClassRoom classRoom = new ClassRoom()
            .name(DEFAULT_NAME);
        return classRoom;
    }

    @Before
    public void initTest() {
        classRoom = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassRoom() throws Exception {
        int databaseSizeBeforeCreate = classRoomRepository.findAll().size();

        // Create the ClassRoom
        ClassRoomDTO classRoomDTO = classRoomMapper.toDto(classRoom);
        restClassRoomMockMvc.perform(post("/api/class-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classRoomDTO)))
            .andExpect(status().isCreated());

        // Validate the ClassRoom in the database
        List<ClassRoom> classRoomList = classRoomRepository.findAll();
        assertThat(classRoomList).hasSize(databaseSizeBeforeCreate + 1);
        ClassRoom testClassRoom = classRoomList.get(classRoomList.size() - 1);
        assertThat(testClassRoom.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the ClassRoom in Elasticsearch
        verify(mockClassRoomSearchRepository, times(1)).save(testClassRoom);
    }

    @Test
    @Transactional
    public void createClassRoomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classRoomRepository.findAll().size();

        // Create the ClassRoom with an existing ID
        classRoom.setId(1L);
        ClassRoomDTO classRoomDTO = classRoomMapper.toDto(classRoom);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassRoomMockMvc.perform(post("/api/class-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classRoomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassRoom in the database
        List<ClassRoom> classRoomList = classRoomRepository.findAll();
        assertThat(classRoomList).hasSize(databaseSizeBeforeCreate);

        // Validate the ClassRoom in Elasticsearch
        verify(mockClassRoomSearchRepository, times(0)).save(classRoom);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = classRoomRepository.findAll().size();
        // set the field null
        classRoom.setName(null);

        // Create the ClassRoom, which fails.
        ClassRoomDTO classRoomDTO = classRoomMapper.toDto(classRoom);

        restClassRoomMockMvc.perform(post("/api/class-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classRoomDTO)))
            .andExpect(status().isBadRequest());

        List<ClassRoom> classRoomList = classRoomRepository.findAll();
        assertThat(classRoomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClassRooms() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList
        restClassRoomMockMvc.perform(get("/api/class-rooms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classRoom.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getClassRoom() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get the classRoom
        restClassRoomMockMvc.perform(get("/api/class-rooms/{id}", classRoom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classRoom.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClassRoom() throws Exception {
        // Get the classRoom
        restClassRoomMockMvc.perform(get("/api/class-rooms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassRoom() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        int databaseSizeBeforeUpdate = classRoomRepository.findAll().size();

        // Update the classRoom
        ClassRoom updatedClassRoom = classRoomRepository.findById(classRoom.getId()).get();
        // Disconnect from session so that the updates on updatedClassRoom are not directly saved in db
        em.detach(updatedClassRoom);
        updatedClassRoom
            .name(UPDATED_NAME);
        ClassRoomDTO classRoomDTO = classRoomMapper.toDto(updatedClassRoom);

        restClassRoomMockMvc.perform(put("/api/class-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classRoomDTO)))
            .andExpect(status().isOk());

        // Validate the ClassRoom in the database
        List<ClassRoom> classRoomList = classRoomRepository.findAll();
        assertThat(classRoomList).hasSize(databaseSizeBeforeUpdate);
        ClassRoom testClassRoom = classRoomList.get(classRoomList.size() - 1);
        assertThat(testClassRoom.getName()).isEqualTo(UPDATED_NAME);

        // Validate the ClassRoom in Elasticsearch
        verify(mockClassRoomSearchRepository, times(1)).save(testClassRoom);
    }

    @Test
    @Transactional
    public void updateNonExistingClassRoom() throws Exception {
        int databaseSizeBeforeUpdate = classRoomRepository.findAll().size();

        // Create the ClassRoom
        ClassRoomDTO classRoomDTO = classRoomMapper.toDto(classRoom);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassRoomMockMvc.perform(put("/api/class-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classRoomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassRoom in the database
        List<ClassRoom> classRoomList = classRoomRepository.findAll();
        assertThat(classRoomList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassRoom in Elasticsearch
        verify(mockClassRoomSearchRepository, times(0)).save(classRoom);
    }

    @Test
    @Transactional
    public void deleteClassRoom() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        int databaseSizeBeforeDelete = classRoomRepository.findAll().size();

        // Delete the classRoom
        restClassRoomMockMvc.perform(delete("/api/class-rooms/{id}", classRoom.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClassRoom> classRoomList = classRoomRepository.findAll();
        assertThat(classRoomList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ClassRoom in Elasticsearch
        verify(mockClassRoomSearchRepository, times(1)).deleteById(classRoom.getId());
    }

    @Test
    @Transactional
    public void searchClassRoom() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);
        when(mockClassRoomSearchRepository.search(queryStringQuery("id:" + classRoom.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(classRoom), PageRequest.of(0, 1), 1));
        // Search the classRoom
        restClassRoomMockMvc.perform(get("/api/_search/class-rooms?query=id:" + classRoom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classRoom.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassRoom.class);
        ClassRoom classRoom1 = new ClassRoom();
        classRoom1.setId(1L);
        ClassRoom classRoom2 = new ClassRoom();
        classRoom2.setId(classRoom1.getId());
        assertThat(classRoom1).isEqualTo(classRoom2);
        classRoom2.setId(2L);
        assertThat(classRoom1).isNotEqualTo(classRoom2);
        classRoom1.setId(null);
        assertThat(classRoom1).isNotEqualTo(classRoom2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassRoomDTO.class);
        ClassRoomDTO classRoomDTO1 = new ClassRoomDTO();
        classRoomDTO1.setId(1L);
        ClassRoomDTO classRoomDTO2 = new ClassRoomDTO();
        assertThat(classRoomDTO1).isNotEqualTo(classRoomDTO2);
        classRoomDTO2.setId(classRoomDTO1.getId());
        assertThat(classRoomDTO1).isEqualTo(classRoomDTO2);
        classRoomDTO2.setId(2L);
        assertThat(classRoomDTO1).isNotEqualTo(classRoomDTO2);
        classRoomDTO1.setId(null);
        assertThat(classRoomDTO1).isNotEqualTo(classRoomDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(classRoomMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(classRoomMapper.fromId(null)).isNull();
    }
}
