package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MySchoolApp;

import io.github.jhipster.application.domain.RubricAmount;
import io.github.jhipster.application.repository.RubricAmountRepository;
import io.github.jhipster.application.repository.search.RubricAmountSearchRepository;
import io.github.jhipster.application.service.RubricAmountService;
import io.github.jhipster.application.service.dto.RubricAmountDTO;
import io.github.jhipster.application.service.mapper.RubricAmountMapper;
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
 * Test class for the RubricAmountResource REST controller.
 *
 * @see RubricAmountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySchoolApp.class)
public class RubricAmountResourceIntTest {

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final String DEFAULT_PAYMENT_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_METHOD = "BBBBBBBBBB";

    @Autowired
    private RubricAmountRepository rubricAmountRepository;

    @Autowired
    private RubricAmountMapper rubricAmountMapper;

    @Autowired
    private RubricAmountService rubricAmountService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.RubricAmountSearchRepositoryMockConfiguration
     */
    @Autowired
    private RubricAmountSearchRepository mockRubricAmountSearchRepository;

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

    private MockMvc restRubricAmountMockMvc;

    private RubricAmount rubricAmount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RubricAmountResource rubricAmountResource = new RubricAmountResource(rubricAmountService);
        this.restRubricAmountMockMvc = MockMvcBuilders.standaloneSetup(rubricAmountResource)
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
    public static RubricAmount createEntity(EntityManager em) {
        RubricAmount rubricAmount = new RubricAmount()
            .amount(DEFAULT_AMOUNT)
            .paymentMethod(DEFAULT_PAYMENT_METHOD);
        return rubricAmount;
    }

    @Before
    public void initTest() {
        rubricAmount = createEntity(em);
    }

