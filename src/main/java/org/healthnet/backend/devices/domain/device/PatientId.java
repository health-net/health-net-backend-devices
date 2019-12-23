package org.healthnet.backend.devices.domain.device;

import java.util.Objects;

public class PatientId {
    private final String id;

    public PatientId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientId)) return false;
        PatientId id1 = (PatientId) o;
        return Objects.equals(id, id1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
