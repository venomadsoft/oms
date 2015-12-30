package com.vritant.oms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vritant.oms.domain.Quality;
import com.vritant.oms.repository.QualityRepository;
import com.vritant.oms.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Quality.
 */
@RestController
@RequestMapping("/api")
public class QualityResource {

    private final Logger log = LoggerFactory.getLogger(QualityResource.class);

    @Inject
    private QualityRepository qualityRepository;

    /**
     * POST  /qualitys -> Create a new quality.
     */
    @RequestMapping(value = "/qualitys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Quality> createQuality(@Valid @RequestBody Quality quality) throws URISyntaxException {
        log.debug("REST request to save Quality : {}", quality);
        if (quality.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new quality cannot already have an ID").body(null);
        }
        Quality result = qualityRepository.save(quality);
        return ResponseEntity.created(new URI("/api/qualitys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("quality", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /qualitys -> Updates an existing quality.
     */
    @RequestMapping(value = "/qualitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Quality> updateQuality(@Valid @RequestBody Quality quality) throws URISyntaxException {
        log.debug("REST request to update Quality : {}", quality);
        if (quality.getId() == null) {
            return createQuality(quality);
        }
        Quality result = qualityRepository.save(quality);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("quality", quality.getId().toString()))
            .body(result);
    }

    /**
     * GET  /qualitys -> get all the qualitys.
     */
    @RequestMapping(value = "/qualitys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Quality> getAllQualitys() {
        log.debug("REST request to get all Qualitys");
        return qualityRepository.findAll();
    }

    /**
     * GET  mill/:id/qualitys -> get all the qualitys if the mill "id".
     */
    @RequestMapping(value = "mill/{id}/qualitys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Set<Quality> getAllMillQualitys(@PathVariable Long id) {
        log.debug("REST request to get all Qualitys of Mill: {}", id);
        return qualityRepository.findByMillId(id);
    }

    /**
     * GET  /qualitys/:id -> get the "id" quality.
     */
    @RequestMapping(value = "/qualitys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Quality> getQuality(@PathVariable Long id) {
        log.debug("REST request to get Quality : {}", id);
        return Optional.ofNullable(qualityRepository.findOne(id))
            .map(quality -> new ResponseEntity<>(
                quality,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /qualitys/:id -> delete the "id" quality.
     */
    @RequestMapping(value = "/qualitys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteQuality(@PathVariable Long id) {
        log.debug("REST request to delete Quality : {}", id);
        qualityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("quality", id.toString())).build();
    }
}
