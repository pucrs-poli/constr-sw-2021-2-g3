package com.construcao.software.users.client.dto;

public class EvaluatePermissionRequest {
    private String userToken;
    private String resource;
    private String scope;

    public EvaluatePermissionRequest() {
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
