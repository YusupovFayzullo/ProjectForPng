package dev.fayzullo.projectforpng.domains;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Builder

public class Camera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String streetName;

    private double latitude;

    private double longitude;

    @ManyToOne
    private User user;




}
