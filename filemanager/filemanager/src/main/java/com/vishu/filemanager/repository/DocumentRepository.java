package com.vishu.filemanager.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vishu.filemanager.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

	Optional<Document> findByDocUUID(String DocUUID);
	
}


