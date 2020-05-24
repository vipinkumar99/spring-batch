package org.batch.process.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Model {
	private String created;
	private String mobileCharging;
	private double batteryPercentage;
	private double longitude;
	private double latitude;
	private double speed;
	private double calculatedSpeed;
	private double calculatedDistanceInKm;
	private String provider;
	private String networkOperator;
	private String simOperator;
	private String deviceTimeStamp;
	private long deviceTime;

	public static String NAMES[] = { "created", "mobileCharging", "batteryPercentage", "longitude", "latitude", "speed",
			"calculatedSpeed", "calculatedDistanceInKm", "provider", "networkOperator", "simOperator",
			"deviceTimeStamp", "deviceTime" };
}
