package org.healthnet.backend.devices.application.dtos;

public class DeviceSelectionDto {
    public final String id;
    public final String name;

    public DeviceSelectionDto(String id, String name) {
        this.id = id;
        this.name = name;
    }
}