package org.healthnet.backend.devices.presentation.rest;

public class Router implements WebHandler {
    private final WebHandler createDeviceWebHandler;
    private final WebHandler selectDeviceByIDWebHandler;
    private final WebHandler selectAllDevicesWebHandler;


    public Router(WebHandler createDeviceWebHandler, WebHandler selectDeviceByIDWebHandler, WebHandler selectAllDevicesWebHandler) {
        this.createDeviceWebHandler = createDeviceWebHandler;
        this.selectDeviceByIDWebHandler = selectDeviceByIDWebHandler;
        this.selectAllDevicesWebHandler = selectAllDevicesWebHandler;
    }

    @Override
    public WebResponse handle(WebRequest webRequest) {
        if(webRequest.getPath().matches("(/?)") && webRequest.getMethod().equals("POST")) {
            return createDeviceWebHandler.handle(webRequest);
        }
        if(webRequest.getPath().matches("(/?)") && webRequest.getMethod().equals("GET")) {
            return selectAllDevicesWebHandler.handle(webRequest);
        }
        if(webRequest.getPath().matches("(/devices)") && webRequest.getMethod().equals("GET")) {
            return selectDeviceByIDWebHandler.handle(webRequest);
        }
        return new WebResponse(WebResponse.Status.NOT_FOUND);
    }
}
