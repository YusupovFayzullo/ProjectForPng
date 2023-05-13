package dev.fayzullo.projectforpng.controller;

import dev.fayzullo.projectforpng.domains.Document;
import dev.fayzullo.projectforpng.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;


@RestController
@RequiredArgsConstructor
@ParameterObject
@RequestMapping("/api/v1/documents")
public class DocumentController {
    private final DocumentService documentService;

    @Value("${uploads.folder}")
    private String fileStorageLocation;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Document> uploadFile(@RequestParam("file") MultipartFile  file) {

        return ResponseEntity.status(201).body(documentService.saveDocumentToServer(file));
    }


    @GetMapping(value = "/download/{fileName}")
    public ResponseEntity<FileUrlResource>  downloadFile(@PathVariable String fileName) throws MalformedURLException {
        Document document= documentService.getDocumentBy(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getMimeType()))
                .contentLength(document.getSize())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getGeneratedName())
                .body(new FileUrlResource(document.getFilePath()));
    }

    @GetMapping(value = "/open/{fileName}")
    public ResponseEntity<FileUrlResource>  openFile(@PathVariable String fileName) throws MalformedURLException {
        Document document= documentService.getDocumentBy(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getMimeType()))
                .contentLength(document.getSize())
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + document.getGeneratedName())
                .body(new FileUrlResource(document.getFilePath()));
    }






}
