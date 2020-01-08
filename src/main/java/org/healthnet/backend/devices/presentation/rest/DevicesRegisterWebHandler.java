package org.healthnet.backend.devices.presentation.rest;

import org.healthnet.backend.devices.application.dtos.DeviceDetailDto;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class DevicesRegisterWebHandler implements WebHandler {
    private final Supplier<Set<DeviceDetailDto>> selectAllDevicesService;
    private final Function<Set<DeviceDetailDto>, String> serialization;

    public DevicesRegisterWebHandler(Supplier<Set<DeviceDetailDto>> selectAllDevicesService, Function<Set<DeviceDetailDto>, String> serialization) {
        this.selectAllDevicesService = selectAllDevicesService;
        this.serialization = serialization;
    }

    @Override
    public WebResponse handle(WebRequest webRequest) {
        try {
            return handleWebRequest(webRequest);
        } catch (RuntimeException e) {
            return new WebResponse(WebResponse.Status.INTERNAL_SERVER_ERROR);
        }
    }

    private WebResponse handleWebRequest(WebRequest webRequest) {
        System.out.println("fraaaa");
        return new WebResponse(WebResponse.Status.OK, serialization.apply(selectAllDevicesService.get()));
    }
}