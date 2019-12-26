package org.healthnet.backend.devices.domain.device;

import org.healthnet.backend.devices.domain.shared.Repository;

public abstract class DeviceRepository extends Repository<Device> {
    public abstract void add(Device device);
}
