package org.healthnet.backend.devices.domain.device;

import org.healthnet.backend.devices.domain.shared.ValueObject;

import java.util.Objects;

public class DeviceInfo extends ValueObject<DeviceInfo> {
    private final DeviceId id;
    private final DeviceName name;

    public DeviceInfo(DeviceId id, DeviceName name) {
        this.id = id;
        this.name = name;
    }

    public DeviceId getId() {
        return id;
    }

    public DeviceName getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceInfo)) return false;
        DeviceInfo that = (DeviceInfo) o;
        return id.equals(that.id) &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
