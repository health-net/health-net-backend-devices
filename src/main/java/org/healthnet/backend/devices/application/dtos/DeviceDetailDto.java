package org.healthnet.backend.devices.application.dtos;

import java.io.Serializable;

public class DeviceDetailDto extends DeviceDto implements Serializable {
    public final String name;

    public DeviceDetailDto(String id, String name) {
        super(id);
        this.name = name;
    }
}