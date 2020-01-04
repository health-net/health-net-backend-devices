package org.healthnet.backend.devices.presentation.rest;

public class WebResponse {
    private final Status status;
    private final String body;

    public WebResponse(Status status, String body) {
        this.status = status;
        this.body = body;
    }

    public WebResponse(Status status) {
        this(status, "");
    }

    public Status getStatus() {
        return status;
    }

    public int getStatusCode() {
        return status.code;
    }

    public String getBodyContent() {
        return body;
    }

    public enum Status {
        OK (200),
        NOT_FOUND (404),
        CREATED (201),
        BAD_REQUEST (400),
        CONFLICT (409),
        INTERNAL_SERVER_ERROR (500);

        private final int code;

        Status(int code) {
            this.code = code;
        }
    }
}
