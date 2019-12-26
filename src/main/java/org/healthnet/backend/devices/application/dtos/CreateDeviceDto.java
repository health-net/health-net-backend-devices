package org.healthnet.backend.devices.application.dtos;

public class CreateDeviceDto {
    public final String id;
    public final String name;

    public CreateDeviceDto(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
