package org.healthnet.backend.devices.presentation.rest;

public class Router implements WebHandler {
    private final WebHandler createPatientWebHandler;

    public Router(WebHandler createPatientWebHandler) {
        this.createPatientWebHandler = createPatientWebHandler;
    }

    @Override
    public WebResponse handle(WebRequest webRequest) {
        if(webRequest.getPath().matches("(/?)") && webRequest.getMethod().equals("POST")) {
            return createPatientWebHandler.handle(webRequest);
        }
        return new WebResponse(WebResponse.Status.NOT_FOUND);
    }
}
