package com.vritant.oms.web.rest;

import com.vritant.oms.Application;
import com.vritant.oms.domain.TaxType;
import com.vritant.oms.repository.TaxTypeRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TaxTypeResource REST controller.
 *
 * @see TaxTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TaxTypeResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAA";
    private static final String UPDATED_LABEL = "BBBBB";

    @Inject
    private TaxTypeRepository taxTypeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTaxTypeMockMvc;

    private TaxType taxType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TaxTypeResource taxTypeResource = new TaxTypeResource();
        ReflectionTestUtils.setField(taxTypeResource, "taxTypeRepository", taxTypeRepository);
        this.restTaxTypeMockMvc = MockMvcBuilders.standaloneSetup(taxTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        taxType = new TaxType();
        taxType.setLabel(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createTaxType() throws Exception {
        int databaseSizeBeforeCreate = taxTypeRepository.findAll().size();

        // Create the TaxType

        restTaxTypeMockMvc.perform(post("/api/taxTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxType)))
                .andExpect(status().isCreated());

        // Validate the TaxType in the database
        List<TaxType> taxTypes = taxTypeRepository.findAll();
        assertThat(taxTypes).hasSize(databaseSizeBeforeCreate + 1);
        TaxType testTaxType = taxTypes.get(taxTypes.size() - 1);
        assertThat(testTaxType.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxTypeRepository.findAll().size();
        // set the field null
        taxType.setLabel(null);

        // Create the TaxType, which fails.

        restTaxTypeMockMvc.perform(post("/api/taxTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxType)))
                .andExpect(status().isBadRequest());

        List<TaxType> taxTypes = taxTypeRepository.findAll();
        assertThat(taxTypes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaxTypes() throws Exception {
        // Initialize the database
        taxTypeRepository.saveAndFlush(taxType);

        // Get all the taxTypes
        restTaxTypeMockMvc.perform(get("/api/taxTypes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(taxType.getId().intValue())))
                .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }

    @Test
    @Transactional
    public void getTaxType() throws Exception {
        // Initialize the database
        taxTypeRepository.saveAndFlush(taxType);

        // Get the taxType
        restTaxTypeMockMvc.perform(get("/api/taxTypes/{id}", taxType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(taxType.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTaxType() throws Exception {
        // Get the taxType
        restTaxTypeMockMvc.perform(get("/api/taxTypes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxType() throws Exception {
        // Initialize the database
        taxTypeRepository.saveAndFlush(taxType);

		int databaseSizeBeforeUpdate = taxTypeRepository.findAll().size();

        // Update the taxType
        taxType.setLabel(UPDATED_LABEL);

        restTaxTypeMockMvc.perform(put("/api/taxTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxType)))
                .andExpect(status().isOk());

        // Validate the TaxType in the database
        List<TaxType> taxTypes = taxTypeRepository.findAll();
        assertThat(taxTypes).hasSize(databaseSizeBeforeUpdate);
        TaxType testTaxType = taxTypes.get(taxTypes.size() - 1);
        assertThat(testTaxType.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void deleteTaxType() throws Exception {
        // Initialize the database
        taxTypeRepository.saveAndFlush(taxType);

		int databaseSizeBeforeDelete = taxTypeRepository.findAll().size();

        // Get the taxType
        restTaxTypeMockMvc.perform(delete("/api/taxTypes/{id}", taxType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TaxType> taxTypes = taxTypeRepository.findAll();
        assertThat(taxTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
