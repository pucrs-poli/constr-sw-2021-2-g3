package com.construcao.software.users.client;

import com.construcao.software.users.client.dto.ChangePasswordDTO;
import com.construcao.software.users.client.dto.CreateUserRequest;
import com.construcao.software.users.client.dto.CreateUserResponse;
import com.construcao.software.users.client.dto.EditUserDTO;
import com.construcao.software.users.client.dto.EvaluatePermissionRequest;
import com.construcao.software.users.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class KeySeguroClient {

    private final boolean mock = true;
    private final Logger logger = LoggerFactory.getLogger(KeySeguroClient.class);
    private final WebClient client;

    public KeySeguroClient(@Value("${keyseguro.url}") String url) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public String evaluatePermission(EvaluatePermissionRequest request) {

        if (mock) {
            return "OK";
        }

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

        if (mock) {
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

    public Void deleteUser(String id) {
        if (mock) {
            return Mono.<Void>empty().block();
        }
        return client.delete()
                .uri(URI.create("/users/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public CreateUserResponse editUser(String id, EditUserDTO userDTO) {
        if (mock) {
            return new CreateUserResponse("fakeid", "rabelo", "admin", "rabelo.example.com");
        }

        return client.put()
                .uri(URI.create("/users/" + id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(userDTO)
                .retrieve()
                .bodyToMono(CreateUserResponse.class)
                .block();
    }

    public CreateUserResponse changePassword(String id, ChangePasswordDTO request) {

        if (mock) {
            return new CreateUserResponse("fakeid", "rabelo", "admin", "rabelo.example.com");
        }

        return client.patch()
                .uri(URI.create("/users/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(CreateUserResponse.class)
                .block();
    }



}
