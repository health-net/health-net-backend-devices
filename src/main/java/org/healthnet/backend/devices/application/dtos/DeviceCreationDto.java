package org.healthnet.backend.devices.application.dtos;

public class DeviceCreationDto extends DeviceDto{
    public final String name;

    public DeviceCreationDto(String id, String name) {
        super(id);
        this.name = name;
    }
}
