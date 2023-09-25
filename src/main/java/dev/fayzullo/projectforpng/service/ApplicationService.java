package dev.fayzullo.projectforpng.service;


import dev.fayzullo.projectforpng.domains.Camera;
import dev.fayzullo.projectforpng.domains.Document;
import dev.fayzullo.projectforpng.domains.User;
import dev.fayzullo.projectforpng.repository.CameraRepository;
import dev.fayzullo.projectforpng.repository.DocumentRepository;
import dev.fayzullo.projectforpng.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Getter

public class ApplicationService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final CameraRepository cameraRepository;
    private final DocumentService documentService;

    public void start() {
        List<Document> documentList = new ArrayList<>();

        documentList.add(documentService.getDocumentBy("myself"));
        documentList.add(documentService.getDocumentBy("myself1"));
        documentList.add(documentService.getDocumentBy("sertificate"));


        List<Camera> cameraList = new ArrayList<>(List.of(Camera.builder()
                        .latitude(22.342)
                        .longitude(-13.3434543)
                        .streetName("Nukus")
                        .build(),
                Camera.builder()
                        .latitude(52.742)
                        .longitude(-45.3434543)
                        .streetName("Navoiy")
                        .build(), Camera.builder()
                        .latitude(72.342)
                        .longitude(20.3454543)
                        .streetName("Bodomzor")
                        .build(), Camera.builder()
                        .latitude(96.242)
                        .longitude(-13.3434543)
                        .streetName("Oybek")
                        .build(), Camera.builder()
                        .latitude(88.347)
                        .longitude(-67.3434110)
                        .streetName("Yangiobod")
                        .build()));

        cameraRepository.saveAll(cameraList);

        User user1 = User.builder()
                .image(documentList.get(0))
                .carNumber("01 A 100 BA")
                .passport("user-1")
                .dateTime(LocalDateTime.now())
                .cameras(List.of(cameraList.get(0), cameraList.get(1), cameraList.get(2)))
                .build();

        User user2 = User.builder()
                .image(documentList.get(1))
                .carNumber("60 A 876 AA")
                .dateTime(LocalDateTime.now())
                .passport("user-2")
                .cameras(List.of(cameraList.get(1),cameraList.get(2),cameraList.get(3)))
                .build();

        User user3 = User.builder()
                .image(documentList.get(2))
                .carNumber("50 A 074 CA")
                .dateTime(LocalDateTime.now())
                .passport("user-3")
                .cameras(cameraList)
                .build();

        List<User> userList = new ArrayList<>(List.of(user1, user2, user3));
        userRepository.saveAll(userList);

        System.out.println("Users: " +userList);
        System.out.println("Cameras: "+cameraList);
    }

}
