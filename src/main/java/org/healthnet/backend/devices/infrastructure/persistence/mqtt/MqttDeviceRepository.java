package org.healthnet.backend.devices.infrastructure.persistence.mqtt;

import org.healthnet.backend.devices.domain.device.Device;
import org.healthnet.backend.devices.domain.device.DeviceRepository;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public final class MqttDeviceRepository extends DeviceRepository {
    private final MqttTopic topicPrefix;
    private final MqttBroker broker;

    public MqttDeviceRepository(MqttTopic topicPrefix, MqttBroker broker) {
        this.topicPrefix = topicPrefix;
        this.broker = broker;
    }

    @Override
    public Device getOneByDeviceId(String deviceId) {
        try (MqttConnection connection = broker.connect()) {
            CompletableFuture<String> deviceHomie = connection.fetchSinglePacket(topicPrefix.addLevels(deviceId, "€homie"))
                    .thenApply(MqttPacket::getPayloadAsString);
            CompletableFuture<String> deviceName = connection.fetchSinglePacket(topicPrefix.addLevels(deviceId, "€name"))
                    .thenApply(MqttPacket::getPayloadAsString);
            CompletableFuture<String> deviceState = connection.fetchSinglePacket(topicPrefix.addLevels(deviceId, "€state"))
                    .thenApply(MqttPacket::getPayloadAsString);
            String[] nodesId = connection.fetchSinglePacket(topicPrefix.addLevels(deviceId, "€nodes"))
                    .orTimeout(5, TimeUnit.SECONDS)
                    .thenApply(MqttPacket::getPayloadAsString)
                    .thenApply(s -> s.split(","))
                    .join();
            List<Device.Node> nodes = new LinkedList<>();
            for (String nodeId : nodesId) {
                CompletableFuture<String> nodeName = connection.fetchSinglePacket(topicPrefix.addLevels(deviceId, nodeId, "€name"))
                        .thenApply(MqttPacket::getPayloadAsString);
                CompletableFuture<String> nodeType = connection.fetchSinglePacket(topicPrefix.addLevels(deviceId, nodeId, "€type"))
                        .thenApply(MqttPacket::getPayloadAsString);
                String[] propertiesId = connection.fetchSinglePacket(topicPrefix.addLevels(deviceId, nodeId, "€properties"))
                        .orTimeout(5, TimeUnit.SECONDS)
                        .thenApply(MqttPacket::getPayloadAsString)
                        .thenApply(s -> s.split(","))
                        .join();
                List<Device.Node.Property> properties = new LinkedList<>();
                for (String propertyId : propertiesId) {
                    CompletableFuture<String> propertyName = connection.fetchSinglePacket(topicPrefix.addLevels(deviceId, nodeId, propertyId, "€name"))
                            .thenApply(MqttPacket::getPayloadAsString);
                    CompletableFuture<String> propertyDataType = connection.fetchSinglePacket(topicPrefix.addLevels(deviceId, nodeId, propertyId, "€datatype"))
                            .thenApply(MqttPacket::getPayloadAsString);
                    CompletableFuture<String> propertySettable = connection.fetchSinglePacket(topicPrefix.addLevels(deviceId, nodeId, propertyId, "€settable"))
                            .thenApply(MqttPacket::getPayloadAsString);
                    CompletableFuture<String> propertyRetained = connection.fetchSinglePacket(topicPrefix.addLevels(deviceId, nodeId, propertyId, "€retained"))
                            .thenApply(MqttPacket::getPayloadAsString);
                    CompletableFuture<String> propertyUnit = connection.fetchSinglePacket(topicPrefix.addLevels(deviceId, nodeId, propertyId, "€unit"))
                            .thenApply(MqttPacket::getPayloadAsString);
                    CompletableFuture.allOf(propertyName, propertyDataType, propertySettable, propertyRetained, propertyUnit)
                            .orTimeout(5, TimeUnit.SECONDS)
                            .join();
                    properties.add(new Device.Node.Property(
                            propertyId,
                            propertyName.join(),
                            propertyDataType.join(),
                            propertySettable.join().equals("true"),
                            propertyRetained.join().equals("true"),
                            (!propertyUnit.join().equals("undefined")) ? propertyUnit.join() : null
                    ));
                }
                CompletableFuture.allOf(nodeName, nodeType).orTimeout(5, TimeUnit.SECONDS).join();
                nodes.add(new Device.Node(
                        nodeId,
                        nodeName.join(),
                        (!nodeType.join().equals("undefined")) ? nodeType.join() : null,
                        properties
                ));
            }
            CompletableFuture.allOf(deviceHomie, deviceName, deviceState).orTimeout(5, TimeUnit.SECONDS);
            return new Device(deviceId, deviceHomie.join(), deviceName.join(), deviceState.join(), nodes);
        } catch (Exception e) {
            throw new NoSuchElementException("Device not found");
        }
    }

    @Override
    public List<Device> getManyByPatientId(String patientId) {
        try (MqttConnection connection = broker.connect()) {
            List<MqttPacket> packets = connection.fetchMultiplePackets(topicPrefix.addLevels("+", "plug", "patient-id")).join();
            List<Device> devices = new LinkedList<>();
            for (MqttPacket packet : packets) {
                if (packet.getPayloadAsString().equals(patientId)) {
                    int deviceLevelIndex = topicPrefix.getLength();
                    String id = packet.getTopicLevel(deviceLevelIndex);
                    devices.add(getOneByDeviceId(id));
                }
            }
            return devices;
        }
    }
}
