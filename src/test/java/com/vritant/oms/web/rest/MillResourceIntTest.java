package com.vritant.oms.web.rest;

import com.vritant.oms.Application;
import com.vritant.oms.domain.Mill;
import com.vritant.oms.repository.MillRepository;

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
 * Test class for the MillResource REST controller.
 *
 * @see MillResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MillResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private MillRepository millRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMillMockMvc;

    private Mill mill;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MillResource millResource = new MillResource();
        ReflectionTestUtils.setField(millResource, "millRepository", millRepository);
        this.restMillMockMvc = MockMvcBuilders.standaloneSetup(millResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        mill = new Mill();
        mill.setCode(DEFAULT_CODE);
        mill.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMill() throws Exception {
        int databaseSizeBeforeCreate = millRepository.findAll().size();

        // Create the Mill

        restMillMockMvc.perform(post("/api/mills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mill)))
                .andExpect(status().isCreated());

        // Validate the Mill in the database
        List<Mill> mills = millRepository.findAll();
        assertThat(mills).hasSize(databaseSizeBeforeCreate + 1);
        Mill testMill = mills.get(mills.size() - 1);
        assertThat(testMill.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMill.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = millRepository.findAll().size();
        // set the field null
        mill.setCode(null);

        // Create the Mill, which fails.

        restMillMockMvc.perform(post("/api/mills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mill)))
                .andExpect(status().isBadRequest());

        List<Mill> mills = millRepository.findAll();
        assertThat(mills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = millRepository.findAll().size();
        // set the field null
        mill.setName(null);

        // Create the Mill, which fails.

        restMillMockMvc.perform(post("/api/mills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mill)))
                .andExpect(status().isBadRequest());

        List<Mill> mills = millRepository.findAll();
        assertThat(mills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMills() throws Exception {
        // Initialize the database
        millRepository.saveAndFlush(mill);

        // Get all the mills
        restMillMockMvc.perform(get("/api/mills?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mill.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getMill() throws Exception {
        // Initialize the database
        millRepository.saveAndFlush(mill);

        // Get the mill
        restMillMockMvc.perform(get("/api/mills/{id}", mill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(mill.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMill() throws Exception {
        // Get the mill
        restMillMockMvc.perform(get("/api/mills/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMill() throws Exception {
        // Initialize the database
        millRepository.saveAndFlush(mill);

		int databaseSizeBeforeUpdate = millRepository.findAll().size();

        // Update the mill
        mill.setCode(UPDATED_CODE);
        mill.setName(UPDATED_NAME);

        restMillMockMvc.perform(put("/api/mills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mill)))
                .andExpect(status().isOk());

        // Validate the Mill in the database
        List<Mill> mills = millRepository.findAll();
        assertThat(mills).hasSize(databaseSizeBeforeUpdate);
        Mill testMill = mills.get(mills.size() - 1);
        assertThat(testMill.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMill.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteMill() throws Exception {
        // Initialize the database
        millRepository.saveAndFlush(mill);

		int databaseSizeBeforeDelete = millRepository.findAll().size();

        // Get the mill
        restMillMockMvc.perform(delete("/api/mills/{id}", mill.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Mill> mills = millRepository.findAll();
        assertThat(mills).hasSize(databaseSizeBeforeDelete - 1);
    }
}
