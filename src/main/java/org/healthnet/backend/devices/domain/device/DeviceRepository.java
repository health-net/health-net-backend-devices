package org.healthnet.backend.devices.domain.device;

import org.healthnet.backend.devices.domain.shared.Repository;

import java.util.List;

public abstract class DeviceRepository extends Repository<Device> {
    public abstract void add(Device device);
    public abstract Device getOneById(DeviceId deviceId);
    public abstract List<Device> getAllAttachedToPatient(PatientId patientId);
}
