package com.vishu.filemanager.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long docId;
    
    private String docUUID;


    private String fileName;
    private String fileType;
    private String filePath;
    private Long fileSize;

    private LocalDateTime uploadedAt;
    
    @PrePersist
    public void generateId() {
        this.docUUID = UUID.randomUUID().toString();
    }
}

