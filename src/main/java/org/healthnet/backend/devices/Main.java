package org.healthnet.backend.devices;

import com.google.gson.Gson;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.healthnet.backend.devices.application.dtos.DeviceCreationDto;
import org.healthnet.backend.devices.application.dtos.DeviceSelectionDto;
import org.healthnet.backend.devices.application.dtos.DeviceDetailDto;
import org.healthnet.backend.devices.application.services.DeviceCreationService;
import org.healthnet.backend.devices.application.services.DeviceRegisterService;
import org.healthnet.backend.devices.application.services.DeviceDetailService;
import org.healthnet.backend.devices.domain.device.*;
import org.healthnet.backend.devices.infrastructure.persistence.DeviceInfoDataMapper;
import org.healthnet.backend.devices.infrastructure.persistence.DeviceInfoRdbmsDataMapper;
import org.healthnet.backend.devices.infrastructure.persistence.DevicePersistenceRepository;
import org.healthnet.backend.devices.infrastructure.persistence.tools.DevicesDataSource;
import org.healthnet.backend.devices.presentation.rest.*;
import org.healthnet.backend.devices.presentation.rest.mappers.Deserializer;
import org.healthnet.backend.devices.presentation.rest.mappers.DeviceDetailDtoToJSON;
import org.healthnet.backend.devices.presentation.rest.mappers.JSONToDeviceCreationDto;
import org.healthnet.backend.devices.presentation.rest.mappers.Serializer;
import org.healthnet.backend.devices.presentation.tools.jetty.DevicesServlet;
import org.healthnet.backend.devices.presentation.tools.jetty.JettyEmbeddedServer;

import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = new DevicesDataSource();

        DeviceInfoDataMapper deviceInfoDataMapper = new DeviceInfoRdbmsDataMapper(dataSource);

        DeviceRepository deviceRepository = new DevicePersistenceRepository(deviceInfoDataMapper);

        Consumer<DeviceCreationDto> createDeviceService = new DeviceCreationService(
                deviceRepository,
                createDeviceDto -> new Device(new DeviceInfo(
                        new DeviceId(createDeviceDto.getId()),
                        new DeviceName(createDeviceDto.name)
                ))
        );

        Deserializer<DeviceCreationDto> JSONToDto = new JSONToDeviceCreationDto();
        WebHandler createDeviceWebHandler = new DeviceCreationWebHandler(
                createDeviceService,
                JSONToDto.get()
        );

        Function<String, DeviceDetailDto> selectDeviceByIDService = new DeviceDetailService(deviceRepository);
        Serializer<DeviceDetailDto> dtoToJSON = new DeviceDetailDtoToJSON();
        WebHandler selectDeviceByIDWebHandler = new DeviceDetailWebHandler(
                selectDeviceByIDService,
                webRequest -> new DeviceSelectionDto(webRequest.getPath().substring(1), null),
                dtoToJSON.get()
        );

        Supplier<Set<DeviceDetailDto>> selectAllDevicesService = new DeviceRegisterService(deviceRepository);


        WebHandler selectAllDevicesWebHandler = new DevicesRegisterWebHandler(selectAllDevicesService, set -> new Gson().toJson(set));

        WebHandler router = new DeviceRouter(createDeviceWebHandler, selectDeviceByIDWebHandler, selectAllDevicesWebHandler);

        HttpServlet devicesServlet = new DevicesServlet(router);

        int port = Integer.parseInt(System.getenv().getOrDefault("HEALTHNET_PORT", "8080"));
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(devicesServlet), "/devices/*");
        JettyEmbeddedServer server = new JettyEmbeddedServer(port, servletContextHandler);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
