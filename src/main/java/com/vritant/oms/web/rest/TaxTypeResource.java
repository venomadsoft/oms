package com.vritant.oms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vritant.oms.domain.TaxType;
import com.vritant.oms.repository.TaxTypeRepository;
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
 * REST controller for managing TaxType.
 */
@RestController
@RequestMapping("/api")
public class TaxTypeResource {

    private final Logger log = LoggerFactory.getLogger(TaxTypeResource.class);

    @Inject
    private TaxTypeRepository taxTypeRepository;

    /**
     * POST  /taxTypes -> Create a new taxType.
     */
    @RequestMapping(value = "/taxTypes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TaxType> createTaxType(@Valid @RequestBody TaxType taxType) throws URISyntaxException {
        log.debug("REST request to save TaxType : {}", taxType);
        if (taxType.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new taxType cannot already have an ID").body(null);
        }
        TaxType result = taxTypeRepository.save(taxType);
        return ResponseEntity.created(new URI("/api/taxTypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("taxType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /taxTypes -> Updates an existing taxType.
     */
    @RequestMapping(value = "/taxTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TaxType> updateTaxType(@Valid @RequestBody TaxType taxType) throws URISyntaxException {
        log.debug("REST request to update TaxType : {}", taxType);
        if (taxType.getId() == null) {
            return createTaxType(taxType);
        }
        TaxType result = taxTypeRepository.save(taxType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("taxType", taxType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /taxTypes -> get all the taxTypes.
     */
    @RequestMapping(value = "/taxTypes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TaxType> getAllTaxTypes() {
        log.debug("REST request to get all TaxTypes");
        return taxTypeRepository.findAll();
    }

    /**
     * GET  /taxTypes/:id -> get the "id" taxType.
     */
    @RequestMapping(value = "/taxTypes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TaxType> getTaxType(@PathVariable Long id) {
        log.debug("REST request to get TaxType : {}", id);
        return Optional.ofNullable(taxTypeRepository.findOne(id))
            .map(taxType -> new ResponseEntity<>(
                taxType,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /taxTypes/:id -> delete the "id" taxType.
     */
    @RequestMapping(value = "/taxTypes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTaxType(@PathVariable Long id) {
        log.debug("REST request to delete TaxType : {}", id);
        taxTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("taxType", id.toString())).build();
    }
}
