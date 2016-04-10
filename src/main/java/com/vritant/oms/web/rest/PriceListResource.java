package com.vritant.oms.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codahale.metrics.annotation.Timed;
import com.vritant.oms.domain.DerivedGsmShade;
import com.vritant.oms.domain.Formula;
import com.vritant.oms.domain.Price;
import com.vritant.oms.domain.PriceList;
import com.vritant.oms.repository.PriceListRepository;
import com.vritant.oms.repository.PriceRepository;
import com.vritant.oms.service.DerivedGsmShadeService;
import com.vritant.oms.web.rest.util.HeaderUtil;
import com.vritant.oms.web.rest.util.PaginationUtil;


/**
 * REST controller for managing PriceList.
 */
@RestController
@RequestMapping("/api")
public class PriceListResource {

    private final Logger log = LoggerFactory.getLogger(PriceListResource.class);

    @Inject
    private PriceListRepository priceListRepository;

    @Inject
    private PriceRepository priceRepository;

    @Inject
    private DerivedGsmShadeService dgsService;

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
        if(priceList != null) {
            Set<Price> prices = priceRepository.findByPriceListId(id);
            priceList.setPricess(enrichDerivedPrices(id, prices));
        }
        return Optional.ofNullable(priceList)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private Set<Price> enrichDerivedPrices(Long id, Set<Price> prices) {

        Set<DerivedGsmShade> derivedShades = dgsService.findByPriceListId(id);
        if(CollectionUtils.isNotEmpty(derivedShades)) {
            Map<Long, List<DerivedGsmShade>> sgsMap = new HashMap<>();
            for(DerivedGsmShade dgShade: derivedShades) {
                List<DerivedGsmShade> dgShades = sgsMap.get(dgShade.getSimpleGsmShade().getId());
                if(dgShades == null) {
                    dgShades = new ArrayList<DerivedGsmShade>();
                    sgsMap.put(dgShade.getSimpleGsmShade().getId(), dgShades);
                }
                dgShades.add(dgShade);
            }
            Set<Price> derivedPrices = new HashSet<Price>();
            for(Price price: prices) {
                List<DerivedGsmShade> dgShades = sgsMap.get(price.getSimpleGsmShade().getId());
                if(CollectionUtils.isNotEmpty(dgShades)) {
                    for(DerivedGsmShade dgShade: dgShades) {
                        Price derivedPrice = price.cloneDerivedPrice();
                        float value = price.getValue();
                        for(Formula formula: dgShade.getFormulae().getSortedChildrens()) {
                            value = formula.apply(value);
                        }
                        derivedPrice.setValue(value);
                        derivedPrice.setDerivedGsmShade(dgShade);
                        derivedPrices.add(derivedPrice);
                    }
                }
            }
            prices.addAll(derivedPrices);
        }
        return prices;
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
