package org.batch.process.config;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.batch.process.model.Tracking;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class TrackingRowMapper implements RowMapper<Tracking> {

	@Override
	public Tracking mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tracking tracking = new Tracking();
		tracking.setBatteryPercentage(rs.getDouble("batteryPercentage"));
		tracking.setCalculatedDistanceInKm(rs.getDouble("calculatedDistanceInKm"));
		tracking.setCalculatedSpeed(rs.getDouble("calculatedSpeed"));
		tracking.setCreated(rs.getString("created"));
		tracking.setDeviceTime(rs.getLong("deviceTime"));
		tracking.setMobileCharging(rs.getBoolean("isMobileCharging"));
		tracking.setLatitude(rs.getDouble("latitude"));
		tracking.setNetworkOperator(rs.getString("networkOperator"));
		tracking.setProvider(rs.getString("provider"));
		tracking.setSimOperator(rs.getString("simOperator"));
		tracking.setSpeed(rs.getDouble("speed"));
		tracking.setDeviceTimeStamp(rs.getString("deviceTimeStamp"));
		tracking.setLongitude(rs.getDouble("longitude"));
		return tracking;
	}

}
