package com.vritant.oms.web.rest;

import com.vritant.oms.Application;
import com.vritant.oms.domain.AddressLine;
import com.vritant.oms.repository.AddressLineRepository;

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
 * Test class for the AddressLineResource REST controller.
 *
 * @see AddressLineResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AddressLineResourceIntTest {

    private static final String DEFAULT_TEXT = "AAAAA";
    private static final String UPDATED_TEXT = "BBBBB";

    @Inject
    private AddressLineRepository addressLineRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAddressLineMockMvc;

    private AddressLine addressLine;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AddressLineResource addressLineResource = new AddressLineResource();
        ReflectionTestUtils.setField(addressLineResource, "addressLineRepository", addressLineRepository);
        this.restAddressLineMockMvc = MockMvcBuilders.standaloneSetup(addressLineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        addressLine = new AddressLine();
        addressLine.setText(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    public void createAddressLine() throws Exception {
        int databaseSizeBeforeCreate = addressLineRepository.findAll().size();

        // Create the AddressLine

        restAddressLineMockMvc.perform(post("/api/addressLines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(addressLine)))
                .andExpect(status().isCreated());

        // Validate the AddressLine in the database
        List<AddressLine> addressLines = addressLineRepository.findAll();
        assertThat(addressLines).hasSize(databaseSizeBeforeCreate + 1);
        AddressLine testAddressLine = addressLines.get(addressLines.size() - 1);
        assertThat(testAddressLine.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressLineRepository.findAll().size();
        // set the field null
        addressLine.setText(null);

        // Create the AddressLine, which fails.

        restAddressLineMockMvc.perform(post("/api/addressLines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(addressLine)))
                .andExpect(status().isBadRequest());

        List<AddressLine> addressLines = addressLineRepository.findAll();
        assertThat(addressLines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAddressLines() throws Exception {
        // Initialize the database
        addressLineRepository.saveAndFlush(addressLine);

        // Get all the addressLines
        restAddressLineMockMvc.perform(get("/api/addressLines"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(addressLine.getId().intValue())))
                .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())));
    }

    @Test
    @Transactional
    public void getAddressLine() throws Exception {
        // Initialize the database
        addressLineRepository.saveAndFlush(addressLine);

        // Get the addressLine
        restAddressLineMockMvc.perform(get("/api/addressLines/{id}", addressLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(addressLine.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAddressLine() throws Exception {
        // Get the addressLine
        restAddressLineMockMvc.perform(get("/api/addressLines/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddressLine() throws Exception {
        // Initialize the database
        addressLineRepository.saveAndFlush(addressLine);

		int databaseSizeBeforeUpdate = addressLineRepository.findAll().size();

        // Update the addressLine
        addressLine.setText(UPDATED_TEXT);

        restAddressLineMockMvc.perform(put("/api/addressLines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(addressLine)))
                .andExpect(status().isOk());

        // Validate the AddressLine in the database
        List<AddressLine> addressLines = addressLineRepository.findAll();
        assertThat(addressLines).hasSize(databaseSizeBeforeUpdate);
        AddressLine testAddressLine = addressLines.get(addressLines.size() - 1);
        assertThat(testAddressLine.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void deleteAddressLine() throws Exception {
        // Initialize the database
        addressLineRepository.saveAndFlush(addressLine);

		int databaseSizeBeforeDelete = addressLineRepository.findAll().size();

        // Get the addressLine
        restAddressLineMockMvc.perform(delete("/api/addressLines/{id}", addressLine.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AddressLine> addressLines = addressLineRepository.findAll();
        assertThat(addressLines).hasSize(databaseSizeBeforeDelete - 1);
    }
}
