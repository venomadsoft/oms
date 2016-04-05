package com.vritant.oms.repository;

import com.vritant.oms.domain.SimpleGsmShade;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the SimpleGsmShade entity.
 */
public interface SimpleGsmShadeRepository extends JpaRepository<SimpleGsmShade,Long> {
	
	public List<SimpleGsmShade> findByMillId(Long id);

}
