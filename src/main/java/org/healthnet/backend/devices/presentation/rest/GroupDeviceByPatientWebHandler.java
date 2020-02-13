package org.healthnet.backend.devices.presentation.rest;

import org.healthnet.backend.devices.domain.device.Device;

import java.util.List;
import java.util.function.Function;

public class GroupDeviceByPatientWebHandler extends WebHandler {
    private final Function<String, List<Device>> groupDeviceByPatientService;
    private final WebResponseFactory webResponseFactory;

    public GroupDeviceByPatientWebHandler(Function<String, List<Device>> groupDeviceByPatientService, WebResponseFactory webResponseFactory) {
        this.groupDeviceByPatientService = groupDeviceByPatientService;
        this.webResponseFactory = webResponseFactory;
    }

    @Override
    public WebResponse handle(WebRequest webRequest) {
        try {
            String patientId = webRequest.getQueryParameter("patientId");
            List<Device> devices = groupDeviceByPatientService.apply(patientId);
            return webResponseFactory.createOk(devices);
        } catch (Exception e) {
            return webResponseFactory.createInternalServerError("Internal server error");
        }
    }
}
