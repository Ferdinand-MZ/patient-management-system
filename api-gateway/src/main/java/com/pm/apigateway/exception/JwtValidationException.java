package com.pm.apigateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

// annotation ini memberi tahu Spring bahwa class ini digunakan
// secara global untuk menangani exception dari seluruh controller / filter
// khususnya pada Spring WebFlux / Gateway
@RestControllerAdvice
public class JwtValidationException {

    // annotation ini menentukan bahwa method di bawah
    // hanya akan menangani exception dengan tipe:
    // WebClientResponseException.Unauthorized

    // exception ini biasanya muncul ketika:
    // auth-service mengembalikan HTTP 401 Unauthorized

    // contoh kasus:
    // - JWT token invalid
    // - JWT expired
    // - JWT tidak dikirim

    @ExceptionHandler(WebClientResponseException.Unauthorized.class)
    public Mono<Void> handleUnauthorizedException(ServerWebExchange exchange) {

        // mengambil response dari current HTTP request
        // lalu mengubah status response menjadi 401 Unauthorized
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

        // setComplete() digunakan untuk mengakhiri response
        // tanpa mengirim body tambahan
        //
        // Mono<Void> digunakan karena Spring Gateway/WebFlux
        // berjalan secara asynchronous dan reactive
        //
        // hasil akhirnya client akan menerima:
        // HTTP 401 Unauthorized
        return exchange.getResponse().setComplete();
    }
}
