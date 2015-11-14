package com.vritant.oms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vritant.oms.domain.CustomerGroup;
import com.vritant.oms.repository.CustomerGroupRepository;
import com.vritant.oms.web.rest.util.HeaderUtil;
import com.vritant.oms.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * REST controller for managing CustomerGroup.
 */
@RestController
@RequestMapping("/api")
public class CustomerGroupResource {

    private final Logger log = LoggerFactory.getLogger(CustomerGroupResource.class);

    @Inject
    private CustomerGroupRepository customerGroupRepository;

    /**
     * POST  /customerGroups -> Create a new customerGroup.
     */
    @RequestMapping(value = "/customerGroups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomerGroup> createCustomerGroup(@Valid @RequestBody CustomerGroup customerGroup) throws URISyntaxException {
        log.debug("REST request to save CustomerGroup : {}", customerGroup);
        if (customerGroup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new customerGroup cannot already have an ID").body(null);
        }
        CustomerGroup result = customerGroupRepository.save(customerGroup);
        return ResponseEntity.created(new URI("/api/customerGroups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("customerGroup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customerGroups -> Updates an existing customerGroup.
     */
    @RequestMapping(value = "/customerGroups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomerGroup> updateCustomerGroup(@Valid @RequestBody CustomerGroup customerGroup) throws URISyntaxException {
        log.debug("REST request to update CustomerGroup : {}", customerGroup);
        if (customerGroup.getId() == null) {
            return createCustomerGroup(customerGroup);
        }
        CustomerGroup result = customerGroupRepository.save(customerGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("customerGroup", customerGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customerGroups -> get all the customerGroups.
     */
    @RequestMapping(value = "/customerGroups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CustomerGroup>> getAllCustomerGroups(Pageable pageable)
        throws URISyntaxException {
        Page<CustomerGroup> page = customerGroupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customerGroups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customerGroups/:id -> get the "id" customerGroup.
     */
    @RequestMapping(value = "/customerGroups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomerGroup> getCustomerGroup(@PathVariable Long id) {
        log.debug("REST request to get CustomerGroup : {}", id);
        return Optional.ofNullable(customerGroupRepository.findOneWithEagerRelationships(id))
            .map(customerGroup -> new ResponseEntity<>(
                customerGroup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /customerGroups/:id -> delete the "id" customerGroup.
     */
    @RequestMapping(value = "/customerGroups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCustomerGroup(@PathVariable Long id) {
        log.debug("REST request to delete CustomerGroup : {}", id);
        customerGroupRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("customerGroup", id.toString())).build();
    }
}
