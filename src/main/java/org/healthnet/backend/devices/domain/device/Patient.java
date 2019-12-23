package org.healthnet.backend.devices.domain.device;

import org.healthnet.backend.devices.domain.shared.Entity;

import java.util.Objects;

public class Patient extends Entity<Patient> {
    private final PatientId id;

    public Patient(PatientId id) {
        if(Objects.isNull(id)) {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }

    public PatientId getId() {
        return id;
    }
}
