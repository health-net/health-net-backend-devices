package org.healthnet.backend.devices.domain.device;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

public class DeviceTest {
    private final static DeviceId deviceId = mock(DeviceId.class);
    private final static DeviceName deviceName = mock(DeviceName.class);
    private final static Patient patient = mock(Patient.class);

    @Test
    void Attach_DeviceIsNotAttachedToAnyPatient_PatientHasBeenAttached() {
        Device device = Device.detached(deviceId, deviceName);
        device.attach(patient);
        assertTrue(device.getAttachedPatient().isPresent());
        assertEquals(patient, device.getAttachedPatient().get());
    }

    @Test
    void Attach_DeviceIsAttachedToPatient_IllegalStateExceptionHasBeenThrown() {
        assertThrows(IllegalStateException.class, () -> {
            Device device = Device.attached(deviceId, deviceName, patient);
            device.attach(patient);
        });
    }

    @Test
    void Detach_DeviceIsAttachedToPatient_PatientHasBeenDetached() {
        Device device = Device.attached(deviceId, deviceName, patient);
        device.detach();
        assertTrue(device.getAttachedPatient().isEmpty());
    }

    @Test
    void Detach_DeviceIsNotAttachedToAnyPatient_IllegalStateExceptionHasBeenThrown() {
        assertThrows(IllegalStateException.class, () -> {
            Device device = Device.detached(deviceId, deviceName);
            device.detach();
        });
    }
}
