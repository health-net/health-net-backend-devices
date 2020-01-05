package org.healthnet.backend.devices.application.dtos;

public class DeviceDetailDto {
    public final String id;
    public final String name;

    public DeviceDetailDto(String id, String name) {
        this.id = id;
        this.name = name;
    }
}