package org.batch.process.config;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.stereotype.Component;

@Component
public class TrackingHeader implements FlatFileHeaderCallback {

	@Override
	public void writeHeader(Writer writer) throws IOException {
		writer.append("Id").append(",").append("Accuracy").append(",");
		writer.append("Altitude").append(",").append("AatteryPercentage").append(",");
		writer.append("Bearing").append(",").append("CalculatedDistanceInKm").append(",");
		writer.append("CalculatedSpeed").append(",").append("Created").append(",");
		writer.append("DeviceTime").append(",").append("EmployeeId").append(",");
		writer.append("MobileCharging").append(",").append("Latitude").append(",");
		writer.append("LocationTime").append(",").append("Longitude").append(",");
		writer.append("NetworkOperator").append(",").append("Provider").append(",");
		writer.append("SimOperator").append(",").append("SimSignalStrength0").append(",");
		writer.append("SimSignalStrength1").append(",").append("Speed").append(",");
		writer.append("TimeFromLocationApi").append(",").append("Updated").append(",");
		writer.append("DeviceTimeStamp").append(",").append("DiffInMillis").append(",");
		writer.append("DiffInTime").append(",").append("NoInfo");
	}
}
