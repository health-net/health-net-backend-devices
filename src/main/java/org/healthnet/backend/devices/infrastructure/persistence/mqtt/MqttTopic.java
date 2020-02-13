package org.healthnet.backend.devices.infrastructure.persistence.mqtt;

public class MqttTopic {
    private final String[] levels;

    public MqttTopic(String ... levels) {
        this.levels = levels;
    }

    public static MqttTopic fromString(String topic) {
        return new MqttTopic(topic.split("/"));
    }

    public int getLength() {
        return levels.length;
    }

    public String getLevel(int index) {
        return levels[index];
    }

    public String getAsString() {
        return String.join("/", levels);
    }

    public MqttTopic addLevels(String ... levels) {
        int aLen = this.levels.length;
        int bLen = levels.length;
        String[] result = new String[aLen + bLen];
        System.arraycopy(this.levels, 0, result, 0, aLen);
        System.arraycopy(levels, 0, result, aLen, bLen);
        return new MqttTopic(result);
    }
}
