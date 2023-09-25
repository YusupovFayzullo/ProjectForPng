package dev.fayzullo.projectforpng.domains;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class UserInformation {

    private String passport;
    private String carNumber;

    private Document document;
}
