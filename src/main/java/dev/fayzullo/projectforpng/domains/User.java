package dev.fayzullo.projectforpng.domains;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity

@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String passport;

    @OneToOne
    private Document image;

    @Column(unique = true)
    private String carNumber;

    @OneToMany
    private List<Camera> cameras;

    private LocalDateTime dateTime;

}
