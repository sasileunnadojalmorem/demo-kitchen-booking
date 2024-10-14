package com.oshi.ohsi_back.domain.tag.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oshi.ohsi_back.domain.kitchen.domain.entity.Space;
import com.oshi.ohsi_back.domain.tag.domain.entity.SpaceTag;

import java.util.List;

public interface SpaceTagRepository extends JpaRepository<SpaceTag, SpaceTag.SpaceTagId> {

       @Query("SELECT s FROM SpaceTag st JOIN st.space s " +
           "WHERE st.tag.id IN :tagIds " +
           "GROUP BY s.id " +
           "HAVING COUNT(st.tag.id) = :size")
    Page<Space> findSpacesByTagIds(@Param("tagIds") List<Long> tagIds, 
                                   @Param("size") long size, 
                                   Pageable pageable);
}