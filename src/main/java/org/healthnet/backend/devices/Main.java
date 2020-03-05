package org.healthnet.backend.devices;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.healthnet.backend.devices.application.services.GroupDeviceByPatientService;
import org.healthnet.backend.devices.application.services.SingleDeviceService;
import org.healthnet.backend.devices.domain.device.Device;
import org.healthnet.backend.devices.domain.device.DeviceRepository;
import org.healthnet.backend.devices.infrastructure.persistence.mqtt.MqttBroker;
import org.healthnet.backend.devices.infrastructure.persistence.mqtt.MqttDeviceRepository;
import org.healthnet.backend.devices.infrastructure.persistence.mqtt.MqttTopic;
import org.healthnet.backend.devices.presentation.rest.*;
import org.healthnet.backend.devices.presentation.tools.jetty.DevicesServlet;
import org.healthnet.backend.devices.presentation.tools.jetty.JettyEmbeddedServer;

import javax.servlet.http.HttpServlet;
import java.util.List;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        MqttBroker broker = new MqttBroker(System.getenv().getOrDefault("BROKER_HOST", "localhost"), Integer.parseInt(System.getenv().getOrDefault("BROKER_PORT", "1883")));
        DeviceRepository deviceRepository = new MqttDeviceRepository(new MqttTopic("healthnet", "devices"), broker);
        Function<String, Device> singleDeviceService = new SingleDeviceService(deviceRepository);
        Function<String, List<Device>> groupDeviceByPatientService = new GroupDeviceByPatientService(deviceRepository);
        WebResponseFactory webResponseFactory = new JsonWebResponseFactory();
        WebHandler singleDeviceWebHandler = new SingleDeviceWebHandler(0, singleDeviceService, webResponseFactory);
        WebHandler groupDeviceByPatientWebHandler = new GroupDeviceByPatientWebHandler(groupDeviceByPatientService, webResponseFactory);
        HttpServlet devicesServlet = new DevicesServlet(singleDeviceWebHandler, groupDeviceByPatientWebHandler, webResponseFactory);
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(devicesServlet), "/devices/*");
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        JettyEmbeddedServer server = new JettyEmbeddedServer(port, servletContextHandler);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
