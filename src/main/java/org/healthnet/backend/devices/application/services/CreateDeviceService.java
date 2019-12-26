package org.healthnet.backend.devices.application.services;

import org.healthnet.backend.devices.application.dtos.CreateDeviceDto;
import org.healthnet.backend.devices.domain.device.Device;
import org.healthnet.backend.devices.domain.device.DeviceRepository;

import java.util.function.Consumer;
import java.util.function.Function;

public class CreateDeviceService implements Consumer<CreateDeviceDto> {
    private final DeviceRepository deviceRepository;
    private final Function<CreateDeviceDto, Device> deviceFactory;

    public CreateDeviceService(DeviceRepository deviceRepository, Function<CreateDeviceDto, Device> deviceFactory) {
        this.deviceRepository = deviceRepository;
        this.deviceFactory = deviceFactory;
    }

    @Override
    public void accept(CreateDeviceDto createDeviceDto) {
        Device device = deviceFactory.apply(createDeviceDto);
        deviceRepository.add(device);
    }
}
