package com.vritant.oms.web.rest;

import com.vritant.oms.Application;
import com.vritant.oms.domain.Formula;
import com.vritant.oms.repository.FormulaRepository;

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
 * Test class for the FormulaResource REST controller.
 *
 * @see FormulaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FormulaResourceIntTest {

    private static final String DEFAULT_OPERATOR = "A";
    private static final String UPDATED_OPERATOR = "B";

    private static final Float DEFAULT_OPERAND = 1F;
    private static final Float UPDATED_OPERAND = 2F;

    @Inject
    private FormulaRepository formulaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFormulaMockMvc;

    private Formula formula;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FormulaResource formulaResource = new FormulaResource();
        ReflectionTestUtils.setField(formulaResource, "formulaRepository", formulaRepository);
        this.restFormulaMockMvc = MockMvcBuilders.standaloneSetup(formulaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        formula = new Formula();
        formula.setOperator(DEFAULT_OPERATOR);
        formula.setOperand(DEFAULT_OPERAND);
    }

    @Test
    @Transactional
    public void createFormula() throws Exception {
        int databaseSizeBeforeCreate = formulaRepository.findAll().size();

        // Create the Formula

        restFormulaMockMvc.perform(post("/api/formulas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(formula)))
                .andExpect(status().isCreated());

        // Validate the Formula in the database
        List<Formula> formulas = formulaRepository.findAll();
        assertThat(formulas).hasSize(databaseSizeBeforeCreate + 1);
        Formula testFormula = formulas.get(formulas.size() - 1);
        assertThat(testFormula.getOperator()).isEqualTo(DEFAULT_OPERATOR);
        assertThat(testFormula.getOperand()).isEqualTo(DEFAULT_OPERAND);
    }

    @Test
    @Transactional
    public void checkOperatorIsRequired() throws Exception {
        int databaseSizeBeforeTest = formulaRepository.findAll().size();
        // set the field null
        formula.setOperator(null);

        // Create the Formula, which fails.

        restFormulaMockMvc.perform(post("/api/formulas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(formula)))
                .andExpect(status().isBadRequest());

        List<Formula> formulas = formulaRepository.findAll();
        assertThat(formulas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOperandIsRequired() throws Exception {
        int databaseSizeBeforeTest = formulaRepository.findAll().size();
        // set the field null
        formula.setOperand(null);

        // Create the Formula, which fails.

        restFormulaMockMvc.perform(post("/api/formulas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(formula)))
                .andExpect(status().isBadRequest());

        List<Formula> formulas = formulaRepository.findAll();
        assertThat(formulas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFormulas() throws Exception {
        // Initialize the database
        formulaRepository.saveAndFlush(formula);

        // Get all the formulas
        restFormulaMockMvc.perform(get("/api/formulas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(formula.getId().intValue())))
                .andExpect(jsonPath("$.[*].operator").value(hasItem(DEFAULT_OPERATOR.toString())))
                .andExpect(jsonPath("$.[*].operand").value(hasItem(DEFAULT_OPERAND.doubleValue())));
    }

    @Test
    @Transactional
    public void getFormula() throws Exception {
        // Initialize the database
        formulaRepository.saveAndFlush(formula);

        // Get the formula
        restFormulaMockMvc.perform(get("/api/formulas/{id}", formula.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(formula.getId().intValue()))
            .andExpect(jsonPath("$.operator").value(DEFAULT_OPERATOR.toString()))
            .andExpect(jsonPath("$.operand").value(DEFAULT_OPERAND.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFormula() throws Exception {
        // Get the formula
        restFormulaMockMvc.perform(get("/api/formulas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormula() throws Exception {
        // Initialize the database
        formulaRepository.saveAndFlush(formula);

		int databaseSizeBeforeUpdate = formulaRepository.findAll().size();

        // Update the formula
        formula.setOperator(UPDATED_OPERATOR);
        formula.setOperand(UPDATED_OPERAND);

        restFormulaMockMvc.perform(put("/api/formulas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(formula)))
                .andExpect(status().isOk());

        // Validate the Formula in the database
        List<Formula> formulas = formulaRepository.findAll();
        assertThat(formulas).hasSize(databaseSizeBeforeUpdate);
        Formula testFormula = formulas.get(formulas.size() - 1);
        assertThat(testFormula.getOperator()).isEqualTo(UPDATED_OPERATOR);
        assertThat(testFormula.getOperand()).isEqualTo(UPDATED_OPERAND);
    }

    @Test
    @Transactional
    public void deleteFormula() throws Exception {
        // Initialize the database
        formulaRepository.saveAndFlush(formula);

		int databaseSizeBeforeDelete = formulaRepository.findAll().size();

        // Get the formula
        restFormulaMockMvc.perform(delete("/api/formulas/{id}", formula.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Formula> formulas = formulaRepository.findAll();
        assertThat(formulas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
