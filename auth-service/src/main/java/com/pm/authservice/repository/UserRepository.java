package com.pm.authservice.repository;

// repository bentuk interface karena kita akan extends jpa repository dengan add method kita sendiri disini

import com.pm.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

// User ngarah ke table nya, dan UUID mengarah ke unik identifier nya
public interface UserRepository extends JpaRepository<User, UUID> {

    // JPA bakal handle sql query behind the scenes based on property email yang kita ketik disini
    Optional<User> findByEmail(String email);
}
