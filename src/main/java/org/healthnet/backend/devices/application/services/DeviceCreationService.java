package org.healthnet.backend.devices.application.services;

import org.healthnet.backend.devices.application.dtos.DeviceCreationDto;
import org.healthnet.backend.devices.domain.device.Device;
import org.healthnet.backend.devices.domain.device.DeviceRepository;

import java.util.function.Consumer;
import java.util.function.Function;

public class DeviceCreationService implements Consumer<DeviceCreationDto> {
    private final DeviceRepository deviceRepository;
    private final Function<DeviceCreationDto, Device> deviceFactory;

    public DeviceCreationService(DeviceRepository deviceRepository, Function<DeviceCreationDto, Device> deviceFactory) {
        this.deviceRepository = deviceRepository;
        this.deviceFactory = deviceFactory;
    }

    @Override
    public void accept(DeviceCreationDto deviceCreationDto) {
        Device device = deviceFactory.apply(deviceCreationDto);
        deviceRepository.add(device);
    }
}
