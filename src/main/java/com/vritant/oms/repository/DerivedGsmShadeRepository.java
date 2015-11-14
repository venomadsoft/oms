package com.vritant.oms.repository;

import com.vritant.oms.domain.DerivedGsmShade;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DerivedGsmShade entity.
 */
public interface DerivedGsmShadeRepository extends JpaRepository<DerivedGsmShade,Long> {

}
