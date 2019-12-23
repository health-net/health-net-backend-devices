package org.healthnet.backend.devices.domain.device;

import org.healthnet.backend.devices.domain.shared.AggregateRoot;

import java.util.Objects;
import java.util.Optional;

public class Device extends AggregateRoot<Device> {
    private final DeviceId id;
    private final DeviceName name;
    private Patient attachedPatient;

    private Device(DeviceId id, DeviceName name, Patient attachedPatient) {
        if(Objects.isNull(id) || Objects.isNull(name)) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.name = name;
        this.attachedPatient = attachedPatient;
    }

    public static Device detached(DeviceId id, DeviceName name) {
        return new Device(id, name, null);
    }

    public static Device attached(DeviceId id, DeviceName name, Patient patient) {
        return new Device(id, name, patient);
    }

    public DeviceId getId() {
        return id;
    }

    public DeviceName getName() {
        return name;
    }

    public void attach(Patient patient) {
        if(getAttachedPatient().isPresent()) {
            throw new IllegalStateException();
        }
        this.attachedPatient = patient;
    }

    public void detach() {
        if(getAttachedPatient().isEmpty()) {
            throw new IllegalStateException();
        }
        attachedPatient = null;
    }

    public Optional<Patient> getAttachedPatient() {
        return Optional.ofNullable(attachedPatient);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device)) return false;
        Device device = (Device) o;
        return Objects.equals(id, device.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
