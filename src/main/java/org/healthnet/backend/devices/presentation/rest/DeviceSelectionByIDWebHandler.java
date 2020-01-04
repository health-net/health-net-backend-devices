package org.healthnet.backend.devices.presentation.rest;

import org.healthnet.backend.devices.application.dtos.DeviceSelectionDto;
import org.healthnet.backend.devices.application.dtos.FetchedDeviceDto;

import java.util.function.Consumer;
import java.util.function.Function;

public class DeviceSelectionByIDWebHandler implements WebHandler {
    private final Function<DeviceSelectionDto, FetchedDeviceDto> selectDeviceService;
    private final Function<WebRequest, DeviceSelectionDto> deserialization;
    private final Function<FetchedDeviceDto, String> serialization;

    public DeviceSelectionByIDWebHandler(Function<DeviceSelectionDto, FetchedDeviceDto> selectDeviceService,
                                    Function<WebRequest, DeviceSelectionDto> deserialization,
                                         Function<FetchedDeviceDto, String> serialization) {
        this.selectDeviceService = selectDeviceService;
        this.deserialization = deserialization;
        this.serialization = serialization;
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
        DeviceSelectionDto deviceSelectionDto = deserialization.apply(webRequest);
        return new WebResponse(WebResponse.Status.OK, serialization.apply(selectDeviceService.apply(deviceSelectionDto)));
    }
}