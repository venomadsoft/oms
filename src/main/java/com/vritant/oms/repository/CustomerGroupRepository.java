package com.vritant.oms.repository;

import com.vritant.oms.domain.CustomerGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomerGroup entity.
 */
public interface CustomerGroupRepository extends JpaRepository<CustomerGroup,Long> {

    @Query("select distinct customerGroup from CustomerGroup customerGroup left join fetch customerGroup.customerss")
    List<CustomerGroup> findAllWithEagerRelationships();

    @Query("select customerGroup from CustomerGroup customerGroup left join fetch customerGroup.customerss where customerGroup.id =:id")
    CustomerGroup findOneWithEagerRelationships(@Param("id") Long id);

}
