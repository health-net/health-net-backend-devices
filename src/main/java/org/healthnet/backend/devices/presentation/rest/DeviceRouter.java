package org.healthnet.backend.devices.presentation.rest;

public class DeviceRouter implements WebHandler {
    private final WebHandler creationWebHandler;
    private final WebHandler detailWebHandler;
    private final WebHandler registerWebHandler;


    public DeviceRouter(WebHandler creationWebHandler, WebHandler detailWebHandler, WebHandler registerWebHandler) {
        this.creationWebHandler = creationWebHandler;
        this.detailWebHandler = detailWebHandler;
        this.registerWebHandler = registerWebHandler;
    }

    @Override
    public WebResponse handle(WebRequest webRequest) {
        if(webRequest.is("POST", "(/?)$")) {
            return creationWebHandler.handle(webRequest);
        } else if (webRequest.is("GET", "(/?)$")) {
            return registerWebHandler.handle(webRequest);
        } else if (webRequest.is("GET", "(/)(.+)(/?)$")) {
            System.out.println("questo fra "+webRequest.getPath());
            return detailWebHandler.handle(webRequest);
        } else {
            return new WebResponse(WebResponse.Status.NOT_FOUND);
        }
    }
}
