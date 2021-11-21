package com.construcao.software.users.service;

import com.construcao.software.users.client.KeySeguroClient;
import com.construcao.software.users.client.dto.EvaluatePermissionRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final KeySeguroClient keySeguroClient;

    public AuthService(KeySeguroClient keySeguroClient) {
        this.keySeguroClient = keySeguroClient;
    }

    public boolean hasAuthorization(EvaluatePermissionRequest request) {
        try {
            keySeguroClient.evaluatePermission(request);
        } catch(Exception e) {
            return false;
        }
        return true;
    }
}
