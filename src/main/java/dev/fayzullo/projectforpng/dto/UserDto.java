package dev.fayzullo.projectforpng.dto;

import dev.fayzullo.projectforpng.domains.Document;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springdoc.core.annotations.ParameterObject;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder

@AllArgsConstructor
@ParameterObject

public class UserDto {


    @NotBlank(message = "username can not be blank")
    private String passport;

    private String carNumber;


    private Document image;

    private List<CameraDto> cameras;






}
