package com.oshi.ohsi_back.domain.tag.infrastructure;
import org.springframework.data.jpa.repository.JpaRepository;

import com.oshi.ohsi_back.domain.tag.domain.entity.Tag;

public interface  TagRepository extends JpaRepository<Tag,Long>{
    
}
