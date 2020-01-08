package org.healthnet.backend.devices.presentation.rest;

import org.healthnet.backend.devices.application.dtos.DeviceDetailDto;
import org.healthnet.backend.devices.application.dtos.DeviceSelectionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;

public class DeviceDetailWebHandlerTest {
    private final static WebRequest webRequest = mock(WebRequest.class);
    private final static DeviceSelectionDto deviceSelectionDto = mock(DeviceSelectionDto.class);
    private final static DeviceDetailDto deviceDetailDto = mock(DeviceDetailDto.class);
    private final static Function<WebRequest, DeviceSelectionDto> deserialization = mock(Function.class);
    private final static Function<String, DeviceDetailDto> service = mock(Function.class);
    private final static Function<DeviceDetailDto, String> serialization= mock(Function.class);

    private final static WebHandler selectDeviceWebHandler = new DeviceDetailWebHandler(service, deserialization, serialization);

    @BeforeEach
    void resetMocks() {
        reset(deviceSelectionDto, deviceDetailDto, deserialization, service, serialization);
    }

    @Test
    void Handle_SuccessfulExecution_SelectResponseHasBeenReturned() {
        String expected = "expected";
        String id = "bruh";
        when(deserialization.apply(webRequest)).thenReturn(deviceSelectionDto);
        when(deviceSelectionDto.getId()).thenReturn(id);
        when(service.apply(id)).thenReturn(deviceDetailDto);

        when(serialization.apply(deviceDetailDto)).thenReturn(expected);

        WebResponse response = selectDeviceWebHandler.handle(webRequest);
        assertEquals(WebResponse.Status.OK, response.getStatus());
        assertEquals(expected, response.getBodyContent());
    }

    @Test
    void Handle_InvalidRequestBodySyntax_NonexistantElementResponseHasBeenReturned() {
        when(deserialization.apply(webRequest)).thenThrow(NoSuchElementException.class);

        WebResponse webResponse = selectDeviceWebHandler.handle(webRequest);
        verifyNoInteractions(service);
        assertEquals(WebResponse.Status.NOT_FOUND, webResponse.getStatus());
    }

    @Test
    void Handle_AnUnknownErrorOccurs_InternalServerErrorResponseHasBeenReturned() {
        when(deserialization.apply(webRequest)).thenThrow(RuntimeException.class);

        WebResponse webResponse = selectDeviceWebHandler.handle(webRequest);
        verifyNoInteractions(service);
        assertEquals(WebResponse.Status.INTERNAL_SERVER_ERROR, webResponse.getStatus());
    }
}
