package org.healthnet.backend.devices.presentation.rest.mappers;

import com.google.gson.Gson;
import org.healthnet.backend.devices.application.dtos.DeviceDetailDto;
import org.healthnet.backend.devices.application.dtos.DeviceDto;

import java.util.function.Function;

public class DeviceDetailDtoToJSON implements Serializer<DeviceDetailDto> {
    @Override
    public Function<DeviceDetailDto, String> get() {
        return fetchDto -> new Gson().toJson(fetchDto, DeviceDetailDto.class);
    }
}
