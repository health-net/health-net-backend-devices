package org.healthnet.backend.devices.presentation.rest;

import org.healthnet.backend.devices.application.dtos.DeviceCreationDto;
import org.healthnet.backend.devices.application.dtos.DeviceDetailDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

public class DeviceRegisterWebHandlerTest {
    private final static WebRequest webRequest = mock(WebRequest.class);
    private final static Function<Set<DeviceDetailDto>, String> serialization = mock(Function.class);
    private final static Supplier<Set<DeviceDetailDto>> storage = mock(Supplier.class);
    private final static Set<DeviceDetailDto> responseSet = mock(Set.class);
    private final static WebHandler createDeviceWebHandler = new DevicesRegisterWebHandler(storage, serialization);

    @BeforeEach
    void resetMocks() {
        reset(serialization, storage);
    }

    @Test
    void Handle_SuccessfulExecution_AllDevicesResponseHasBeenReturned() {
        String expected = "expected";
        when(storage.get()).thenReturn(responseSet);
        when(serialization.apply(responseSet)).thenReturn(expected);
        WebResponse response = createDeviceWebHandler.handle(webRequest);

        assertEquals(WebResponse.Status.OK, response.getStatus());
        assertEquals(response.getBodyContent(), expected);
    }
}
