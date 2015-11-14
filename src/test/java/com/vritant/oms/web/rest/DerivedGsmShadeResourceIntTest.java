package com.vritant.oms.web.rest;

import com.vritant.oms.Application;
import com.vritant.oms.domain.DerivedGsmShade;
import com.vritant.oms.repository.DerivedGsmShadeRepository;

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
 * Test class for the DerivedGsmShadeResource REST controller.
 *
 * @see DerivedGsmShadeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DerivedGsmShadeResourceIntTest {


    private static final Integer DEFAULT_MIN_GSM = 1;
    private static final Integer UPDATED_MIN_GSM = 2;

    private static final Integer DEFAULT_MAX_GSM = 1;
    private static final Integer UPDATED_MAX_GSM = 2;
    private static final String DEFAULT_SHADE = "AAAAA";
    private static final String UPDATED_SHADE = "BBBBB";

    @Inject
    private DerivedGsmShadeRepository derivedGsmShadeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDerivedGsmShadeMockMvc;

    private DerivedGsmShade derivedGsmShade;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DerivedGsmShadeResource derivedGsmShadeResource = new DerivedGsmShadeResource();
        ReflectionTestUtils.setField(derivedGsmShadeResource, "derivedGsmShadeRepository", derivedGsmShadeRepository);
        this.restDerivedGsmShadeMockMvc = MockMvcBuilders.standaloneSetup(derivedGsmShadeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        derivedGsmShade = new DerivedGsmShade();
        derivedGsmShade.setMinGsm(DEFAULT_MIN_GSM);
        derivedGsmShade.setMaxGsm(DEFAULT_MAX_GSM);
        derivedGsmShade.setShade(DEFAULT_SHADE);
    }

    @Test
    @Transactional
    public void createDerivedGsmShade() throws Exception {
        int databaseSizeBeforeCreate = derivedGsmShadeRepository.findAll().size();

        // Create the DerivedGsmShade

        restDerivedGsmShadeMockMvc.perform(post("/api/derivedGsmShades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(derivedGsmShade)))
                .andExpect(status().isCreated());

        // Validate the DerivedGsmShade in the database
        List<DerivedGsmShade> derivedGsmShades = derivedGsmShadeRepository.findAll();
        assertThat(derivedGsmShades).hasSize(databaseSizeBeforeCreate + 1);
        DerivedGsmShade testDerivedGsmShade = derivedGsmShades.get(derivedGsmShades.size() - 1);
        assertThat(testDerivedGsmShade.getMinGsm()).isEqualTo(DEFAULT_MIN_GSM);
        assertThat(testDerivedGsmShade.getMaxGsm()).isEqualTo(DEFAULT_MAX_GSM);
        assertThat(testDerivedGsmShade.getShade()).isEqualTo(DEFAULT_SHADE);
    }

    @Test
    @Transactional
    public void checkMinGsmIsRequired() throws Exception {
        int databaseSizeBeforeTest = derivedGsmShadeRepository.findAll().size();
        // set the field null
        derivedGsmShade.setMinGsm(null);

        // Create the DerivedGsmShade, which fails.

        restDerivedGsmShadeMockMvc.perform(post("/api/derivedGsmShades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(derivedGsmShade)))
                .andExpect(status().isBadRequest());

        List<DerivedGsmShade> derivedGsmShades = derivedGsmShadeRepository.findAll();
        assertThat(derivedGsmShades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaxGsmIsRequired() throws Exception {
        int databaseSizeBeforeTest = derivedGsmShadeRepository.findAll().size();
        // set the field null
        derivedGsmShade.setMaxGsm(null);

        // Create the DerivedGsmShade, which fails.

        restDerivedGsmShadeMockMvc.perform(post("/api/derivedGsmShades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(derivedGsmShade)))
                .andExpect(status().isBadRequest());

        List<DerivedGsmShade> derivedGsmShades = derivedGsmShadeRepository.findAll();
        assertThat(derivedGsmShades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = derivedGsmShadeRepository.findAll().size();
        // set the field null
        derivedGsmShade.setShade(null);

        // Create the DerivedGsmShade, which fails.

        restDerivedGsmShadeMockMvc.perform(post("/api/derivedGsmShades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(derivedGsmShade)))
                .andExpect(status().isBadRequest());

        List<DerivedGsmShade> derivedGsmShades = derivedGsmShadeRepository.findAll();
        assertThat(derivedGsmShades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDerivedGsmShades() throws Exception {
        // Initialize the database
        derivedGsmShadeRepository.saveAndFlush(derivedGsmShade);

        // Get all the derivedGsmShades
        restDerivedGsmShadeMockMvc.perform(get("/api/derivedGsmShades"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(derivedGsmShade.getId().intValue())))
                .andExpect(jsonPath("$.[*].minGsm").value(hasItem(DEFAULT_MIN_GSM)))
                .andExpect(jsonPath("$.[*].maxGsm").value(hasItem(DEFAULT_MAX_GSM)))
                .andExpect(jsonPath("$.[*].shade").value(hasItem(DEFAULT_SHADE.toString())));
    }

    @Test
    @Transactional
    public void getDerivedGsmShade() throws Exception {
        // Initialize the database
        derivedGsmShadeRepository.saveAndFlush(derivedGsmShade);

        // Get the derivedGsmShade
        restDerivedGsmShadeMockMvc.perform(get("/api/derivedGsmShades/{id}", derivedGsmShade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(derivedGsmShade.getId().intValue()))
            .andExpect(jsonPath("$.minGsm").value(DEFAULT_MIN_GSM))
            .andExpect(jsonPath("$.maxGsm").value(DEFAULT_MAX_GSM))
            .andExpect(jsonPath("$.shade").value(DEFAULT_SHADE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDerivedGsmShade() throws Exception {
        // Get the derivedGsmShade
        restDerivedGsmShadeMockMvc.perform(get("/api/derivedGsmShades/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDerivedGsmShade() throws Exception {
        // Initialize the database
        derivedGsmShadeRepository.saveAndFlush(derivedGsmShade);

		int databaseSizeBeforeUpdate = derivedGsmShadeRepository.findAll().size();

        // Update the derivedGsmShade
        derivedGsmShade.setMinGsm(UPDATED_MIN_GSM);
        derivedGsmShade.setMaxGsm(UPDATED_MAX_GSM);
        derivedGsmShade.setShade(UPDATED_SHADE);

        restDerivedGsmShadeMockMvc.perform(put("/api/derivedGsmShades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(derivedGsmShade)))
                .andExpect(status().isOk());

        // Validate the DerivedGsmShade in the database
        List<DerivedGsmShade> derivedGsmShades = derivedGsmShadeRepository.findAll();
        assertThat(derivedGsmShades).hasSize(databaseSizeBeforeUpdate);
        DerivedGsmShade testDerivedGsmShade = derivedGsmShades.get(derivedGsmShades.size() - 1);
        assertThat(testDerivedGsmShade.getMinGsm()).isEqualTo(UPDATED_MIN_GSM);
        assertThat(testDerivedGsmShade.getMaxGsm()).isEqualTo(UPDATED_MAX_GSM);
        assertThat(testDerivedGsmShade.getShade()).isEqualTo(UPDATED_SHADE);
    }

    @Test
    @Transactional
    public void deleteDerivedGsmShade() throws Exception {
        // Initialize the database
        derivedGsmShadeRepository.saveAndFlush(derivedGsmShade);

		int databaseSizeBeforeDelete = derivedGsmShadeRepository.findAll().size();

        // Get the derivedGsmShade
        restDerivedGsmShadeMockMvc.perform(delete("/api/derivedGsmShades/{id}", derivedGsmShade.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DerivedGsmShade> derivedGsmShades = derivedGsmShadeRepository.findAll();
        assertThat(derivedGsmShades).hasSize(databaseSizeBeforeDelete - 1);
    }
}
