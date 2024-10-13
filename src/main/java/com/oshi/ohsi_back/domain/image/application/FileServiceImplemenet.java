package com.oshi.ohsi_back.domain.image.application;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServiceImplemenet implements Fileservice {

    @Value("${file.path}")  
    private String filePath;
    
    @Value("${file.url}")  
    private String fileUrl;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String SaveImage(MultipartFile file) {


        // 파일 이름 생성
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + extension;
        String saveFilePath = filePath + saveFileName;

        try {
            // 파일 저장
            file.transferTo(new File(saveFilePath));
            
            // 이미지 URL 생성
            String imageUrl = fileUrl + saveFileName;

            return imageUrl;  // 성공적으로 저장된 이미지 ID 반환
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    @Override
    public Resource getImage(String fileName) {
        try {
            return new UrlResource("file:" + filePath + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public void deleteFile(String fileUrl) {
        try {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            File file = new File(filePath + fileName);
            if (file.exists()) {
                file.delete();  // 파일 삭제
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}