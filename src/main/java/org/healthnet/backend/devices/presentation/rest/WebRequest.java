package org.healthnet.backend.devices.presentation.rest;

import java.io.Reader;

public class WebRequest {
    private final String path;
    private final String method;
    private final Reader reader;

    public WebRequest(String method, String path, Reader reader) {
        this.path = path;
        this.method = method;
        this.reader = reader;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public boolean is(String method, String regex) {
        return this.path.matches(regex) && this.method.equals(method);
    }

    public Reader getBodyContent() {
        return reader;
    }
}
