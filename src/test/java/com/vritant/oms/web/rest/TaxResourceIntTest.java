package com.vritant.oms.web.rest;

import com.vritant.oms.Application;
import com.vritant.oms.domain.Tax;
import com.vritant.oms.repository.TaxRepository;

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
 * Test class for the TaxResource REST controller.
 *
 * @see TaxResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TaxResourceIntTest {


    private static final Float DEFAULT_RATE = 1F;
    private static final Float UPDATED_RATE = 2F;

    @Inject
    private TaxRepository taxRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTaxMockMvc;

    private Tax tax;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TaxResource taxResource = new TaxResource();
        ReflectionTestUtils.setField(taxResource, "taxRepository", taxRepository);
        this.restTaxMockMvc = MockMvcBuilders.standaloneSetup(taxResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tax = new Tax();
        tax.setRate(DEFAULT_RATE);
    }

    @Test
    @Transactional
    public void createTax() throws Exception {
        int databaseSizeBeforeCreate = taxRepository.findAll().size();

        // Create the Tax

        restTaxMockMvc.perform(post("/api/taxs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tax)))
                .andExpect(status().isCreated());

        // Validate the Tax in the database
        List<Tax> taxs = taxRepository.findAll();
        assertThat(taxs).hasSize(databaseSizeBeforeCreate + 1);
        Tax testTax = taxs.get(taxs.size() - 1);
        assertThat(testTax.getRate()).isEqualTo(DEFAULT_RATE);
    }

    @Test
    @Transactional
    public void checkRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxRepository.findAll().size();
        // set the field null
        tax.setRate(null);

        // Create the Tax, which fails.

        restTaxMockMvc.perform(post("/api/taxs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tax)))
                .andExpect(status().isBadRequest());

        List<Tax> taxs = taxRepository.findAll();
        assertThat(taxs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaxs() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxs
        restTaxMockMvc.perform(get("/api/taxs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tax.getId().intValue())))
                .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())));
    }

    @Test
    @Transactional
    public void getTax() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get the tax
        restTaxMockMvc.perform(get("/api/taxs/{id}", tax.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tax.getId().intValue()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTax() throws Exception {
        // Get the tax
        restTaxMockMvc.perform(get("/api/taxs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTax() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

		int databaseSizeBeforeUpdate = taxRepository.findAll().size();

        // Update the tax
        tax.setRate(UPDATED_RATE);

        restTaxMockMvc.perform(put("/api/taxs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tax)))
                .andExpect(status().isOk());

        // Validate the Tax in the database
        List<Tax> taxs = taxRepository.findAll();
        assertThat(taxs).hasSize(databaseSizeBeforeUpdate);
        Tax testTax = taxs.get(taxs.size() - 1);
        assertThat(testTax.getRate()).isEqualTo(UPDATED_RATE);
    }

    @Test
    @Transactional
    public void deleteTax() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

		int databaseSizeBeforeDelete = taxRepository.findAll().size();

        // Get the tax
        restTaxMockMvc.perform(delete("/api/taxs/{id}", tax.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tax> taxs = taxRepository.findAll();
        assertThat(taxs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
