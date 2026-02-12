package com.vishu.filemanager.controller;


import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vishu.filemanager.entity.Document;
import com.vishu.filemanager.service.FileService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService service;

    @PostMapping("/upload")
    public ResponseEntity<Document> upload(@RequestParam MultipartFile file) throws Exception {
        return ResponseEntity.ok(service.uploadFile(file));
    }

    @GetMapping("/download/{DocUUID}")
    public ResponseEntity<Resource> download(@PathVariable String DocUUID) throws Exception {

        Path path = service.downloadFile(DocUUID);
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{DocUUID}")
    public ResponseEntity<String> delete(@PathVariable String DocUUID) throws Exception {
        service.deleteFile(DocUUID);
        return ResponseEntity.ok("File deleted successfully");
    }
}

