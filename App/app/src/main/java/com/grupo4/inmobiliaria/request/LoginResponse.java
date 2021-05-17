package com.grupo4.inmobiliaria.request;

import com.grupo4.inmobiliaria.modelo.Propietario;

public class LoginResponse {
    private int statusCode;
    private String token;
    private Propietario propietario;

    public LoginResponse() {
    }

    public LoginResponse(int statusCode, String token, Propietario propietario) {
        this.statusCode = statusCode;
        this.token = token;
        this.propietario = propietario;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }
}
