package org.healthnet.backend.devices.application.services;

import org.healthnet.backend.devices.application.dtos.DeviceCreationDto;
import org.healthnet.backend.devices.application.dtos.DeviceSelectionDto;
import org.healthnet.backend.devices.application.dtos.FetchedDeviceDto;
import org.healthnet.backend.devices.domain.device.Device;
import org.healthnet.backend.devices.domain.device.DeviceId;
import org.healthnet.backend.devices.domain.device.DeviceRepository;

import java.util.function.Consumer;
import java.util.function.Function;

public class DeviceSelectionByIDService implements Function<DeviceSelectionDto, FetchedDeviceDto> {
    private final DeviceRepository deviceRepository;

    public DeviceSelectionByIDService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public FetchedDeviceDto apply(DeviceSelectionDto deviceSelectionDto) {
        Device tmp = deviceRepository.getByID(new DeviceId(deviceSelectionDto.id));
        return new FetchedDeviceDto(tmp.getId().getValue(), tmp.getInfo().getName().getValue());
    }
}