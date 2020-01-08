package org.healthnet.backend.devices.presentation.rest.mappers;

import org.healthnet.backend.devices.application.dtos.DeviceDto;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Serializer<T extends Serializable> extends Supplier<Function<T, String>> {
}
