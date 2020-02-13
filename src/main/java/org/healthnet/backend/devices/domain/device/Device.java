package org.healthnet.backend.devices.domain.device;

import java.util.List;

public class Device {
    private final String id;
    private final String homie;
    private final String name;
    private final String state;
    private final List<Node> nodes;

    public Device(String id, String homie, String name, String state, List<Node> nodes) {
        this.id = id;
        this.homie = homie;
        this.name = name;
        this.state = state;
        this.nodes = nodes;
    }

    public final static class Node {
        private final String id;
        private final String name;
        private final String type;
        private final List<Property> properties;

        public Node(String id, String name, String type, List<Property> properties) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.properties = properties;
        }

        public final static class Property {
            private final String id;
            private final String name;
            private final String datatype;
            private final boolean settable;
            private final boolean retained;
            private final String unit;

            public Property(String id,
                            String name,
                            String datatype,
                            boolean settable,
                            boolean retained,
                            String unit) {
                this.id = id;
                this.name = name;
                this.datatype = datatype;
                this.settable = settable;
                this.retained = retained;
                this.unit = unit;
            }
        }
    }
}
