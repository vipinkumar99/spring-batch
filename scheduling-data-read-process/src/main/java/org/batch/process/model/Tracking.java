package org.batch.process.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tracking {
	private int id;
	private String created;
	private boolean mobileCharging;
	private double batteryPercentage;
	private double longitude;
	private double latitude;
	private double speed;
	private double calculatedSpeed;
	private double calculatedDistanceInKm;
	private String provider;
	private String networkOperator;
	private String simOperator;
	private long deviceTime;
	private String deviceTimeStamp;
}
