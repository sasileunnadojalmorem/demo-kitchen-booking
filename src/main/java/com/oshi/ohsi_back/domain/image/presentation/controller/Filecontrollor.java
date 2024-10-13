    package com.oshi.ohsi_back.domain.image.presentation.controller;

    import org.springframework.core.io.Resource;
    import org.springframework.http.MediaType;

    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

import com.oshi.ohsi_back.domain.image.application.Fileservice;

import lombok.RequiredArgsConstructor;

    @RestController
    @RequestMapping("/api/user/file")
    @RequiredArgsConstructor
    public class Filecontrollor {

    private final Fileservice fileservice;


    @GetMapping(value = "{fileName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public Resource getImage(
            @PathVariable("fileName") String fileName) {
        
        Resource resource = fileservice.getImage(fileName);
        return resource;
    }
}