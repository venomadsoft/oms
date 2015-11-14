package com.vritant.oms.web.rest;

import com.vritant.oms.Application;
import com.vritant.oms.domain.Addresses;
import com.vritant.oms.repository.AddressesRepository;

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
 * Test class for the AddressesResource REST controller.
 *
 * @see AddressesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AddressesResourceIntTest {


    @Inject
    private AddressesRepository addressesRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAddressesMockMvc;

    private Addresses addresses;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AddressesResource addressesResource = new AddressesResource();
        ReflectionTestUtils.setField(addressesResource, "addressesRepository", addressesRepository);
        this.restAddressesMockMvc = MockMvcBuilders.standaloneSetup(addressesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        addresses = new Addresses();
    }

    @Test
    @Transactional
    public void createAddresses() throws Exception {
        int databaseSizeBeforeCreate = addressesRepository.findAll().size();

        // Create the Addresses

        restAddressesMockMvc.perform(post("/api/addressess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(addresses)))
                .andExpect(status().isCreated());

        // Validate the Addresses in the database
        List<Addresses> addressess = addressesRepository.findAll();
        assertThat(addressess).hasSize(databaseSizeBeforeCreate + 1);
        Addresses testAddresses = addressess.get(addressess.size() - 1);
    }

    @Test
    @Transactional
    public void getAllAddressess() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressess
        restAddressesMockMvc.perform(get("/api/addressess"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(addresses.getId().intValue())));
    }

    @Test
    @Transactional
    public void getAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get the addresses
        restAddressesMockMvc.perform(get("/api/addressess/{id}", addresses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(addresses.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAddresses() throws Exception {
        // Get the addresses
        restAddressesMockMvc.perform(get("/api/addressess/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

		int databaseSizeBeforeUpdate = addressesRepository.findAll().size();

        // Update the addresses

        restAddressesMockMvc.perform(put("/api/addressess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(addresses)))
                .andExpect(status().isOk());

        // Validate the Addresses in the database
        List<Addresses> addressess = addressesRepository.findAll();
        assertThat(addressess).hasSize(databaseSizeBeforeUpdate);
        Addresses testAddresses = addressess.get(addressess.size() - 1);
    }

    @Test
    @Transactional
    public void deleteAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

		int databaseSizeBeforeDelete = addressesRepository.findAll().size();

        // Get the addresses
        restAddressesMockMvc.perform(delete("/api/addressess/{id}", addresses.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Addresses> addressess = addressesRepository.findAll();
        assertThat(addressess).hasSize(databaseSizeBeforeDelete - 1);
    }
}
