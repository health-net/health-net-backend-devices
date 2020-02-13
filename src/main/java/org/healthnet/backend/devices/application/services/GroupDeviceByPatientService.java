package org.healthnet.backend.devices.application.services;

import org.healthnet.backend.devices.domain.device.Device;
import org.healthnet.backend.devices.domain.device.DeviceRepository;

import java.util.List;
import java.util.function.Function;

public class GroupDeviceByPatientService implements Function<String, List<Device>> {
    private final DeviceRepository deviceRepository;

    public GroupDeviceByPatientService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public List<Device> apply(String patientId) {
        return deviceRepository.getManyByPatientId(patientId);
    }
}
