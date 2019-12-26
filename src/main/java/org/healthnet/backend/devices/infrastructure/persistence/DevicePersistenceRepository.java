package org.healthnet.backend.devices.infrastructure.persistence;

import org.healthnet.backend.devices.domain.device.Device;
import org.healthnet.backend.devices.domain.device.DeviceRepository;

public class DevicePersistenceRepository extends DeviceRepository {
    private final DeviceInfoDataMapper deviceInfoDataMapper;

    public DevicePersistenceRepository(DeviceInfoDataMapper deviceInfoDataMapper) {
        this.deviceInfoDataMapper = deviceInfoDataMapper;
    }

    @Override
    public void add(Device device) {
        if(contains(device)) {
            throw new IllegalStateException();
        }
        deviceInfoDataMapper.insert(device.getInfo());
    }

    private boolean contains(Device device) {
        return deviceInfoDataMapper.select(device.getId()).isPresent();
    }
}
