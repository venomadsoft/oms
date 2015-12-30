package com.vritant.oms.repository;

import com.vritant.oms.domain.Quality;

import org.springframework.data.jpa.repository.*;

import java.util.Set;

/**
 * Spring Data JPA repository for the Quality entity.
 */
public interface QualityRepository extends JpaRepository<Quality,Long> {

	public Set<Quality> findByMillId(Long id);
}
