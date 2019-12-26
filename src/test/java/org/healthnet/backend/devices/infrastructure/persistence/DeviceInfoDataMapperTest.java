package org.healthnet.backend.devices.infrastructure.persistence;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.healthnet.backend.devices.domain.device.DeviceId;
import org.healthnet.backend.devices.domain.device.DeviceInfo;
import org.healthnet.backend.devices.domain.device.DeviceName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DeviceInfoDataMapperTest {
    private final static DataSource dataSource = new TestDataSource();
    private final static DeviceInfoDataMapper dataMapper = new DeviceInfoRdbmsDataMapper(dataSource);

    @BeforeEach
    void setUp() throws Exception {
        var connection = dataSource.getConnection();
        connection.createStatement().execute("TRUNCATE TABLE `devices_info`");
    }

    @Test
    void Insert_SuccessfulExecution_DeviceInfoHasBeenStored() throws Exception {
        DeviceInfo info = aDeviceInfo();
        dataMapper.insert(info);
        ResultSet result = select(info.getId());
        assertEquals(info.getId().getValue(), result.getString("id"));
        assertEquals(info.getName().getValue(), result.getString("name"));
    }

    @Test
    void Select_DeviceInfoIsPresent_OptionalOfDeviceInfoHasBeenReturned() throws Exception {
        DeviceInfo expectedInfo = aDeviceInfo();
        insert(expectedInfo);

        Optional<DeviceInfo> actualInfo = dataMapper.select(expectedInfo.getId());
        assertTrue(actualInfo.isPresent());
        assertEquals(expectedInfo, actualInfo.get());
    }

    @Test
    void Select_DeviceInfoIsNotPresent_OptionalOfEmptyHasBeenReturned() throws Exception {
        DeviceInfo expectedInfo = aDeviceInfo();

        Optional<DeviceInfo> actualInfo = dataMapper.select(expectedInfo.getId());
        assertTrue(actualInfo.isEmpty());
    }

    private DeviceInfo aDeviceInfo() {
        return new DeviceInfo(
                new DeviceId("37b784af-6f9b-4891-9e45-58d98f0fa8a2"),
                new DeviceName("Ecg2000")
        );
    }

    private void insert(DeviceInfo deviceInfo) throws Exception {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `devices_info` VALUES (?, ?)");
        preparedStatement.setString(1, deviceInfo.getId().getValue());
        preparedStatement.setString(2, deviceInfo.getName().getValue());
        preparedStatement.execute();
    }

    private ResultSet select(DeviceId deviceId) throws Exception {
        PreparedStatement statement = dataSource.getConnection().prepareStatement("SELECT * FROM `devices_info` WHERE id=(?)");
        statement.setString(1, deviceId.getValue());
        ResultSet result = statement.executeQuery();
        result.next();
        return result;
    }

    public static class TestDataSource extends MysqlDataSource {
        public TestDataSource() {
            setDatabaseName("healthnet_devices_test");
            Map<String, String> env = System.getenv();
            setUser(env.getOrDefault("HEALTHNET_DB_USER", "root"));
            setPassword(env.getOrDefault("HEALTHNET_DB_PASSWORD", "root"));
        }
    }
}
