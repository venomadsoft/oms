package com.vritant.oms.repository;

import com.vritant.oms.domain.Formulae;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Formulae entity.
 */
public interface FormulaeRepository extends JpaRepository<Formulae,Long> {

}
