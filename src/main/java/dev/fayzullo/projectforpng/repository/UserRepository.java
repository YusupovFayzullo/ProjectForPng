package dev.fayzullo.projectforpng.repository;


import dev.fayzullo.projectforpng.domains.Document;
import dev.fayzullo.projectforpng.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByImage(Document image);

}

