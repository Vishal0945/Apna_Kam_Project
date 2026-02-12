package com.vishu.filemanager.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vishu.filemanager.entity.Document;
import com.vishu.filemanager.repository.DocumentRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class FileService {

    private final DocumentRepository repository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public Document uploadFile(MultipartFile file) throws IOException {

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Document doc = new Document();
        doc.setFileName(fileName);
        doc.setFileType(file.getContentType());
        doc.setFilePath(filePath.toString());
        doc.setFileSize(file.getSize());
        doc.setUploadedAt(LocalDateTime.now());

        return repository.save(doc);
    }

    public Path downloadFile(String DocUUID) {
        Document doc = repository.findByDocUUID(DocUUID)
                .orElseThrow(() -> new RuntimeException("File not found"));
        return Paths.get(doc.getFilePath());
    }

    public void deleteFile(String DocUUID) throws IOException {
        Document doc = repository.findByDocUUID(DocUUID)
                .orElseThrow(() -> new RuntimeException("File not found"));

        Files.deleteIfExists(Paths.get(doc.getFilePath()));
        repository.delete(doc);
    }
}

