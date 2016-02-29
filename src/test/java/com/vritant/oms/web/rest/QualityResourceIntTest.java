package com.vritant.oms.web.rest;

import com.vritant.oms.Application;
import com.vritant.oms.domain.Quality;
import com.vritant.oms.repository.QualityRepository;

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
 * Test class for the QualityResource REST controller.
 *
 * @see QualityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class QualityResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAA";
    private static final String UPDATED_LABEL = "BBBBB";

    @Inject
    private QualityRepository qualityRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restQualityMockMvc;

    private Quality quality;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QualityResource qualityResource = new QualityResource();
        ReflectionTestUtils.setField(qualityResource, "qualityRepository", qualityRepository);
        this.restQualityMockMvc = MockMvcBuilders.standaloneSetup(qualityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        quality = new Quality();
        quality.setLabel(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createQuality() throws Exception {
        int databaseSizeBeforeCreate = qualityRepository.findAll().size();

        // Create the Quality

        restQualityMockMvc.perform(post("/api/qualitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quality)))
                .andExpect(status().isCreated());

        // Validate the Quality in the database
        List<Quality> qualitys = qualityRepository.findAll();
        assertThat(qualitys).hasSize(databaseSizeBeforeCreate + 1);
        Quality testQuality = qualitys.get(qualitys.size() - 1);
        assertThat(testQuality.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = qualityRepository.findAll().size();
        // set the field null
        quality.setLabel(null);

        // Create the Quality, which fails.

        restQualityMockMvc.perform(post("/api/qualitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quality)))
                .andExpect(status().isBadRequest());

        List<Quality> qualitys = qualityRepository.findAll();
        assertThat(qualitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQualitys() throws Exception {
        // Initialize the database
        qualityRepository.saveAndFlush(quality);

        // Get all the qualitys
        restQualityMockMvc.perform(get("/api/qualitys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(quality.getId().intValue())))
                .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }

    @Test
    @Transactional
    public void getQuality() throws Exception {
        // Initialize the database
        qualityRepository.saveAndFlush(quality);

        // Get the quality
        restQualityMockMvc.perform(get("/api/qualitys/{id}", quality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(quality.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQuality() throws Exception {
        // Get the quality
        restQualityMockMvc.perform(get("/api/qualitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuality() throws Exception {
        // Initialize the database
        qualityRepository.saveAndFlush(quality);

		int databaseSizeBeforeUpdate = qualityRepository.findAll().size();

        // Update the quality
        quality.setLabel(UPDATED_LABEL);

        restQualityMockMvc.perform(put("/api/qualitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quality)))
                .andExpect(status().isOk());

        // Validate the Quality in the database
        List<Quality> qualitys = qualityRepository.findAll();
        assertThat(qualitys).hasSize(databaseSizeBeforeUpdate);
        Quality testQuality = qualitys.get(qualitys.size() - 1);
        assertThat(testQuality.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void deleteQuality() throws Exception {
        // Initialize the database
        qualityRepository.saveAndFlush(quality);

		int databaseSizeBeforeDelete = qualityRepository.findAll().size();

        // Get the quality
        restQualityMockMvc.perform(delete("/api/qualitys/{id}", quality.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Quality> qualitys = qualityRepository.findAll();
        assertThat(qualitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
