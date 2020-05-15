package org.batch.process.config;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


@Component
public class TrackingRowMapper implements RowMapper<Tracking> {

	@Override
	public Tracking mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tracking tracking = new Tracking();
		tracking.setId(rs.getInt("id"));
		tracking.setAccuracy(rs.getDouble("accuracy"));
		tracking.setAltitude(rs.getDouble("altitude"));
		tracking.setBatteryPercentage(rs.getDouble("batteryPercentage"));
		tracking.setBearing(rs.getDouble("bearing"));
		tracking.setCalculatedDistanceInKm(rs.getDouble("calculatedDistanceInKm"));
		tracking.setCalculatedSpeed(rs.getDouble("calculatedSpeed"));
		tracking.setCreated(rs.getString("created"));
		tracking.setDeviceTime(rs.getLong("deviceTime"));
		tracking.setEmployeeId(rs.getInt("employeeId"));
		tracking.setMobileCharging(rs.getBoolean("isMobileCharging"));
		tracking.setLatitude(rs.getDouble("latitude"));
		tracking.setNetworkOperator(rs.getString("networkOperator"));
		tracking.setProvider(rs.getString("provider"));
		tracking.setSimOperator(rs.getString("simOperator"));
		tracking.setSimSignalStrength0(rs.getString("simSignalStrength0"));
		tracking.setSimSignalStrength1(rs.getString("simSignalStrength1"));
		tracking.setSpeed(rs.getDouble("speed"));
		tracking.setTimeFromLocationApi(rs.getLong("timeFromLocationApi"));
		tracking.setUpdated(rs.getString("updated"));
		tracking.setDeviceTimeStamp(rs.getString("deviceTimeStamp"));
		tracking.setDiffInMillis(rs.getLong("diffInMillis"));
		tracking.setDiffInTime(rs.getString("diffInTime"));
		tracking.setNoInfo(rs.getBoolean("noInfo"));
		tracking.setLongitude(rs.getDouble("longitude"));
		tracking.setLocationTime(rs.getLong("locationTime"));
		return tracking;
	}

}
