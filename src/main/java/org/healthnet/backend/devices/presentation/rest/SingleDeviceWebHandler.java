package org.healthnet.backend.devices.presentation.rest;

import org.healthnet.backend.devices.domain.device.Device;

import java.util.NoSuchElementException;
import java.util.function.Function;

public final class SingleDeviceWebHandler extends WebHandler {
    private final int idPathVariableIndex;
    private final Function<String, Device> singleDeviceService;
    private final WebResponseFactory webResponseFactory;

    public SingleDeviceWebHandler(int idPathVariableIndex, Function<String, Device> singleDeviceService, WebResponseFactory webResponseFactory) {
        this.idPathVariableIndex = idPathVariableIndex;
        this.singleDeviceService = singleDeviceService;
        this.webResponseFactory = webResponseFactory;
    }

    @Override
    public WebResponse handle(WebRequest webRequest) {
        try {
            String deviceId = webRequest.getPathVariable(idPathVariableIndex);
            Device device = singleDeviceService.apply(deviceId);
            return webResponseFactory.createOk(device);
        } catch (NoSuchElementException e) {
            return webResponseFactory.createNotFound(e.getMessage());
        } catch (RuntimeException e) {
            return webResponseFactory.createInternalServerError("Internal server error");
        }
    }
}