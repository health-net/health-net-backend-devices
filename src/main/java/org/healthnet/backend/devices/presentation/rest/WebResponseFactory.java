package org.healthnet.backend.devices.presentation.rest;

public abstract class WebResponseFactory {
    public abstract WebResponse createOk(Object body);
    public abstract WebResponse createNotFound(String message);
    public abstract WebResponse createInternalServerError(String message);
}
