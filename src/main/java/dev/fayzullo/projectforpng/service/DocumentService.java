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
import java.awt.*;
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
        System.out.println();
        Document savedDocument = documentRepository.save(document);

        try {
            File file1 = new File(document.getFilePath());
            InputStream inputStream = file.getInputStream();
            BufferedImage read = ImageIO.read(inputStream);
            file.transferTo(file1);
            resizeImage(savedDocument.getExtension(),read,100,100,file1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return savedDocument;
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





        public void resizeImage(String extension, BufferedImage image,
                                int targetWidth, int targetHeight, File file) throws IOException {


            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, image.getType());
            Graphics2D graphics = resizedImage.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            graphics.drawImage(image, 0, 0, targetWidth, targetHeight, null);
            graphics.dispose();
            ImageIO.write(resizedImage, extension, file);
        }



}
