package com.vritant.oms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vritant.oms.domain.Formulae;
import com.vritant.oms.repository.FormulaeRepository;
import com.vritant.oms.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Formulae.
 */
@RestController
@RequestMapping("/api")
public class FormulaeResource {

    private final Logger log = LoggerFactory.getLogger(FormulaeResource.class);

    @Inject
    private FormulaeRepository formulaeRepository;

    /**
     * POST  /formulaes -> Create a new formulae.
     */
    @RequestMapping(value = "/formulaes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Formulae> createFormulae(@RequestBody Formulae formulae) throws URISyntaxException {
        log.debug("REST request to save Formulae : {}", formulae);
        if (formulae.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new formulae cannot already have an ID").body(null);
        }
        Formulae result = formulaeRepository.save(formulae);
        return ResponseEntity.created(new URI("/api/formulaes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("formulae", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /formulaes -> Updates an existing formulae.
     */
    @RequestMapping(value = "/formulaes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Formulae> updateFormulae(@RequestBody Formulae formulae) throws URISyntaxException {
        log.debug("REST request to update Formulae : {}", formulae);
        if (formulae.getId() == null) {
            return createFormulae(formulae);
        }
        Formulae result = formulaeRepository.save(formulae);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("formulae", formulae.getId().toString()))
            .body(result);
    }

    /**
     * GET  /formulaes -> get all the formulaes.
     */
    @RequestMapping(value = "/formulaes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Formulae> getAllFormulaes() {
        log.debug("REST request to get all Formulaes");
        return formulaeRepository.findAll();
    }

    /**
     * GET  /formulaes/:id -> get the "id" formulae.
     */
    @RequestMapping(value = "/formulaes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Formulae> getFormulae(@PathVariable Long id) {
        log.debug("REST request to get Formulae : {}", id);
        return Optional.ofNullable(formulaeRepository.findOne(id))
            .map(formulae -> new ResponseEntity<>(
                formulae,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /formulaes/:id -> delete the "id" formulae.
     */
    @RequestMapping(value = "/formulaes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFormulae(@PathVariable Long id) {
        log.debug("REST request to delete Formulae : {}", id);
        formulaeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("formulae", id.toString())).build();
    }
}
