package org.healthnet.backend.devices.application.dtos;

import java.io.Serializable;

public abstract class DeviceDto implements Serializable {

    private final String id;

    protected DeviceDto(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
