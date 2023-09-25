package dev.fayzullo.projectforpng.controller;


import dev.fayzullo.projectforpng.domains.UserEvent;
import dev.fayzullo.projectforpng.domains.UserInformation;
import dev.fayzullo.projectforpng.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {


    private final UserService userService;

    @GetMapping("/getUserByImage")
    private ResponseEntity<UserInformation> getUserByImage(@RequestBody MultipartFile file) throws IOException {
        UserInformation userInformation=userService.getUserByImage(file);
        return ResponseEntity.status(201).body(userInformation);
    }

    @GetMapping("/getUserEventByImage")
    private ResponseEntity<UserEvent> getUserByPassport(@RequestBody MultipartFile file){
        UserEvent userEvent=userService.getUserEventByImage(file);
        return ResponseEntity.status(201).body(userEvent);

    }
}
