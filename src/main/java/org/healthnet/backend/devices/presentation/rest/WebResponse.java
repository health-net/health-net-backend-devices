package org.healthnet.backend.devices.presentation.rest;

public final class WebResponse {
    private final int statusCode;
    private final String body;

    public WebResponse(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBodyAsString() {
        return body;
    }
}
