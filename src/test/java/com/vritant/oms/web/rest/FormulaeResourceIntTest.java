package com.vritant.oms.web.rest;

import com.vritant.oms.Application;
import com.vritant.oms.domain.Formulae;
import com.vritant.oms.repository.FormulaeRepository;

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
 * Test class for the FormulaeResource REST controller.
 *
 * @see FormulaeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FormulaeResourceIntTest {


    @Inject
    private FormulaeRepository formulaeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFormulaeMockMvc;

    private Formulae formulae;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FormulaeResource formulaeResource = new FormulaeResource();
        ReflectionTestUtils.setField(formulaeResource, "formulaeRepository", formulaeRepository);
        this.restFormulaeMockMvc = MockMvcBuilders.standaloneSetup(formulaeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        formulae = new Formulae();
    }

    @Test
    @Transactional
    public void createFormulae() throws Exception {
        int databaseSizeBeforeCreate = formulaeRepository.findAll().size();

        // Create the Formulae

        restFormulaeMockMvc.perform(post("/api/formulaes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(formulae)))
                .andExpect(status().isCreated());

        // Validate the Formulae in the database
        List<Formulae> formulaes = formulaeRepository.findAll();
        assertThat(formulaes).hasSize(databaseSizeBeforeCreate + 1);
        Formulae testFormulae = formulaes.get(formulaes.size() - 1);
    }

    @Test
    @Transactional
    public void getAllFormulaes() throws Exception {
        // Initialize the database
        formulaeRepository.saveAndFlush(formulae);

        // Get all the formulaes
        restFormulaeMockMvc.perform(get("/api/formulaes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(formulae.getId().intValue())));
    }

    @Test
    @Transactional
    public void getFormulae() throws Exception {
        // Initialize the database
        formulaeRepository.saveAndFlush(formulae);

        // Get the formulae
        restFormulaeMockMvc.perform(get("/api/formulaes/{id}", formulae.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(formulae.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFormulae() throws Exception {
        // Get the formulae
        restFormulaeMockMvc.perform(get("/api/formulaes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormulae() throws Exception {
        // Initialize the database
        formulaeRepository.saveAndFlush(formulae);

		int databaseSizeBeforeUpdate = formulaeRepository.findAll().size();

        // Update the formulae

        restFormulaeMockMvc.perform(put("/api/formulaes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(formulae)))
                .andExpect(status().isOk());

        // Validate the Formulae in the database
        List<Formulae> formulaes = formulaeRepository.findAll();
        assertThat(formulaes).hasSize(databaseSizeBeforeUpdate);
        Formulae testFormulae = formulaes.get(formulaes.size() - 1);
    }

    @Test
    @Transactional
    public void deleteFormulae() throws Exception {
        // Initialize the database
        formulaeRepository.saveAndFlush(formulae);

		int databaseSizeBeforeDelete = formulaeRepository.findAll().size();

        // Get the formulae
        restFormulaeMockMvc.perform(delete("/api/formulaes/{id}", formulae.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Formulae> formulaes = formulaeRepository.findAll();
        assertThat(formulaes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
