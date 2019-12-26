package org.healthnet.backend.devices.domain.device;

import org.healthnet.backend.devices.domain.shared.AggregateRoot;

public class Device extends AggregateRoot<Device> {
    private final DeviceInfo info;

    public Device(DeviceInfo info) {
        this.info = info;
    }

    public DeviceId getId() {
        return info.getId();
    }

    public DeviceInfo getInfo() {
        return info;
    }
}
