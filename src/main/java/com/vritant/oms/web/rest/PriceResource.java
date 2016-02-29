package com.vritant.oms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vritant.oms.domain.Price;
import com.vritant.oms.repository.PriceRepository;
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
 * REST controller for managing Price.
 */
@RestController
@RequestMapping("/api")
public class PriceResource {

    private final Logger log = LoggerFactory.getLogger(PriceResource.class);
        
    @Inject
    private PriceRepository priceRepository;
    
    /**
     * POST  /prices -> Create a new price.
     */
    @RequestMapping(value = "/prices",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Price> createPrice(@Valid @RequestBody Price price) throws URISyntaxException {
        log.debug("REST request to save Price : {}", price);
        if (price.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("price", "idexists", "A new price cannot already have an ID")).body(null);
        }
        Price result = priceRepository.save(price);
        return ResponseEntity.created(new URI("/api/prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("price", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prices -> Updates an existing price.
     */
    @RequestMapping(value = "/prices",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Price> updatePrice(@Valid @RequestBody Price price) throws URISyntaxException {
        log.debug("REST request to update Price : {}", price);
        if (price.getId() == null) {
            return createPrice(price);
        }
        Price result = priceRepository.save(price);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("price", price.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prices -> get all the prices.
     */
    @RequestMapping(value = "/prices",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Price>> getAllPrices(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Prices");
        Page<Price> page = priceRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prices/:id -> get the "id" price.
     */
    @RequestMapping(value = "/prices/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Price> getPrice(@PathVariable Long id) {
        log.debug("REST request to get Price : {}", id);
        Price price = priceRepository.findOne(id);
        return Optional.ofNullable(price)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prices/:id -> delete the "id" price.
     */
    @RequestMapping(value = "/prices/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrice(@PathVariable Long id) {
        log.debug("REST request to delete Price : {}", id);
        priceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("price", id.toString())).build();
    }
}
