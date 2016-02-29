package com.vritant.oms.repository;

import com.vritant.oms.domain.Mill;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Mill entity.
 */
public interface MillRepository extends JpaRepository<Mill,Long> {

}
