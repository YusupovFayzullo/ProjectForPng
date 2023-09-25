package dev.fayzullo.projectforpng.dto;


import lombok.*;
import org.springdoc.core.annotations.ParameterObject;

@Getter
@Setter
@NoArgsConstructor
@Builder

@AllArgsConstructor
@ParameterObject

public class CameraDto {

    private String streetName;

    private double latitude;

    private double longitude;
}
