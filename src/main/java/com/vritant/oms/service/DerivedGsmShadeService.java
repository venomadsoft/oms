package com.vritant.oms.service;

import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vritant.oms.domain.DerivedGsmShade;
import com.vritant.oms.repository.DerivedGsmShadeRepository;

@Service
@Transactional
public class DerivedGsmShadeService {

    private final Logger log = LoggerFactory.getLogger(DerivedGsmShadeService.class);

    @Inject
    private DerivedGsmShadeRepository dgsRepository;

    @Transactional(readOnly = true)
    public Set<DerivedGsmShade> findByPriceListId(Long id) {
        Set<DerivedGsmShade> results = dgsRepository.findByPriceListId(id);
        // force initialization
        if(CollectionUtils.isNotEmpty(results)) {
            for(DerivedGsmShade result: results) {
                Hibernate.initialize(result.getFormulae().getChildrens());
            }
        }
        return results;
    }
}
