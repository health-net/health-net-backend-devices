package org.healthnet.backend.devices.presentation.rest;

import org.healthnet.backend.devices.application.dtos.CreateDeviceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public class CreateDeviceWebHandlerTest {
    private final static WebRequest webRequest = mock(WebRequest.class);
    private final static CreateDeviceDto createDeviceDto = mock(CreateDeviceDto.class);
    private final static Function<WebRequest, CreateDeviceDto> deserialization = mock(Function.class);
    private final static Consumer<CreateDeviceDto> storage = mock(Consumer.class);

    private final static WebHandler createDeviceWebHandler = new CreateDeviceWebHandler(storage, deserialization);

    @BeforeEach
    void resetMocks() {
        reset(deserialization, storage);
    }

    @Test
    void Handle_SuccessfulExecution_CreatedResponseHasBeenReturned() {
        when(deserialization.apply(webRequest)).thenReturn(createDeviceDto);
        doNothing().when(storage).accept(createDeviceDto);

        WebResponse response = createDeviceWebHandler.handle(webRequest);
        assertEquals(WebResponse.Status.CREATED, response.getStatus());
    }

    @Test
    void Handle_InvalidRequestBodySyntax_BadRequestResponseHasBeenReturned() {
        when(deserialization.apply(webRequest)).thenThrow(IllegalArgumentException.class);

        WebResponse webResponse = createDeviceWebHandler.handle(webRequest);
        verifyNoInteractions(storage);
        assertEquals(WebResponse.Status.BAD_REQUEST, webResponse.getStatus());
    }

    @Test
    void Handle_InvalidDeviceData_BadRequestResponseHasBeenReturned() {
        when(deserialization.apply(webRequest)).thenReturn(createDeviceDto);
        doThrow(IllegalArgumentException.class).when(storage).accept(createDeviceDto);

        WebResponse webResponse = createDeviceWebHandler.handle(webRequest);
        assertEquals(WebResponse.Status.BAD_REQUEST, webResponse.getStatus());
    }

    @Test
    void Handle_DeviceAlreadyExist_ConflictResponseHasBeenReturned() {
        when(deserialization.apply(webRequest)).thenReturn(createDeviceDto);
        doThrow(IllegalStateException.class).when(storage).accept(createDeviceDto);

        WebResponse webResponse = createDeviceWebHandler.handle(webRequest);
        assertEquals(WebResponse.Status.CONFLICT, webResponse.getStatus());
    }

    @Test
    void Handle_AnUnknownErrorOccurs_InternalServerErrorResponseHasBeenReturned() {
        when(deserialization.apply(webRequest)).thenThrow(RuntimeException.class);

        WebResponse webResponse = createDeviceWebHandler.handle(webRequest);
        verifyNoInteractions(storage);
        assertEquals(WebResponse.Status.INTERNAL_SERVER_ERROR, webResponse.getStatus());
    }
}
