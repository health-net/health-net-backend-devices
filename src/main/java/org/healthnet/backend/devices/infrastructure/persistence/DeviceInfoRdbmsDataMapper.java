package org.healthnet.backend.devices.infrastructure.persistence;

import org.healthnet.backend.devices.domain.device.DeviceId;
import org.healthnet.backend.devices.domain.device.DeviceInfo;
import org.healthnet.backend.devices.domain.device.DeviceName;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class DeviceInfoRdbmsDataMapper extends DeviceInfoDataMapper {
    private final DataSource dataSource;

    public DeviceInfoRdbmsDataMapper(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(DeviceInfo info) {
        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `devices_info` VALUES (?, ?)");
            preparedStatement.setString(1, info.getId().getValue());
            preparedStatement.setString(2, info.getName().getValue());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<DeviceInfo> selectByDeviceId(DeviceId deviceId) {
        try (var connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `devices_info` WHERE id=(?)");
            statement.setString(1, deviceId.getValue());
            ResultSet result = statement.executeQuery();
            if(!result.next()) {
                return Optional.empty();
            } else {
                return Optional.of(new DeviceInfo(
                        new DeviceId(result.getString("id")),
                        new DeviceName(result.getString("name"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<DeviceInfo> selectAll() {
        try (var connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `devices_info`");
            ResultSet result = statement.executeQuery();
            HashSet<DeviceInfo> set = new HashSet<>();
            while(result.next()) {
                set.add(new DeviceInfo(
                        new DeviceId(result.getString("id")),
                        new DeviceName(result.getString("name"))
                ));
            }
            return set;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
