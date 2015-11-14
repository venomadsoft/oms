package com.vritant.oms.repository;

import com.vritant.oms.domain.NoteType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the NoteType entity.
 */
public interface NoteTypeRepository extends JpaRepository<NoteType,Long> {

}
