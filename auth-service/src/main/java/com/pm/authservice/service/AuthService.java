package com.pm.authservice.service;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.model.User;
import com.pm.authservice.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // buat authenticated method yang bakal hold all bussiness logic
    // disini logicnya: password request -> password -> encoded -> $fssdadaiin
    // kemudian hasil encodenya dicompare apakah sama atau tidak encode nya dengan yang di db
    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {

        // ambil user yang di db
        Optional<String> token = userService
                .findByEmail(loginRequestDTO.getEmail())

                // fungsi dari filter method, akan mengecek jika password dan login request matches the one stored in user
                // if the password didn't match then the Optional user di atas akan menjadi empty
                // effectively bakal ngakhirin chain dari userService
                .filter(u-> passwordEncoder.matches(loginRequestDTO.getPassword(), u.getPassword()))

                // fungsi dari map method disini akan mengtransform user object menjadi token yang di assigned di optional token
                .map(u -> jwtUtil.generateToken(u.getEmail(), u.getRole()));

        return token;
    }

    public boolean validateToken(String token) {
        try {
            jwtUtil.validateToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

}
