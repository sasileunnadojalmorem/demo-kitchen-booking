package com.oshi.ohsi_back.domain.kitchen.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oshi.ohsi_back.domain.kitchen.domain.entity.Space;
import com.oshi.ohsi_back.domain.user.domain.entitiy.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SpaceRepository extends JpaRepository<Space, Long> {

    // 태그 ID 리스트에 해당하는 장소 검색

    // 사용자가 등록한 장소를 최신순으로 조회
    List<Space> findByOwnerOrderByCreatedAtDesc(User owner, Pageable pageable);
}