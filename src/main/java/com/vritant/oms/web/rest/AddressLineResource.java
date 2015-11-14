package com.vritant.oms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vritant.oms.domain.AddressLine;
import com.vritant.oms.repository.AddressLineRepository;
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
 * REST controller for managing AddressLine.
 */
@RestController
@RequestMapping("/api")
public class AddressLineResource {

    private final Logger log = LoggerFactory.getLogger(AddressLineResource.class);

    @Inject
    private AddressLineRepository addressLineRepository;

    /**
     * POST  /addressLines -> Create a new addressLine.
     */
    @RequestMapping(value = "/addressLines",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AddressLine> createAddressLine(@Valid @RequestBody AddressLine addressLine) throws URISyntaxException {
        log.debug("REST request to save AddressLine : {}", addressLine);
        if (addressLine.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new addressLine cannot already have an ID").body(null);
        }
        AddressLine result = addressLineRepository.save(addressLine);
        return ResponseEntity.created(new URI("/api/addressLines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("addressLine", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /addressLines -> Updates an existing addressLine.
     */
    @RequestMapping(value = "/addressLines",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AddressLine> updateAddressLine(@Valid @RequestBody AddressLine addressLine) throws URISyntaxException {
        log.debug("REST request to update AddressLine : {}", addressLine);
        if (addressLine.getId() == null) {
            return createAddressLine(addressLine);
        }
        AddressLine result = addressLineRepository.save(addressLine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("addressLine", addressLine.getId().toString()))
            .body(result);
    }

    /**
     * GET  /addressLines -> get all the addressLines.
     */
    @RequestMapping(value = "/addressLines",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AddressLine> getAllAddressLines() {
        log.debug("REST request to get all AddressLines");
        return addressLineRepository.findAll();
    }

    /**
     * GET  /addressLines/:id -> get the "id" addressLine.
     */
    @RequestMapping(value = "/addressLines/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AddressLine> getAddressLine(@PathVariable Long id) {
        log.debug("REST request to get AddressLine : {}", id);
        return Optional.ofNullable(addressLineRepository.findOne(id))
            .map(addressLine -> new ResponseEntity<>(
                addressLine,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /addressLines/:id -> delete the "id" addressLine.
     */
    @RequestMapping(value = "/addressLines/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAddressLine(@PathVariable Long id) {
        log.debug("REST request to delete AddressLine : {}", id);
        addressLineRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("addressLine", id.toString())).build();
    }
}
