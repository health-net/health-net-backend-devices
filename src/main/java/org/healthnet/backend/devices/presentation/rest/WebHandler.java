package org.healthnet.backend.devices.presentation.rest;

public abstract class WebHandler {
    public abstract WebResponse handle(WebRequest webRequest);
}
