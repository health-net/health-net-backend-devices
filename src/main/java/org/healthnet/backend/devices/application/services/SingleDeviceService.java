package org.healthnet.backend.devices.application.services;

import org.healthnet.backend.devices.domain.device.Device;
import org.healthnet.backend.devices.domain.device.DeviceRepository;

import java.util.function.Function;

public final class SingleDeviceService implements Function<String, Device> {
    private final DeviceRepository deviceRepository;

    public SingleDeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Device apply(String deviceId) {
        return deviceRepository.getOneByDeviceId(deviceId);
    }
}