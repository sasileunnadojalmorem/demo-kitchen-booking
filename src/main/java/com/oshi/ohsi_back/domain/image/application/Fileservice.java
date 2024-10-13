package com.oshi.ohsi_back.domain.image.application;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface Fileservice {
    String SaveImage(MultipartFile file);
    Resource getImage(String fileName);
    void deleteFile(String fileUrl);
}