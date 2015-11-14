package com.vritant.oms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vritant.oms.domain.Address;
import com.vritant.oms.repository.AddressRepository;
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
 * REST controller for managing Address.
 */
@RestController
@RequestMapping("/api")
public class AddressResource {

    private final Logger log = LoggerFactory.getLogger(AddressResource.class);

    @Inject
    private AddressRepository addressRepository;

    /**
     * POST  /addresss -> Create a new address.
     */
    @RequestMapping(value = "/addresss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Address> createAddress(@RequestBody Address address) throws URISyntaxException {
        log.debug("REST request to save Address : {}", address);
        if (address.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new address cannot already have an ID").body(null);
        }
        Address result = addressRepository.save(address);
        return ResponseEntity.created(new URI("/api/addresss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("address", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /addresss -> Updates an existing address.
     */
    @RequestMapping(value = "/addresss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Address> updateAddress(@RequestBody Address address) throws URISyntaxException {
        log.debug("REST request to update Address : {}", address);
        if (address.getId() == null) {
            return createAddress(address);
        }
        Address result = addressRepository.save(address);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("address", address.getId().toString()))
            .body(result);
    }

    /**
     * GET  /addresss -> get all the addresss.
     */
    @RequestMapping(value = "/addresss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Address> getAllAddresss() {
        log.debug("REST request to get all Addresss");
        return addressRepository.findAll();
    }

    /**
     * GET  /addresss/:id -> get the "id" address.
     */
    @RequestMapping(value = "/addresss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Address> getAddress(@PathVariable Long id) {
        log.debug("REST request to get Address : {}", id);
        return Optional.ofNullable(addressRepository.findOne(id))
            .map(address -> new ResponseEntity<>(
                address,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /addresss/:id -> delete the "id" address.
     */
    @RequestMapping(value = "/addresss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        log.debug("REST request to delete Address : {}", id);
        addressRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("address", id.toString())).build();
    }
}
