package com.pm.authservice.controller;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.dto.LoginResponseDTO;
import com.pm.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @Operation(summary = "Generate token on user login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
        @RequestBody LoginRequestDTO loginRequestDTO) {

            // ketika login request happen, kita akan memanggil service layer method
            // yang mana method akan memanggil authenticate, yang accept loginRequestDTO
            // authenticate method akan melakukan bussiness logic dalam memastikan
            // credential di request valid dan generate tokennya, kemudian handle cases
            // dimana tokennya invalid
            // optional di java artinya antara return empty atau string
            Optional<String> tokenOptional = authService.authenticate(loginRequestDTO);

            if(tokenOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String token = tokenOptional.get();

            return ResponseEntity.ok(new LoginResponseDTO(token));
        }

        @Operation(summary = "Validate token")
        @GetMapping("/validate")

        // any request yang sampai ke validate endpoint, spring akan memberikan authorization header dari request header
        // kemudian header nya di pass ke kita sebagai variabel bernama authHeader yang bertipe string
        public ResponseEntity<Void> validateToken(
            @RequestHeader("Authorization") String authHeader) {

            // Authorization: Bearer <token>
            if(authHeader == null || !authHeader.startsWith("Bearer")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // kenapa dimulai dari 7? karena 6 awal karakter pertamanya merupakan bearer
            return authService.validateToken(authHeader.substring(7))
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
}
