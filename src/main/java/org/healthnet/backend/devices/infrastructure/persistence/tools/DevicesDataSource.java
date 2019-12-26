package org.healthnet.backend.devices.infrastructure.persistence.tools;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.util.Map;

public class DevicesDataSource extends MysqlDataSource {
    public DevicesDataSource() {
        setDatabaseName("healthnet_devices");
        Map<String, String> env = System.getenv();
        setUser(env.getOrDefault("HEALTHNET_DB_USER", "root"));
        setPassword(env.getOrDefault("HEALTHNET_DB_PASSWORD", "root"));
    }
}
