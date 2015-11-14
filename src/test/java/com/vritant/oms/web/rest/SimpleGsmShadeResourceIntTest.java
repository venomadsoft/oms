package com.vritant.oms.web.rest;

import com.vritant.oms.Application;
import com.vritant.oms.domain.SimpleGsmShade;
import com.vritant.oms.repository.SimpleGsmShadeRepository;

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
 * Test class for the SimpleGsmShadeResource REST controller.
 *
 * @see SimpleGsmShadeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SimpleGsmShadeResourceIntTest {


    private static final Integer DEFAULT_MIN_GSM = 1;
    private static final Integer UPDATED_MIN_GSM = 2;

    private static final Integer DEFAULT_MAX_GSM = 1;
    private static final Integer UPDATED_MAX_GSM = 2;
    private static final String DEFAULT_SHADE = "AAAAA";
    private static final String UPDATED_SHADE = "BBBBB";

    @Inject
    private SimpleGsmShadeRepository simpleGsmShadeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSimpleGsmShadeMockMvc;

    private SimpleGsmShade simpleGsmShade;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SimpleGsmShadeResource simpleGsmShadeResource = new SimpleGsmShadeResource();
        ReflectionTestUtils.setField(simpleGsmShadeResource, "simpleGsmShadeRepository", simpleGsmShadeRepository);
        this.restSimpleGsmShadeMockMvc = MockMvcBuilders.standaloneSetup(simpleGsmShadeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        simpleGsmShade = new SimpleGsmShade();
        simpleGsmShade.setMinGsm(DEFAULT_MIN_GSM);
        simpleGsmShade.setMaxGsm(DEFAULT_MAX_GSM);
        simpleGsmShade.setShade(DEFAULT_SHADE);
    }

    @Test
    @Transactional
    public void createSimpleGsmShade() throws Exception {
        int databaseSizeBeforeCreate = simpleGsmShadeRepository.findAll().size();

        // Create the SimpleGsmShade

        restSimpleGsmShadeMockMvc.perform(post("/api/simpleGsmShades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(simpleGsmShade)))
                .andExpect(status().isCreated());

        // Validate the SimpleGsmShade in the database
        List<SimpleGsmShade> simpleGsmShades = simpleGsmShadeRepository.findAll();
        assertThat(simpleGsmShades).hasSize(databaseSizeBeforeCreate + 1);
        SimpleGsmShade testSimpleGsmShade = simpleGsmShades.get(simpleGsmShades.size() - 1);
        assertThat(testSimpleGsmShade.getMinGsm()).isEqualTo(DEFAULT_MIN_GSM);
        assertThat(testSimpleGsmShade.getMaxGsm()).isEqualTo(DEFAULT_MAX_GSM);
        assertThat(testSimpleGsmShade.getShade()).isEqualTo(DEFAULT_SHADE);
    }

    @Test
    @Transactional
    public void checkMinGsmIsRequired() throws Exception {
        int databaseSizeBeforeTest = simpleGsmShadeRepository.findAll().size();
        // set the field null
        simpleGsmShade.setMinGsm(null);

        // Create the SimpleGsmShade, which fails.

        restSimpleGsmShadeMockMvc.perform(post("/api/simpleGsmShades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(simpleGsmShade)))
                .andExpect(status().isBadRequest());

        List<SimpleGsmShade> simpleGsmShades = simpleGsmShadeRepository.findAll();
        assertThat(simpleGsmShades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaxGsmIsRequired() throws Exception {
        int databaseSizeBeforeTest = simpleGsmShadeRepository.findAll().size();
        // set the field null
        simpleGsmShade.setMaxGsm(null);

        // Create the SimpleGsmShade, which fails.

        restSimpleGsmShadeMockMvc.perform(post("/api/simpleGsmShades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(simpleGsmShade)))
                .andExpect(status().isBadRequest());

        List<SimpleGsmShade> simpleGsmShades = simpleGsmShadeRepository.findAll();
        assertThat(simpleGsmShades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = simpleGsmShadeRepository.findAll().size();
        // set the field null
        simpleGsmShade.setShade(null);

        // Create the SimpleGsmShade, which fails.

        restSimpleGsmShadeMockMvc.perform(post("/api/simpleGsmShades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(simpleGsmShade)))
                .andExpect(status().isBadRequest());

        List<SimpleGsmShade> simpleGsmShades = simpleGsmShadeRepository.findAll();
        assertThat(simpleGsmShades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSimpleGsmShades() throws Exception {
        // Initialize the database
        simpleGsmShadeRepository.saveAndFlush(simpleGsmShade);

        // Get all the simpleGsmShades
        restSimpleGsmShadeMockMvc.perform(get("/api/simpleGsmShades"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(simpleGsmShade.getId().intValue())))
                .andExpect(jsonPath("$.[*].minGsm").value(hasItem(DEFAULT_MIN_GSM)))
                .andExpect(jsonPath("$.[*].maxGsm").value(hasItem(DEFAULT_MAX_GSM)))
                .andExpect(jsonPath("$.[*].shade").value(hasItem(DEFAULT_SHADE.toString())));
    }

    @Test
    @Transactional
    public void getSimpleGsmShade() throws Exception {
        // Initialize the database
        simpleGsmShadeRepository.saveAndFlush(simpleGsmShade);

        // Get the simpleGsmShade
        restSimpleGsmShadeMockMvc.perform(get("/api/simpleGsmShades/{id}", simpleGsmShade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(simpleGsmShade.getId().intValue()))
            .andExpect(jsonPath("$.minGsm").value(DEFAULT_MIN_GSM))
            .andExpect(jsonPath("$.maxGsm").value(DEFAULT_MAX_GSM))
            .andExpect(jsonPath("$.shade").value(DEFAULT_SHADE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSimpleGsmShade() throws Exception {
        // Get the simpleGsmShade
        restSimpleGsmShadeMockMvc.perform(get("/api/simpleGsmShades/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSimpleGsmShade() throws Exception {
        // Initialize the database
        simpleGsmShadeRepository.saveAndFlush(simpleGsmShade);

		int databaseSizeBeforeUpdate = simpleGsmShadeRepository.findAll().size();

        // Update the simpleGsmShade
        simpleGsmShade.setMinGsm(UPDATED_MIN_GSM);
        simpleGsmShade.setMaxGsm(UPDATED_MAX_GSM);
        simpleGsmShade.setShade(UPDATED_SHADE);

        restSimpleGsmShadeMockMvc.perform(put("/api/simpleGsmShades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(simpleGsmShade)))
                .andExpect(status().isOk());

        // Validate the SimpleGsmShade in the database
        List<SimpleGsmShade> simpleGsmShades = simpleGsmShadeRepository.findAll();
        assertThat(simpleGsmShades).hasSize(databaseSizeBeforeUpdate);
        SimpleGsmShade testSimpleGsmShade = simpleGsmShades.get(simpleGsmShades.size() - 1);
        assertThat(testSimpleGsmShade.getMinGsm()).isEqualTo(UPDATED_MIN_GSM);
        assertThat(testSimpleGsmShade.getMaxGsm()).isEqualTo(UPDATED_MAX_GSM);
        assertThat(testSimpleGsmShade.getShade()).isEqualTo(UPDATED_SHADE);
    }

    @Test
    @Transactional
    public void deleteSimpleGsmShade() throws Exception {
        // Initialize the database
        simpleGsmShadeRepository.saveAndFlush(simpleGsmShade);

		int databaseSizeBeforeDelete = simpleGsmShadeRepository.findAll().size();

        // Get the simpleGsmShade
        restSimpleGsmShadeMockMvc.perform(delete("/api/simpleGsmShades/{id}", simpleGsmShade.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SimpleGsmShade> simpleGsmShades = simpleGsmShadeRepository.findAll();
        assertThat(simpleGsmShades).hasSize(databaseSizeBeforeDelete - 1);
    }
}
