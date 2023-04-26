package dev.fayzullo.projectforpng.repository;

import dev.fayzullo.projectforpng.domains.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document,Long> {



    Optional<Document> findByGeneratedName( String generatedName);
}
