package org.healthnet.backend.devices.application.dtos;

public class FetchedDeviceDto {
    public final String id;
    public final String name;

    public FetchedDeviceDto(String id, String name) {
        this.id = id;
        this.name = name;
    }
}