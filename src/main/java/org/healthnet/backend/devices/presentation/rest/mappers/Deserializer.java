package org.healthnet.backend.devices.presentation.rest.mappers;

import org.healthnet.backend.devices.application.dtos.DeviceDto;
import org.healthnet.backend.devices.presentation.rest.WebRequest;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Deserializer<T extends Serializable> extends Supplier<Function<WebRequest, T>> {
}
