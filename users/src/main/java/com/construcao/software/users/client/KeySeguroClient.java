package com.construcao.software.users.client;

import com.construcao.software.users.client.dto.CreateUserRequest;
import com.construcao.software.users.client.dto.CreateUserResponse;
import com.construcao.software.users.client.dto.EvaluatePermissionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Component
public class KeySeguroClient {

    private final Logger logger = LoggerFactory.getLogger(KeySeguroClient.class);
    private final WebClient client;

    public KeySeguroClient(@Value("${keyseguro.url}") String url) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public String evaluatePermission(EvaluatePermissionRequest request) {
        return client.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/auth/evaluate_permission")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError( throwable -> logger.error("Erro ao avaliar permissões: {}", throwable.getMessage()))
                .block();
    }

    public CreateUserResponse createUser(CreateUserRequest request) {

        if (true) {
            return new CreateUserResponse("fakeid", "rabelo", "admin", "rabelo.example.com");
        }

        return client.post()
                .uri(URI.create("/users"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(CreateUserResponse.class)
                .doOnError(throwable -> logger.error("Erro ao criar usuário: {}", throwable.getMessage()))
                .block();
    }

}
