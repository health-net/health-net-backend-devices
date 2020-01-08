package org.healthnet.backend.devices.application.dtos;

public class DeviceSelectionDto extends DeviceDto {
    public final String name;

    public DeviceSelectionDto(String id, String name) {
        super(id);
        this.name = name;
    }
}