package com.vritant.oms.repository;

import com.vritant.oms.domain.NoteSet;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the NoteSet entity.
 */
public interface NoteSetRepository extends JpaRepository<NoteSet,Long> {

}
