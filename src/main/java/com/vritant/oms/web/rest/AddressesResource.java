package com.vritant.oms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vritant.oms.domain.Addresses;
import com.vritant.oms.repository.AddressesRepository;
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
 * REST controller for managing Addresses.
 */
@RestController
@RequestMapping("/api")
public class AddressesResource {

    private final Logger log = LoggerFactory.getLogger(AddressesResource.class);

    @Inject
    private AddressesRepository addressesRepository;

    /**
     * POST  /addressess -> Create a new addresses.
     */
    @RequestMapping(value = "/addressess",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Addresses> createAddresses(@RequestBody Addresses addresses) throws URISyntaxException {
        log.debug("REST request to save Addresses : {}", addresses);
        if (addresses.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new addresses cannot already have an ID").body(null);
        }
        Addresses result = addressesRepository.save(addresses);
        return ResponseEntity.created(new URI("/api/addressess/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("addresses", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /addressess -> Updates an existing addresses.
     */
    @RequestMapping(value = "/addressess",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Addresses> updateAddresses(@RequestBody Addresses addresses) throws URISyntaxException {
        log.debug("REST request to update Addresses : {}", addresses);
        if (addresses.getId() == null) {
            return createAddresses(addresses);
        }
        Addresses result = addressesRepository.save(addresses);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("addresses", addresses.getId().toString()))
            .body(result);
    }

    /**
     * GET  /addressess -> get all the addressess.
     */
    @RequestMapping(value = "/addressess",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Addresses> getAllAddressess() {
        log.debug("REST request to get all Addressess");
        return addressesRepository.findAll();
    }

    /**
     * GET  /addressess/:id -> get the "id" addresses.
     */
    @RequestMapping(value = "/addressess/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Addresses> getAddresses(@PathVariable Long id) {
        log.debug("REST request to get Addresses : {}", id);
        return Optional.ofNullable(addressesRepository.findOne(id))
            .map(addresses -> new ResponseEntity<>(
                addresses,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /addressess/:id -> delete the "id" addresses.
     */
    @RequestMapping(value = "/addressess/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAddresses(@PathVariable Long id) {
        log.debug("REST request to delete Addresses : {}", id);
        addressesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("addresses", id.toString())).build();
    }
}
