package dev.fayzullo.projectforpng.service;


import dev.fayzullo.projectforpng.domains.Document;
import dev.fayzullo.projectforpng.repository.DocumentRepository;
import io.micrometer.common.lang.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class DocumentService {


    @Value("${uploads.folder}")
    private  String fileStorageLocation;
    private final DocumentRepository documentRepository;


    public Document saveDocumentToServer(MultipartFile file) throws IOException {
        String generateUniqueName = generateUniqueName(Objects.requireNonNull(file.getOriginalFilename()));
        Document document = Document.builder()
                .generatedName(generateUniqueName)
                .size(file.getSize())
                .extension(StringUtils.getFilenameExtension(file.getOriginalFilename()))
                .mimeType(file.getContentType())
                .filePath(fileStorageLocation+generateUniqueName)
                .originalName(file.getOriginalFilename())
                .build();
        try {
            File file1 = new File(document.getFilePath());
            InputStream inputStream = file.getInputStream();
            BufferedImage read = ImageIO.read(inputStream);
            file.transferTo(file1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return documentRepository.save(document);
    }




    public Document getDocumentBy(String fileName) {
        return getDocument(fileName).orElseThrow(() -> new RuntimeException("Document not found by "+fileName));
    }

    private Optional<Document> getDocument(String filename) {
        return documentRepository.findByGeneratedName(filename);
    }
    public String generateUniqueName(@NonNull String fileName) {
        return UUID.randomUUID() + "." + StringUtils.getFilenameExtension(fileName);
    }
}
