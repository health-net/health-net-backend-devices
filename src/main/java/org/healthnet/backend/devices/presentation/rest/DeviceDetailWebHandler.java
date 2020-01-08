package org.healthnet.backend.devices.presentation.rest;

import org.healthnet.backend.devices.application.dtos.DeviceSelectionDto;
import org.healthnet.backend.devices.application.dtos.DeviceDetailDto;

import java.util.NoSuchElementException;
import java.util.function.Function;

public class DeviceDetailWebHandler implements WebHandler {
    private final Function<String, DeviceDetailDto> selectDeviceService;
    private final Function<WebRequest, DeviceSelectionDto> deserialization;
    private final Function<? super DeviceDetailDto, String> serialization;

    public DeviceDetailWebHandler(Function<String, DeviceDetailDto> selectDeviceService,
                                  Function<WebRequest, DeviceSelectionDto> deserialization,
                                  Function<DeviceDetailDto, String> serialization) {
        this.selectDeviceService = selectDeviceService;
        this.deserialization = deserialization;
        this.serialization = serialization;
    }

    @Override
    public WebResponse handle(WebRequest webRequest) {
        try {
            System.out.println("fra");
            return handleWebRequest(webRequest);
        } catch (NoSuchElementException e) {
            return new WebResponse(WebResponse.Status.NOT_FOUND);
        } catch (RuntimeException e) {
            return new WebResponse(WebResponse.Status.INTERNAL_SERVER_ERROR);
        }
    }

    private WebResponse handleWebRequest(WebRequest webRequest) {
        System.out.println("porcodio fra");
        DeviceSelectionDto deviceSelectionDto = deserialization.apply(webRequest);
        System.out.println("fra2"+deviceSelectionDto.name+" "+deviceSelectionDto.getId());
        return new WebResponse(WebResponse.Status.OK, serialization.apply(selectDeviceService.apply(deviceSelectionDto.getId())));
    }
}