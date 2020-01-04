package org.healthnet.backend.devices.domain.device;

import org.healthnet.backend.devices.domain.shared.Repository;

import java.util.Set;

public abstract class DeviceRepository extends Repository<Device> {
    public abstract void add(Device device);
    public abstract Device getByID(DeviceId id);
    public abstract Set<Device> getAll();
}
