package com.vritant.oms.repository;

import com.vritant.oms.domain.Price;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the Price entity.
 */
public interface PriceRepository extends JpaRepository<Price,Long> {

	public Set<Price> findByPriceListId(Long id);
}