    @Test
    @Transactional
    public void createRubricAmount() throws Exception {
        int databaseSizeBeforeCreate = rubricAmountRepository.findAll().size();

        // Create the RubricAmount
        RubricAmountDTO rubricAmountDTO = rubricAmountMapper.toDto(rubricAmount);
        restRubricAmountMockMvc.perform(post("/api/rubric-amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubricAmountDTO)))
            .andExpect(status().isCreated());

        // Validate the RubricAmount in the database
        List<RubricAmount> rubricAmountList = rubricAmountRepository.findAll();
        assertThat(rubricAmountList).hasSize(databaseSizeBeforeCreate + 1);
        RubricAmount testRubricAmount = rubricAmountList.get(rubricAmountList.size() - 1);
        assertThat(testRubricAmount.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testRubricAmount.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);

        // Validate the RubricAmount in Elasticsearch
        verify(mockRubricAmountSearchRepository, times(1)).save(testRubricAmount);
    }

    @Test
    @Transactional
    public void createRubricAmountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rubricAmountRepository.findAll().size();

        // Create the RubricAmount with an existing ID
        rubricAmount.setId(1L);
        RubricAmountDTO rubricAmountDTO = rubricAmountMapper.toDto(rubricAmount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRubricAmountMockMvc.perform(post("/api/rubric-amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubricAmountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RubricAmount in the database
        List<RubricAmount> rubricAmountList = rubricAmountRepository.findAll();
        assertThat(rubricAmountList).hasSize(databaseSizeBeforeCreate);

        // Validate the RubricAmount in Elasticsearch
        verify(mockRubricAmountSearchRepository, times(0)).save(rubricAmount);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = rubricAmountRepository.findAll().size();
        // set the field null
        rubricAmount.setAmount(null);

        // Create the RubricAmount, which fails.
        RubricAmountDTO rubricAmountDTO = rubricAmountMapper.toDto(rubricAmount);

        restRubricAmountMockMvc.perform(post("/api/rubric-amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubricAmountDTO)))
            .andExpect(status().isBadRequest());

        List<RubricAmount> rubricAmountList = rubricAmountRepository.findAll();
        assertThat(rubricAmountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentMethodIsRequired() throws Exception {
        int databaseSizeBeforeTest = rubricAmountRepository.findAll().size();
        // set the field null
        rubricAmount.setPaymentMethod(null);

        // Create the RubricAmount, which fails.
        RubricAmountDTO rubricAmountDTO = rubricAmountMapper.toDto(rubricAmount);

        restRubricAmountMockMvc.perform(post("/api/rubric-amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubricAmountDTO)))
            .andExpect(status().isBadRequest());

        List<RubricAmount> rubricAmountList = rubricAmountRepository.findAll();
        assertThat(rubricAmountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRubricAmounts() throws Exception {
        // Initialize the database
        rubricAmountRepository.saveAndFlush(rubricAmount);

        // Get all the rubricAmountList
        restRubricAmountMockMvc.perform(get("/api/rubric-amounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rubricAmount.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD.toString())));
    }
    
    @Test
    @Transactional
    public void getRubricAmount() throws Exception {
        // Initialize the database
        rubricAmountRepository.saveAndFlush(rubricAmount);

        // Get the rubricAmount
        restRubricAmountMockMvc.perform(get("/api/rubric-amounts/{id}", rubricAmount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rubricAmount.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRubricAmount() throws Exception {
        // Get the rubricAmount
        restRubricAmountMockMvc.perform(get("/api/rubric-amounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRubricAmount() throws Exception {
        // Initialize the database
        rubricAmountRepository.saveAndFlush(rubricAmount);

        int databaseSizeBeforeUpdate = rubricAmountRepository.findAll().size();

        // Update the rubricAmount
        RubricAmount updatedRubricAmount = rubricAmountRepository.findById(rubricAmount.getId()).get();
        // Disconnect from session so that the updates on updatedRubricAmount are not directly saved in db
        em.detach(updatedRubricAmount);
        updatedRubricAmount
            .amount(UPDATED_AMOUNT)
            .paymentMethod(UPDATED_PAYMENT_METHOD);
        RubricAmountDTO rubricAmountDTO = rubricAmountMapper.toDto(updatedRubricAmount);

        restRubricAmountMockMvc.perform(put("/api/rubric-amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubricAmountDTO)))
            .andExpect(status().isOk());

        // Validate the RubricAmount in the database
        List<RubricAmount> rubricAmountList = rubricAmountRepository.findAll();
        assertThat(rubricAmountList).hasSize(databaseSizeBeforeUpdate);
        RubricAmount testRubricAmount = rubricAmountList.get(rubricAmountList.size() - 1);
        assertThat(testRubricAmount.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testRubricAmount.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);

        // Validate the RubricAmount in Elasticsearch
        verify(mockRubricAmountSearchRepository, times(1)).save(testRubricAmount);
    }

    @Test
    @Transactional
    public void updateNonExistingRubricAmount() throws Exception {
        int databaseSizeBeforeUpdate = rubricAmountRepository.findAll().size();

        // Create the RubricAmount
        RubricAmountDTO rubricAmountDTO = rubricAmountMapper.toDto(rubricAmount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRubricAmountMockMvc.perform(put("/api/rubric-amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubricAmountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RubricAmount in the database
        List<RubricAmount> rubricAmountList = rubricAmountRepository.findAll();
        assertThat(rubricAmountList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RubricAmount in Elasticsearch
        verify(mockRubricAmountSearchRepository, times(0)).save(rubricAmount);
    }

    @Test
    @Transactional
    public void deleteRubricAmount() throws Exception {
        // Initialize the database
        rubricAmountRepository.saveAndFlush(rubricAmount);

        int databaseSizeBeforeDelete = rubricAmountRepository.findAll().size();

        // Delete the rubricAmount
        restRubricAmountMockMvc.perform(delete("/api/rubric-amounts/{id}", rubricAmount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RubricAmount> rubricAmountList = rubricAmountRepository.findAll();
        assertThat(rubricAmountList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RubricAmount in Elasticsearch
        verify(mockRubricAmountSearchRepository, times(1)).deleteById(rubricAmount.getId());
    }

    @Test
    @Transactional
    public void searchRubricAmount() throws Exception {
        // Initialize the database
        rubricAmountRepository.saveAndFlush(rubricAmount);
        when(mockRubricAmountSearchRepository.search(queryStringQuery("id:" + rubricAmount.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(rubricAmount), PageRequest.of(0, 1), 1));
        // Search the rubricAmount
        restRubricAmountMockMvc.perform(get("/api/_search/rubric-amounts?query=id:" + rubricAmount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rubricAmount.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RubricAmount.class);
        RubricAmount rubricAmount1 = new RubricAmount();
        rubricAmount1.setId(1L);
        RubricAmount rubricAmount2 = new RubricAmount();
        rubricAmount2.setId(rubricAmount1.getId());
        assertThat(rubricAmount1).isEqualTo(rubricAmount2);
        rubricAmount2.setId(2L);
        assertThat(rubricAmount1).isNotEqualTo(rubricAmount2);
        rubricAmount1.setId(null);
        assertThat(rubricAmount1).isNotEqualTo(rubricAmount2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RubricAmountDTO.class);
        RubricAmountDTO rubricAmountDTO1 = new RubricAmountDTO();
        rubricAmountDTO1.setId(1L);
        RubricAmountDTO rubricAmountDTO2 = new RubricAmountDTO();
        assertThat(rubricAmountDTO1).isNotEqualTo(rubricAmountDTO2);
        rubricAmountDTO2.setId(rubricAmountDTO1.getId());
        assertThat(rubricAmountDTO1).isEqualTo(rubricAmountDTO2);
        rubricAmountDTO2.setId(2L);
        assertThat(rubricAmountDTO1).isNotEqualTo(rubricAmountDTO2);
        rubricAmountDTO1.setId(null);
        assertThat(rubricAmountDTO1).isNotEqualTo(rubricAmountDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rubricAmountMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rubricAmountMapper.fromId(null)).isNull();
    }
}
