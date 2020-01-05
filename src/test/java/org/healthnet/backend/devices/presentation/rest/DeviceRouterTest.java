package org.healthnet.backend.devices.presentation.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeviceRouterTest {
    private final static WebRequest webRequest = mock(WebRequest.class);
    private final static WebResponse expectedWebResponse = mock(WebResponse.class);
    private final static WebHandler creationWebHandler = mock(WebHandler.class);
    private final static WebHandler detailWebHandler = mock(WebHandler.class);
    private final static WebHandler registerWebHandler = mock(WebHandler.class);
    private final static DeviceRouter deviceRouter = new DeviceRouter(
            creationWebHandler,
            detailWebHandler,
            registerWebHandler
    );

    @BeforeEach
    void resetMocks() {
        reset(webRequest, expectedWebResponse, creationWebHandler, detailWebHandler, registerWebHandler);
    }

    @Test
    void Handle_PostOnRoot_DeviceCreationWebHandlerResponseHasBeenReturned() {
        when(webRequest.is("POST", "(/?)$")).thenReturn(true);
        when(creationWebHandler.handle(webRequest)).thenReturn(expectedWebResponse);

        WebResponse webResponse = deviceRouter.handle(webRequest);
        assertEquals(expectedWebResponse, webResponse);
    }

    @Test
    void Handle_GetOnRoot_DeviceRegisterWebHandlerResponseHasBeenReturned() {
        when(webRequest.is("GET", "(/?)$")).thenReturn(true);
        when(registerWebHandler.handle(webRequest)).thenReturn(expectedWebResponse);

        WebResponse webResponse = deviceRouter.handle(webRequest);
        assertEquals(expectedWebResponse, webResponse);
    }

    @Test
    void Handle_GetOnDeviceId_DeviceDetailWebHandlerResponseHasBeenReturned() {
        when(webRequest.is("GET", "(/)(.+)(/?)$")).thenReturn(true);
        when(detailWebHandler.handle(webRequest)).thenReturn(expectedWebResponse);

        WebResponse webResponse = deviceRouter.handle(webRequest);
        assertEquals(expectedWebResponse, webResponse);
    }

    @Test
    void Handle_PostOnNotExistingPath_NotFoundResponseHasBeenReturned() {
        when(webRequest.getPath()).thenReturn("/foo");
        when(webRequest.getMethod()).thenReturn("POST");

        WebResponse webResponse = deviceRouter.handle(webRequest);
        assertEquals(WebResponse.Status.NOT_FOUND, webResponse.getStatus());
    }
}
