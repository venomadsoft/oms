package com.vritant.oms.repository;

import com.vritant.oms.domain.Addresses;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Addresses entity.
 */
public interface AddressesRepository extends JpaRepository<Addresses,Long> {

}
