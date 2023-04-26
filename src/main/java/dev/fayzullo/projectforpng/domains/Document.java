package dev.fayzullo.projectforpng.domains;


import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column (nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String generatedName;

    @Column(nullable = false)
    private String extension;

    @Column(nullable = false)
    private String mimeType;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private Long size;



}
