package org.batch.process.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tracking {
	private int id;
	private double batteryPercentage;
	private long timeFromLocationApi;
	private long locationTime;
	private double longitude;
	private double calculatedSpeed;
	private boolean mobileCharging;
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
	private boolean noInfo;
	private String created;
	private String updated;
}
