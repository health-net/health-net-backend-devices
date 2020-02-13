package org.healthnet.backend.devices.infrastructure.persistence.mqtt;

import org.fusesource.mqtt.client.MQTT;

public final class MqttBroker {
    private final MQTT mqtt;

    public MqttBroker(String host, int port) {
        mqtt = new MQTT();
        try {
            mqtt.setHost(host, port);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MqttConnection connect() {
        return new MqttConnection(mqtt);
    }
}
