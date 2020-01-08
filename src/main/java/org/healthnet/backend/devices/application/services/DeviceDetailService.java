package org.healthnet.backend.devices.application.services;

import org.healthnet.backend.devices.application.dtos.DeviceSelectionDto;
import org.healthnet.backend.devices.application.dtos.DeviceDetailDto;
import org.healthnet.backend.devices.domain.device.Device;
import org.healthnet.backend.devices.domain.device.DeviceId;
import org.healthnet.backend.devices.domain.device.DeviceRepository;

import java.util.function.Function;

public class DeviceDetailService implements Function<String, DeviceDetailDto> {
    private final DeviceRepository deviceRepository;

    public DeviceDetailService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public DeviceDetailDto apply(String deviceId) {
        Device tmp = deviceRepository.getByID(new DeviceId(deviceId));
        return new DeviceDetailDto(tmp.getId().getValue(), tmp.getInfo().getName().getValue());
    }
}