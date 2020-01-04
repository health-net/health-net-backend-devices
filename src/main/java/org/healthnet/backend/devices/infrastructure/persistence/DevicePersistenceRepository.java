package org.healthnet.backend.devices.infrastructure.persistence;

import org.healthnet.backend.devices.domain.device.Device;
import org.healthnet.backend.devices.domain.device.DeviceId;
import org.healthnet.backend.devices.domain.device.DeviceInfo;
import org.healthnet.backend.devices.domain.device.DeviceRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public Device getByID(DeviceId id) {
        Optional<DeviceInfo> result = deviceInfoDataMapper.select(id);
        if(result.isEmpty()) throw new IllegalArgumentException();
        else return new Device(result.get());
    }

    @Override
    public Set<Device> getAll() {
        return deviceInfoDataMapper.getAll().stream().map(info -> new Device(info)).collect(Collectors.toSet());
    }

    private boolean contains(Device device) {
        return deviceInfoDataMapper.select(device.getId()).isPresent();
    }
}

