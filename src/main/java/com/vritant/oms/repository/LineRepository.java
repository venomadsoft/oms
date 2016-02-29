package com.vritant.oms.repository;

import com.vritant.oms.domain.Line;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Line entity.
 */
public interface LineRepository extends JpaRepository<Line,Long> {

}
