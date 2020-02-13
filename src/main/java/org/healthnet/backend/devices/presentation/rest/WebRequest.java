package org.healthnet.backend.devices.presentation.rest;

public abstract class WebRequest {
    public abstract boolean isOn(String path);
    public abstract boolean isGet();
    public abstract String getPathVariable(int index);
    public abstract String getQueryParameter(String name);
}
