package org.healthnet.backend.devices.presentation.rest;

import org.healthnet.backend.devices.application.dtos.DeviceSelectionDto;
import org.healthnet.backend.devices.application.dtos.FetchedDeviceDto;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class DeviceSelectAllWebHandler implements WebHandler {
    private final Supplier<Set<FetchedDeviceDto>> selectAllDevicesService;
    private final Function<Set<FetchedDeviceDto>, String> serialization;

    public DeviceSelectAllWebHandler(Supplier<Set<FetchedDeviceDto>> selectAllDevicesService, Function<Set<FetchedDeviceDto>, String> serialization) {
        this.selectAllDevicesService = selectAllDevicesService;
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
        return new WebResponse(WebResponse.Status.OK, serialization.apply(selectAllDevicesService.get()));
    }
}