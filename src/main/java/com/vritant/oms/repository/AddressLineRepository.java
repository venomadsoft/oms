package com.vritant.oms.repository;

import com.vritant.oms.domain.AddressLine;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AddressLine entity.
 */
public interface AddressLineRepository extends JpaRepository<AddressLine,Long> {

}
