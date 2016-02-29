package com.vritant.oms.repository;

import com.vritant.oms.domain.PriceList;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PriceList entity.
 */
public interface PriceListRepository extends JpaRepository<PriceList,Long> {

}
