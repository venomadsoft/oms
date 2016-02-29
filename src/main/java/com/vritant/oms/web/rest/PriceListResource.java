package com.vritant.oms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vritant.oms.domain.PriceList;
import com.vritant.oms.repository.PriceListRepository;
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
 * REST controller for managing PriceList.
 */
@RestController
@RequestMapping("/api")
public class PriceListResource {

    private final Logger log = LoggerFactory.getLogger(PriceListResource.class);
        
    @Inject
    private PriceListRepository priceListRepository;
    
    /**
     * POST  /priceLists -> Create a new priceList.
     */
    @RequestMapping(value = "/priceLists",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PriceList> createPriceList(@Valid @RequestBody PriceList priceList) throws URISyntaxException {
        log.debug("REST request to save PriceList : {}", priceList);
        if (priceList.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("priceList", "idexists", "A new priceList cannot already have an ID")).body(null);
        }
        PriceList result = priceListRepository.save(priceList);
        return ResponseEntity.created(new URI("/api/priceLists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("priceList", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /priceLists -> Updates an existing priceList.
     */
    @RequestMapping(value = "/priceLists",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PriceList> updatePriceList(@Valid @RequestBody PriceList priceList) throws URISyntaxException {
        log.debug("REST request to update PriceList : {}", priceList);
        if (priceList.getId() == null) {
            return createPriceList(priceList);
        }
        PriceList result = priceListRepository.save(priceList);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("priceList", priceList.getId().toString()))
            .body(result);
    }

    /**
     * GET  /priceLists -> get all the priceLists.
     */
    @RequestMapping(value = "/priceLists",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PriceList>> getAllPriceLists(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PriceLists");
        Page<PriceList> page = priceListRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/priceLists");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /priceLists/:id -> get the "id" priceList.
     */
    @RequestMapping(value = "/priceLists/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PriceList> getPriceList(@PathVariable Long id) {
        log.debug("REST request to get PriceList : {}", id);
        PriceList priceList = priceListRepository.findOne(id);
        return Optional.ofNullable(priceList)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /priceLists/:id -> delete the "id" priceList.
     */
    @RequestMapping(value = "/priceLists/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePriceList(@PathVariable Long id) {
        log.debug("REST request to delete PriceList : {}", id);
        priceListRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("priceList", id.toString())).build();
    }
}
