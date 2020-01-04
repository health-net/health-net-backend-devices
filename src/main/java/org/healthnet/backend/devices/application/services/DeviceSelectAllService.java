package org.healthnet.backend.devices.application.services;

import org.healthnet.backend.devices.application.dtos.DeviceCreationDto;
import org.healthnet.backend.devices.application.dtos.DeviceSelectionDto;
import org.healthnet.backend.devices.application.dtos.FetchedDeviceDto;
import org.healthnet.backend.devices.domain.device.Device;
import org.healthnet.backend.devices.domain.device.DeviceId;
import org.healthnet.backend.devices.domain.device.DeviceRepository;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DeviceSelectAllService implements Supplier<Set<FetchedDeviceDto>> {
    private final DeviceRepository deviceRepository;

    public DeviceSelectAllService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Set<FetchedDeviceDto> get() {
        Set<Device> tmp = deviceRepository.getAll();
        return tmp.stream().map(device -> new FetchedDeviceDto(device.getId().getValue(), device.getInfo().getName().getValue())).collect(Collectors.toSet());
    }
}