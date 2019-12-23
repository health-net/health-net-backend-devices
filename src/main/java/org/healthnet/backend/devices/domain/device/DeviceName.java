package org.healthnet.backend.devices.domain.device;

import org.healthnet.backend.devices.domain.shared.ValueObject;

import java.util.Objects;

public class DeviceName extends ValueObject<DeviceName> {
    private final String value;

    public DeviceName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceName)) return false;
        DeviceName name = (DeviceName) o;
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
