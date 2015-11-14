package com.vritant.oms.web.rest;

import com.vritant.oms.Application;
import com.vritant.oms.domain.NoteType;
import com.vritant.oms.repository.NoteTypeRepository;

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
 * Test class for the NoteTypeResource REST controller.
 *
 * @see NoteTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class NoteTypeResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAA";
    private static final String UPDATED_LABEL = "BBBBB";

    @Inject
    private NoteTypeRepository noteTypeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restNoteTypeMockMvc;

    private NoteType noteType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NoteTypeResource noteTypeResource = new NoteTypeResource();
        ReflectionTestUtils.setField(noteTypeResource, "noteTypeRepository", noteTypeRepository);
        this.restNoteTypeMockMvc = MockMvcBuilders.standaloneSetup(noteTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        noteType = new NoteType();
        noteType.setLabel(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createNoteType() throws Exception {
        int databaseSizeBeforeCreate = noteTypeRepository.findAll().size();

        // Create the NoteType

        restNoteTypeMockMvc.perform(post("/api/noteTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(noteType)))
                .andExpect(status().isCreated());

        // Validate the NoteType in the database
        List<NoteType> noteTypes = noteTypeRepository.findAll();
        assertThat(noteTypes).hasSize(databaseSizeBeforeCreate + 1);
        NoteType testNoteType = noteTypes.get(noteTypes.size() - 1);
        assertThat(testNoteType.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = noteTypeRepository.findAll().size();
        // set the field null
        noteType.setLabel(null);

        // Create the NoteType, which fails.

        restNoteTypeMockMvc.perform(post("/api/noteTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(noteType)))
                .andExpect(status().isBadRequest());

        List<NoteType> noteTypes = noteTypeRepository.findAll();
        assertThat(noteTypes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNoteTypes() throws Exception {
        // Initialize the database
        noteTypeRepository.saveAndFlush(noteType);

        // Get all the noteTypes
        restNoteTypeMockMvc.perform(get("/api/noteTypes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(noteType.getId().intValue())))
                .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }

    @Test
    @Transactional
    public void getNoteType() throws Exception {
        // Initialize the database
        noteTypeRepository.saveAndFlush(noteType);

        // Get the noteType
        restNoteTypeMockMvc.perform(get("/api/noteTypes/{id}", noteType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(noteType.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNoteType() throws Exception {
        // Get the noteType
        restNoteTypeMockMvc.perform(get("/api/noteTypes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNoteType() throws Exception {
        // Initialize the database
        noteTypeRepository.saveAndFlush(noteType);

		int databaseSizeBeforeUpdate = noteTypeRepository.findAll().size();

        // Update the noteType
        noteType.setLabel(UPDATED_LABEL);

        restNoteTypeMockMvc.perform(put("/api/noteTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(noteType)))
                .andExpect(status().isOk());

        // Validate the NoteType in the database
        List<NoteType> noteTypes = noteTypeRepository.findAll();
        assertThat(noteTypes).hasSize(databaseSizeBeforeUpdate);
        NoteType testNoteType = noteTypes.get(noteTypes.size() - 1);
        assertThat(testNoteType.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void deleteNoteType() throws Exception {
        // Initialize the database
        noteTypeRepository.saveAndFlush(noteType);

		int databaseSizeBeforeDelete = noteTypeRepository.findAll().size();

        // Get the noteType
        restNoteTypeMockMvc.perform(delete("/api/noteTypes/{id}", noteType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<NoteType> noteTypes = noteTypeRepository.findAll();
        assertThat(noteTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
