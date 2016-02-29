package com.vritant.oms.repository;

import com.vritant.oms.domain.Formula;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Formula entity.
 */
public interface FormulaRepository extends JpaRepository<Formula,Long> {

}
