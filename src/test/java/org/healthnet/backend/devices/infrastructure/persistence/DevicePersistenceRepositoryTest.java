package org.healthnet.backend.devices.infrastructure.persistence;

import org.healthnet.backend.devices.domain.device.Device;
import org.healthnet.backend.devices.domain.device.DeviceId;
import org.healthnet.backend.devices.domain.device.DeviceInfo;
import org.healthnet.backend.devices.domain.device.DeviceRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DevicePersistenceRepositoryTest {
    private final static Device device = mock(Device.class);
    private final static DeviceInfoDataMapper deviceInfoDataMapper = mock(DeviceInfoDataMapper.class);
    private final static DeviceRepository deviceRepository = new DevicePersistenceRepository(deviceInfoDataMapper);

    @Test
    void Add_SuccessfulExecution_DeviceHasBeenAdded() {
        deviceRepository.add(device);
        verify(deviceInfoDataMapper).insert(device.getInfo());
    }

    @Test
    void Add_DeviceAlreadyExist_IllegalStateExceptionHasBeenThrown() {
        DeviceId id = mock(DeviceId.class);
        DeviceInfo info = mock(DeviceInfo.class);
        when(device.getId()).thenReturn(id);
        when(device.getInfo()).thenReturn(info);
        when(deviceInfoDataMapper.select(id)).thenReturn(Optional.of(info));
        assertThrows(IllegalStateException.class, () -> deviceRepository.add(device));
    }
}
