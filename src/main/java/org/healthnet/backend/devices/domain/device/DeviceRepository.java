package org.healthnet.backend.devices.domain.device;

import java.util.List;

public abstract class DeviceRepository {
    public abstract Device getOneByDeviceId(String deviceId);
    public abstract List<Device> getManyByPatientId(String patientId);
}
