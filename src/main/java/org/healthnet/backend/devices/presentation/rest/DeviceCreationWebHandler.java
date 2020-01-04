package org.healthnet.backend.devices.presentation.rest;

import org.healthnet.backend.devices.application.dtos.DeviceCreationDto;

import java.util.function.Consumer;
import java.util.function.Function;

public class DeviceCreationWebHandler implements WebHandler {
    private final Consumer<DeviceCreationDto> createDeviceService;
    private final Function<WebRequest, DeviceCreationDto> deserialization;

    public DeviceCreationWebHandler(Consumer<DeviceCreationDto> createDeviceService,
                                    Function<WebRequest, DeviceCreationDto> deserialization) {
        this.createDeviceService = createDeviceService;
        this.deserialization = deserialization;
    }

    @Override
    public WebResponse handle(WebRequest webRequest) {
        try {
            return handleWebRequest(webRequest);
        } catch (IllegalArgumentException e) {
            return new WebResponse(WebResponse.Status.BAD_REQUEST);
        } catch (IllegalStateException e) {
            return new WebResponse(WebResponse.Status.CONFLICT);
        } catch (RuntimeException e) {
            return new WebResponse(WebResponse.Status.INTERNAL_SERVER_ERROR);
        }
    }

    private WebResponse handleWebRequest(WebRequest webRequest) {
        DeviceCreationDto deviceCreationDto = deserialization.apply(webRequest);
        createDeviceService.accept(deviceCreationDto);
        return new WebResponse(WebResponse.Status.CREATED);
    }
}
