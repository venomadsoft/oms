package com.vritant.oms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vritant.oms.domain.Tax;
import com.vritant.oms.repository.TaxRepository;
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
 * REST controller for managing Tax.
 */
@RestController
@RequestMapping("/api")
public class TaxResource {

    private final Logger log = LoggerFactory.getLogger(TaxResource.class);

    @Inject
    private TaxRepository taxRepository;

    /**
     * POST  /taxs -> Create a new tax.
     */
    @RequestMapping(value = "/taxs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax> createTax(@Valid @RequestBody Tax tax) throws URISyntaxException {
        log.debug("REST request to save Tax : {}", tax);
        if (tax.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new tax cannot already have an ID").body(null);
        }
        Tax result = taxRepository.save(tax);
        return ResponseEntity.created(new URI("/api/taxs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tax", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /taxs -> Updates an existing tax.
     */
    @RequestMapping(value = "/taxs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax> updateTax(@Valid @RequestBody Tax tax) throws URISyntaxException {
        log.debug("REST request to update Tax : {}", tax);
        if (tax.getId() == null) {
            return createTax(tax);
        }
        Tax result = taxRepository.save(tax);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tax", tax.getId().toString()))
            .body(result);
    }

    /**
     * GET  /taxs -> get all the taxs.
     */
    @RequestMapping(value = "/taxs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Tax> getAllTaxs() {
        log.debug("REST request to get all Taxs");
        return taxRepository.findAll();
    }

    /**
     * GET  /taxs/:id -> get the "id" tax.
     */
    @RequestMapping(value = "/taxs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax> getTax(@PathVariable Long id) {
        log.debug("REST request to get Tax : {}", id);
        return Optional.ofNullable(taxRepository.findOne(id))
            .map(tax -> new ResponseEntity<>(
                tax,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /taxs/:id -> delete the "id" tax.
     */
    @RequestMapping(value = "/taxs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTax(@PathVariable Long id) {
        log.debug("REST request to delete Tax : {}", id);
        taxRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tax", id.toString())).build();
    }
}
