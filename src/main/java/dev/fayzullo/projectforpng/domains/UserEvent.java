package dev.fayzullo.projectforpng.domains;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class UserEvent {

    private String passport;

    private Document image;

    private String carNumber;

    private List<Camera> cameras;

    private LocalDateTime dateTime;
}
