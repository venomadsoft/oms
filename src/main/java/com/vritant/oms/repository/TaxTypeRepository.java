package com.vritant.oms.repository;

import com.vritant.oms.domain.TaxType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TaxType entity.
 */
public interface TaxTypeRepository extends JpaRepository<TaxType,Long> {

}
