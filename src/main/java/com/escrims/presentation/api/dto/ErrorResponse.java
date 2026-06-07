package com.escrims.presentation.api.dto;

import java.util.Map;

public class ErrorResponse {

    private final String error;

    public ErrorResponse(String error) {
        this.error = error;
    }

    public static ErrorResponse of(String error) {
        return new ErrorResponse(error);
    }

    public Map<String, String> toMap() {
        return Map.of("error", error);
    }

    public String getError() { return error; }
}
