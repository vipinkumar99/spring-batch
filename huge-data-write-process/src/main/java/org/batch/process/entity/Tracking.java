package org.batch.process.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "trackings")
public class Tracking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Basic
	@Temporal(TemporalType.DATE)
	private Date created;
	private String mobileCharging;
	private Double batteryPercentage;
	private Double longitude;
	private Double latitude;
	private Double speed;
	private Double calculatedSpeed;
	private Double calculatedDistanceInKm;
	private String provider;
	private String networkOperator;
	private String simOperator;
	private Long deviceTime;
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	private Date deviceTimeStamp;
}
