package dev.fayzullo.projectforpng.repository;

import dev.fayzullo.projectforpng.domains.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document,Long> {



    Optional<Document> findByGeneratedName( String generatedName);

    @Query("select d from Document d  where d.originalName=?1 and d.size=?2")
    Optional<Document> findImage( String originalName, long size);
}
