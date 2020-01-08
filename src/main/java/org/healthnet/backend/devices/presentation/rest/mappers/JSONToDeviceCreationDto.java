package org.healthnet.backend.devices.presentation.rest.mappers;

import com.google.gson.Gson;
import org.healthnet.backend.devices.application.dtos.DeviceCreationDto;
import org.healthnet.backend.devices.application.dtos.DeviceDetailDto;
import org.healthnet.backend.devices.presentation.rest.WebRequest;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Function;

public class JSONToDeviceCreationDto implements Deserializer<DeviceCreationDto> {
    @Override
    public Function<WebRequest, DeviceCreationDto> get() {
        Function<WebRequest, DeviceCreationDto> tmp = webRequest -> new Gson().fromJson(webRequest.getBodyContent(), DeviceCreationDto.class);
        return tmp;
    }
}
