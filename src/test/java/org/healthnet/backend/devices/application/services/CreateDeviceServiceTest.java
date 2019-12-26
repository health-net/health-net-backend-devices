package org.healthnet.backend.devices.application.services;

import org.healthnet.backend.devices.application.dtos.CreateDeviceDto;
import org.healthnet.backend.devices.domain.device.Device;
import org.healthnet.backend.devices.domain.device.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public class CreateDeviceServiceTest {
    private final static CreateDeviceDto createDeviceDto = mock(CreateDeviceDto.class);
    private final static Device device = mock(Device.class);
    private final static Function<CreateDeviceDto, Device> deviceFactory = mock(Function.class);
    private final static DeviceRepository deviceRepository = mock(DeviceRepository.class);
    private final static Consumer<CreateDeviceDto> createDeviceService = new CreateDeviceService(deviceRepository, deviceFactory);

    @BeforeEach
    void resetMocks() {
        reset(deviceFactory, deviceRepository);
    }

    @Test
    void Accept_SuccessfulExecution_DeviceHasBeenStored() {
        when(deviceFactory.apply(createDeviceDto)).thenReturn(device);
        createDeviceService.accept(createDeviceDto);
        verify(deviceRepository).add(device);
    }

    @Test
    void Accept_DeviceAlreadyExist_IllegalStateExceptionHasBeenThrown() {
        when(deviceFactory.apply(createDeviceDto)).thenReturn(device);
        doThrow(IllegalStateException.class).when(deviceRepository).add(device);

        assertThrows(IllegalStateException.class, () -> createDeviceService.accept(createDeviceDto));
    }

    @Test
    void Accept_InvalidDeviceData_IllegalArgumentExceptionHasBeenThrown() {
        when(deviceFactory.apply(createDeviceDto)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> createDeviceService.accept(createDeviceDto));
        verifyNoInteractions(deviceRepository);
    }
}
