package org.healthnet.backend.devices.domain.device;

import org.healthnet.backend.devices.domain.shared.ValueObject;

import java.util.Objects;

public class DeviceId extends ValueObject<DeviceId> {
    private final String value;

    public DeviceId(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceId)) return false;
        DeviceId id = (DeviceId) o;
        return Objects.equals(value, id.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
