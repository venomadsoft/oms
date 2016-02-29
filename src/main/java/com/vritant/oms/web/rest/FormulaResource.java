package com.vritant.oms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vritant.oms.domain.Formula;
import com.vritant.oms.repository.FormulaRepository;
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

/**
 * REST controller for managing Formula.
 */
@RestController
@RequestMapping("/api")
public class FormulaResource {

    private final Logger log = LoggerFactory.getLogger(FormulaResource.class);
        
    @Inject
    private FormulaRepository formulaRepository;
    
    /**
     * POST  /formulas -> Create a new formula.
     */
    @RequestMapping(value = "/formulas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Formula> createFormula(@Valid @RequestBody Formula formula) throws URISyntaxException {
        log.debug("REST request to save Formula : {}", formula);
        if (formula.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("formula", "idexists", "A new formula cannot already have an ID")).body(null);
        }
        Formula result = formulaRepository.save(formula);
        return ResponseEntity.created(new URI("/api/formulas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("formula", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /formulas -> Updates an existing formula.
     */
    @RequestMapping(value = "/formulas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Formula> updateFormula(@Valid @RequestBody Formula formula) throws URISyntaxException {
        log.debug("REST request to update Formula : {}", formula);
        if (formula.getId() == null) {
            return createFormula(formula);
        }
        Formula result = formulaRepository.save(formula);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("formula", formula.getId().toString()))
            .body(result);
    }

    /**
     * GET  /formulas -> get all the formulas.
     */
    @RequestMapping(value = "/formulas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Formula> getAllFormulas() {
        log.debug("REST request to get all Formulas");
        return formulaRepository.findAll();
            }

    /**
     * GET  /formulas/:id -> get the "id" formula.
     */
    @RequestMapping(value = "/formulas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Formula> getFormula(@PathVariable Long id) {
        log.debug("REST request to get Formula : {}", id);
        Formula formula = formulaRepository.findOne(id);
        return Optional.ofNullable(formula)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /formulas/:id -> delete the "id" formula.
     */
    @RequestMapping(value = "/formulas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFormula(@PathVariable Long id) {
        log.debug("REST request to delete Formula : {}", id);
        formulaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("formula", id.toString())).build();
    }
}
