package org.healthnet.backend.devices.presentation.rest;

import org.healthnet.backend.devices.application.dtos.CreateDeviceDto;

import java.util.function.Consumer;
import java.util.function.Function;

public class CreateDeviceWebHandler implements WebHandler {
    private final Consumer<CreateDeviceDto> createDeviceService;
    private final Function<WebRequest, CreateDeviceDto> deserialization;

    public CreateDeviceWebHandler(Consumer<CreateDeviceDto> createDeviceService,
                                  Function<WebRequest, CreateDeviceDto> deserialization) {
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
        CreateDeviceDto createDeviceDto = deserialization.apply(webRequest);
        createDeviceService.accept(createDeviceDto);
        return new WebResponse(WebResponse.Status.CREATED);
    }
}
