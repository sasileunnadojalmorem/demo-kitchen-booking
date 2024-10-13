package com.oshi.ohsi_back.domain.image.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oshi.ohsi_back.domain.image.domain.entity.ImageEntity;
import com.oshi.ohsi_back.domain.image.domain.enums.ImageType;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
    
    ImageEntity findById(int id);
    boolean existsById(int id);
    List<ImageEntity> findByRelatedIdAndRelatedType(int relatedId, ImageType relatedType);

}