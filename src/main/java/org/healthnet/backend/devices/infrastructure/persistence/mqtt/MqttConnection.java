package org.healthnet.backend.devices.infrastructure.persistence.mqtt;

import org.fusesource.mqtt.client.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public final class MqttConnection implements AutoCloseable {
    private final MQTT mqtt;

    public MqttConnection(MQTT mqtt) {
        this.mqtt = mqtt;
    }

    public CompletableFuture<MqttPacket> fetchSinglePacket(MqttTopic topic) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                BlockingConnection connection = mqtt.blockingConnection();
                connection.connect();
                Topic[] topics = { new Topic(topic.getAsString(), QoS.AT_LEAST_ONCE)};
                connection.subscribe(topics);
                Message message = connection.receive();
                connection.unsubscribe(new String[] {topic.getAsString()});
                return new MqttPacket(message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<List<MqttPacket>> fetchMultiplePackets(MqttTopic topic) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                List<MqttPacket> packets = new LinkedList<>();
                BlockingConnection connection = mqtt.blockingConnection();
                connection.connect();
                Topic[] topics = { new Topic(topic.getAsString(), QoS.AT_LEAST_ONCE) };
                connection.subscribe(topics);
                while (true) {
                    Optional<Message> message = Optional.ofNullable(connection.receive(2, TimeUnit.SECONDS));
                    if (message.isEmpty()) {
                        connection.unsubscribe(new String[] {topic.getAsString()});
                        connection.disconnect();
                        return packets;
                    } else {
                        packets.add(new MqttPacket(message.get()));
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void close() {
    }
}
