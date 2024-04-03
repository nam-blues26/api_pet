package com.pet.controller;

import com.pet.service.IBannerService;
import com.pet.utils.ImageUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("${project.pet.version.v1}/banner")
public class BannerController {
    @Autowired
    private IBannerService bannerService;

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> upload(@RequestParam("files") MultipartFile[] files) {
            Arrays.asList(files).stream().forEach(file -> {
                String filename = null;
                try {
                    filename = ImageUtil.storeFile(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                bannerService.uploadFiles(filename);
            });

            return ResponseEntity.ok(true);
    }
}
