package dev.fayzullo.projectforpng.service;


import dev.fayzullo.projectforpng.ImageUtils;
import dev.fayzullo.projectforpng.domains.Document;
import dev.fayzullo.projectforpng.repository.DocumentRepository;
import io.micrometer.common.lang.NonNull;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class DocumentService {


    @Value("${uploads.folder}")
    private  String fileStorageLocation;
    private final DocumentRepository documentRepository;


    public Document saveDocumentToServer(MultipartFile file) {
        String generateUniqueName = generateUniqueName(Objects.requireNonNull(file.getOriginalFilename()));
        Document document = Document.builder()
                .generatedName(generateUniqueName)
                .size(file.getSize())
                .extension(StringUtils.getFilenameExtension(file.getOriginalFilename()))
                .mimeType(file.getContentType())
                .filePath(fileStorageLocation+generateUniqueName)
                .originalName(file.getOriginalFilename())
                .build();


        Document savedDocument = documentRepository.save(document);
        String extension = savedDocument.getExtension();
        try {
            InputStream inputStream = file.getInputStream();
            File file1=new File(savedDocument.getFilePath());
            BufferedImage read = ImageIO.read(inputStream);
            file.transferTo(file1);
            BufferedImage bufferedImage = resizeImage(read, 100, 100);
            ImageIO.write(bufferedImage,extension,file1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return savedDocument;
    }


    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight)  {
        return Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight, Scalr.OP_ANTIALIAS);
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
