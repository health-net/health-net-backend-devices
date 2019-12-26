package org.healthnet.backend.devices.infrastructure.persistence;

import org.healthnet.backend.devices.domain.device.DeviceId;
import org.healthnet.backend.devices.domain.device.DeviceInfo;

import java.util.Optional;

public abstract class DeviceInfoDataMapper {
    public abstract void insert(DeviceInfo info);
    public abstract Optional<DeviceInfo> select(DeviceId deviceId);
}