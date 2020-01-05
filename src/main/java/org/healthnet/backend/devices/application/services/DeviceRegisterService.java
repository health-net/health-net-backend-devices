package org.healthnet.backend.devices.application.services;

import org.healthnet.backend.devices.application.dtos.DeviceDetailDto;
import org.healthnet.backend.devices.domain.device.Device;
import org.healthnet.backend.devices.domain.device.DeviceRepository;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DeviceRegisterService implements Supplier<Set<DeviceDetailDto>> {
    private final DeviceRepository deviceRepository;

    public DeviceRegisterService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Set<DeviceDetailDto> get() {
        Set<Device> tmp = deviceRepository.getAll();
        return tmp.stream().map(device -> new DeviceDetailDto(device.getId().getValue(), device.getInfo().getName().getValue())).collect(Collectors.toSet());
    }
}