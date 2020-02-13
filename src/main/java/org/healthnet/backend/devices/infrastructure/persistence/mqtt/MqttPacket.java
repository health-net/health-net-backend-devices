package org.healthnet.backend.devices.infrastructure.persistence.mqtt;

import org.fusesource.mqtt.client.Message;

import java.nio.charset.StandardCharsets;

public final class MqttPacket {
    private final MqttTopic topic;
    private final byte[] bytes;

    public MqttPacket(Message message) {
        topic = MqttTopic.fromString(message.getTopic());
        bytes = message.getPayload();
    }

    public String getTopicLevel(int index) {
        return topic.getLevel(index);
    }

    public String getPayloadAsString() {
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
