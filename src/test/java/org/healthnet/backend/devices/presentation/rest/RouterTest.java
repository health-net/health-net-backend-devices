package org.healthnet.backend.devices.presentation.rest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RouterTest {
    private final static WebRequest webRequest = mock(WebRequest.class);
    private final static WebResponse expectedWebResponse = mock(WebResponse.class);
    private final static WebHandler createPatientWebHandler = mock(WebHandler.class);
    private final static Router router = new Router(createPatientWebHandler);

    @Test
    void Handle_PostOnRoot_CreateDeviceWebHandlerResponseHasBeenReturned() {
        when(webRequest.getPath()).thenReturn("/");
        when(webRequest.getMethod()).thenReturn("POST");
        when(createPatientWebHandler.handle(webRequest)).thenReturn(expectedWebResponse);

        WebResponse webResponse = router.handle(webRequest);
        assertEquals(expectedWebResponse, webResponse);
    }

    @Test
    void Handle_PostOnNotExistingPath_NotFoundResponseHasBeenReturned() {
        when(webRequest.getPath()).thenReturn("/foo");
        when(webRequest.getMethod()).thenReturn("POST");

        WebResponse webResponse = router.handle(webRequest);
        assertEquals(WebResponse.Status.NOT_FOUND, webResponse.getStatus());
    }
}
