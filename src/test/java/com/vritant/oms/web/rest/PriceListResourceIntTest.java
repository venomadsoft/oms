package com.vritant.oms.web.rest;

import com.vritant.oms.Application;
import com.vritant.oms.domain.PriceList;
import com.vritant.oms.repository.PriceListRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PriceListResource REST controller.
 *
 * @see PriceListResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PriceListResourceIntTest {


    private static final LocalDate DEFAULT_WEF_DATE_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_WEF_DATE_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_WEF_DATE_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_WEF_DATE_TO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Inject
    private PriceListRepository priceListRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPriceListMockMvc;

    private PriceList priceList;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PriceListResource priceListResource = new PriceListResource();
        ReflectionTestUtils.setField(priceListResource, "priceListRepository", priceListRepository);
        this.restPriceListMockMvc = MockMvcBuilders.standaloneSetup(priceListResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        priceList = new PriceList();
        priceList.setWefDateFrom(DEFAULT_WEF_DATE_FROM);
        priceList.setWefDateTo(DEFAULT_WEF_DATE_TO);
        priceList.setActive(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPriceList() throws Exception {
        int databaseSizeBeforeCreate = priceListRepository.findAll().size();

        // Create the PriceList

        restPriceListMockMvc.perform(post("/api/priceLists")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(priceList)))
                .andExpect(status().isCreated());

        // Validate the PriceList in the database
        List<PriceList> priceLists = priceListRepository.findAll();
        assertThat(priceLists).hasSize(databaseSizeBeforeCreate + 1);
        PriceList testPriceList = priceLists.get(priceLists.size() - 1);
        assertThat(testPriceList.getWefDateFrom()).isEqualTo(DEFAULT_WEF_DATE_FROM);
        assertThat(testPriceList.getWefDateTo()).isEqualTo(DEFAULT_WEF_DATE_TO);
        assertThat(testPriceList.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void checkWefDateFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceListRepository.findAll().size();
        // set the field null
        priceList.setWefDateFrom(null);

        // Create the PriceList, which fails.

        restPriceListMockMvc.perform(post("/api/priceLists")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(priceList)))
                .andExpect(status().isBadRequest());

        List<PriceList> priceLists = priceListRepository.findAll();
        assertThat(priceLists).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWefDateToIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceListRepository.findAll().size();
        // set the field null
        priceList.setWefDateTo(null);

        // Create the PriceList, which fails.

        restPriceListMockMvc.perform(post("/api/priceLists")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(priceList)))
                .andExpect(status().isBadRequest());

        List<PriceList> priceLists = priceListRepository.findAll();
        assertThat(priceLists).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPriceLists() throws Exception {
        // Initialize the database
        priceListRepository.saveAndFlush(priceList);

        // Get all the priceLists
        restPriceListMockMvc.perform(get("/api/priceLists"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(priceList.getId().intValue())))
                .andExpect(jsonPath("$.[*].wefDateFrom").value(hasItem(DEFAULT_WEF_DATE_FROM.toString())))
                .andExpect(jsonPath("$.[*].wefDateTo").value(hasItem(DEFAULT_WEF_DATE_TO.toString())))
                .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getPriceList() throws Exception {
        // Initialize the database
        priceListRepository.saveAndFlush(priceList);

        // Get the priceList
        restPriceListMockMvc.perform(get("/api/priceLists/{id}", priceList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(priceList.getId().intValue()))
            .andExpect(jsonPath("$.wefDateFrom").value(DEFAULT_WEF_DATE_FROM.toString()))
            .andExpect(jsonPath("$.wefDateTo").value(DEFAULT_WEF_DATE_TO.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPriceList() throws Exception {
        // Get the priceList
        restPriceListMockMvc.perform(get("/api/priceLists/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePriceList() throws Exception {
        // Initialize the database
        priceListRepository.saveAndFlush(priceList);

		int databaseSizeBeforeUpdate = priceListRepository.findAll().size();

        // Update the priceList
        priceList.setWefDateFrom(UPDATED_WEF_DATE_FROM);
        priceList.setWefDateTo(UPDATED_WEF_DATE_TO);
        priceList.setActive(UPDATED_ACTIVE);

        restPriceListMockMvc.perform(put("/api/priceLists")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(priceList)))
                .andExpect(status().isOk());

        // Validate the PriceList in the database
        List<PriceList> priceLists = priceListRepository.findAll();
        assertThat(priceLists).hasSize(databaseSizeBeforeUpdate);
        PriceList testPriceList = priceLists.get(priceLists.size() - 1);
        assertThat(testPriceList.getWefDateFrom()).isEqualTo(UPDATED_WEF_DATE_FROM);
        assertThat(testPriceList.getWefDateTo()).isEqualTo(UPDATED_WEF_DATE_TO);
        assertThat(testPriceList.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void deletePriceList() throws Exception {
        // Initialize the database
        priceListRepository.saveAndFlush(priceList);

		int databaseSizeBeforeDelete = priceListRepository.findAll().size();

        // Get the priceList
        restPriceListMockMvc.perform(delete("/api/priceLists/{id}", priceList.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PriceList> priceLists = priceListRepository.findAll();
        assertThat(priceLists).hasSize(databaseSizeBeforeDelete - 1);
    }
}
