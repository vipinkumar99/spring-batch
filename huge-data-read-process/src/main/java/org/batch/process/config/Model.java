package org.batch.process.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Model {
	private int id;
	private double batteryPercentage;
	private long timeFromLocationApi;
	private long locationTime;
	private double longitude;
	private double calculatedSpeed;
	private String mobileCharging;
	private double calculatedDistanceInKm;
	private String simOperator;
	private double accuracy;
	private double altitude;
	private double speed;
	private String networkOperator;
	private long deviceTime;
	private double latitude;
	private double bearing;
	private String simSignalStrength0;
	private String simSignalStrength1;
	private String provider;
	private int employeeId;
	private String deviceTimeStamp;
	private long diffInMillis;
	private String diffInTime;
	private String noInfo;
	private String created;
	private String updated;
	
	static String names[] = { "id", "accuracy", "altitude", "batteryPercentage", "bearing", "calculatedDistanceInKm",
			"calculatedSpeed", "created", "deviceTime", "employeeId", "mobileCharging", "latitude", "locationTime",
			"longitude", "networkOperator", "provider", "simOperator", "simSignalStrength0", "simSignalStrength1",
			"speed", "timeFromLocationApi", "updated", "deviceTimeStamp", "diffInMillis", "diffInTime", "noInfo" };
}
