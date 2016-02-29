package com.vritant.oms.web.rest;

import com.vritant.oms.Application;
import com.vritant.oms.domain.NoteSet;
import com.vritant.oms.repository.NoteSetRepository;

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
 * Test class for the NoteSetResource REST controller.
 *
 * @see NoteSetResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class NoteSetResourceIntTest {


    @Inject
    private NoteSetRepository noteSetRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restNoteSetMockMvc;

    private NoteSet noteSet;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NoteSetResource noteSetResource = new NoteSetResource();
        ReflectionTestUtils.setField(noteSetResource, "noteSetRepository", noteSetRepository);
        this.restNoteSetMockMvc = MockMvcBuilders.standaloneSetup(noteSetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        noteSet = new NoteSet();
    }

    @Test
    @Transactional
    public void createNoteSet() throws Exception {
        int databaseSizeBeforeCreate = noteSetRepository.findAll().size();

        // Create the NoteSet

        restNoteSetMockMvc.perform(post("/api/noteSets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(noteSet)))
                .andExpect(status().isCreated());

        // Validate the NoteSet in the database
        List<NoteSet> noteSets = noteSetRepository.findAll();
        assertThat(noteSets).hasSize(databaseSizeBeforeCreate + 1);
        NoteSet testNoteSet = noteSets.get(noteSets.size() - 1);
    }

    @Test
    @Transactional
    public void getAllNoteSets() throws Exception {
        // Initialize the database
        noteSetRepository.saveAndFlush(noteSet);

        // Get all the noteSets
        restNoteSetMockMvc.perform(get("/api/noteSets?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(noteSet.getId().intValue())));
    }

    @Test
    @Transactional
    public void getNoteSet() throws Exception {
        // Initialize the database
        noteSetRepository.saveAndFlush(noteSet);

        // Get the noteSet
        restNoteSetMockMvc.perform(get("/api/noteSets/{id}", noteSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(noteSet.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNoteSet() throws Exception {
        // Get the noteSet
        restNoteSetMockMvc.perform(get("/api/noteSets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNoteSet() throws Exception {
        // Initialize the database
        noteSetRepository.saveAndFlush(noteSet);

		int databaseSizeBeforeUpdate = noteSetRepository.findAll().size();

        // Update the noteSet

        restNoteSetMockMvc.perform(put("/api/noteSets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(noteSet)))
                .andExpect(status().isOk());

        // Validate the NoteSet in the database
        List<NoteSet> noteSets = noteSetRepository.findAll();
        assertThat(noteSets).hasSize(databaseSizeBeforeUpdate);
        NoteSet testNoteSet = noteSets.get(noteSets.size() - 1);
    }

    @Test
    @Transactional
    public void deleteNoteSet() throws Exception {
        // Initialize the database
        noteSetRepository.saveAndFlush(noteSet);

		int databaseSizeBeforeDelete = noteSetRepository.findAll().size();

        // Get the noteSet
        restNoteSetMockMvc.perform(delete("/api/noteSets/{id}", noteSet.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<NoteSet> noteSets = noteSetRepository.findAll();
        assertThat(noteSets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
