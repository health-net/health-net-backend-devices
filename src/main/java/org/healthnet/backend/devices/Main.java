package org.healthnet.backend.devices;

import com.google.gson.Gson;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.healthnet.backend.devices.application.dtos.CreateDeviceDto;
import org.healthnet.backend.devices.application.services.CreateDeviceService;
import org.healthnet.backend.devices.domain.device.*;
import org.healthnet.backend.devices.infrastructure.persistence.DeviceInfoDataMapper;
import org.healthnet.backend.devices.infrastructure.persistence.DeviceInfoRdbmsDataMapper;
import org.healthnet.backend.devices.infrastructure.persistence.DevicePersistenceRepository;
import org.healthnet.backend.devices.infrastructure.persistence.tools.DevicesDataSource;
import org.healthnet.backend.devices.presentation.rest.CreateDeviceWebHandler;
import org.healthnet.backend.devices.presentation.rest.Router;
import org.healthnet.backend.devices.presentation.rest.WebHandler;
import org.healthnet.backend.devices.presentation.tools.jetty.DevicesServlet;
import org.healthnet.backend.devices.presentation.tools.jetty.JettyEmbeddedServer;

import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = new DevicesDataSource();

        DeviceInfoDataMapper deviceInfoDataMapper = new DeviceInfoRdbmsDataMapper(dataSource);

        DeviceRepository deviceRepository = new DevicePersistenceRepository(deviceInfoDataMapper);

        Consumer<CreateDeviceDto> createDeviceService = new CreateDeviceService(
                deviceRepository,
                createDeviceDto -> new Device(new DeviceInfo(
                        new DeviceId(createDeviceDto.id),
                        new DeviceName(createDeviceDto.name)
                ))
        );

        WebHandler createDeviceWebHandler = new CreateDeviceWebHandler(
                createDeviceService,
                webRequest -> new Gson().fromJson(webRequest.getBodyContent(), CreateDeviceDto.class)
        );

        WebHandler router = new Router(createDeviceWebHandler);

        HttpServlet devicesServlet = new DevicesServlet(router);

        int port = Integer.parseInt(System.getenv().getOrDefault("HEALTHNET_PORT", "8080"));
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(devicesServlet), "/devices");
        JettyEmbeddedServer server = new JettyEmbeddedServer(port, servletContextHandler);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
