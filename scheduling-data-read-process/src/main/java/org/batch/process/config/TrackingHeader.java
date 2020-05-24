package org.batch.process.config;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.stereotype.Component;

@Component
public class TrackingHeader implements FlatFileHeaderCallback {

	@Override
	public void writeHeader(Writer writer) throws IOException {
		writer.append("Created").append(",");
		writer.append("ChargingStatus").append(",").append("ChargingPercentage").append(",");
		writer.append("Longitude").append(",").append("Latitude").append(",");
		writer.append("Speed").append(",").append("CalculatedSpeed").append(",");
		writer.append("CalculatedDistanceInKm").append(",").append("Provider").append(",");
		writer.append("NetworkOperator").append(",").append("SimOperator").append(",");
		writer.append("DeviceTimeStamp").append(",").append("DeviceTime");
	}
}
