package org.healthnet.backend.devices.application.dtos;

public class DeviceCreationDto {
    public final String id;
    public final String name;

    public DeviceCreationDto(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
