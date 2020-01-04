package org.healthnet.backend.devices.application.services;

import org.healthnet.backend.devices.application.dtos.DeviceCreationDto;
import org.healthnet.backend.devices.domain.device.Device;
import org.healthnet.backend.devices.domain.device.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public class DeviceCreationServiceTest {
    private final static DeviceCreationDto deviceCreationDto = mock(DeviceCreationDto.class);
    private final static Device device = mock(Device.class);
    private final static Function<DeviceCreationDto, Device> deviceFactory = mock(Function.class);
    private final static DeviceRepository deviceRepository = mock(DeviceRepository.class);
    private final static Consumer<DeviceCreationDto> createDeviceService = new DeviceCreationService(deviceRepository, deviceFactory);

    @BeforeEach
    void resetMocks() {
        reset(deviceFactory, deviceRepository);
    }

    @Test
    void Accept_SuccessfulExecution_DeviceHasBeenStored() {
        when(deviceFactory.apply(deviceCreationDto)).thenReturn(device);
        createDeviceService.accept(deviceCreationDto);
        verify(deviceRepository).add(device);
    }

    @Test
    void Accept_DeviceAlreadyExist_IllegalStateExceptionHasBeenThrown() {
        when(deviceFactory.apply(deviceCreationDto)).thenReturn(device);
        doThrow(IllegalStateException.class).when(deviceRepository).add(device);

        assertThrows(IllegalStateException.class, () -> createDeviceService.accept(deviceCreationDto));
    }

    @Test
    void Accept_InvalidDeviceData_IllegalArgumentExceptionHasBeenThrown() {
        when(deviceFactory.apply(deviceCreationDto)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> createDeviceService.accept(deviceCreationDto));
        verifyNoInteractions(deviceRepository);
    }
}
