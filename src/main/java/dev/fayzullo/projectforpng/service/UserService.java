package dev.fayzullo.projectforpng.service;


import dev.fayzullo.projectforpng.domains.Document;
import dev.fayzullo.projectforpng.domains.User;
import dev.fayzullo.projectforpng.domains.UserEvent;
import dev.fayzullo.projectforpng.domains.UserInformation;
import dev.fayzullo.projectforpng.repository.DocumentRepository;
import dev.fayzullo.projectforpng.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;


    public UserInformation getUserByImage(MultipartFile file) throws IOException {
        Optional<Document> image = existUser(file);
        if (image.isPresent()) {
            Document document = image.get();
            Optional<User> byImage = userRepository.findByImage(document);
            if(byImage.isPresent()){
                User user = byImage.get();
                return UserInformation.builder()
                        .carNumber(user.getCarNumber())
                        .passport(user.getPassport())
                        .document(user.getImage())
                        .build();

            }
            return null;
        }
        return null;
    }


    public UserEvent getUserEventByImage(MultipartFile file)  {
        Optional<Document> image = existUser(file);
        if (image.isPresent()) {
            Document document = image.get();
            Optional<User> byImage = userRepository.findByImage(document);
            if(byImage.isPresent()){
                User user = byImage.get();
                return UserEvent.builder()
                        .carNumber(user.getCarNumber())
                        .dateTime(user.getDateTime())
                        .cameras(user.getCameras())
                        .image(user.getImage())
                        .passport(user.getPassport())
                        .build();

            }
            return null;
        }
        return null;

    }
    private Optional<Document> existUser(MultipartFile file) {
        long size = file.getSize();
        String originalFilename = file.getOriginalFilename();
        byte[] bytes = new byte[0];
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String string = Arrays.toString(bytes);
        return documentRepository.findImage(string, originalFilename, size);
    }
}
