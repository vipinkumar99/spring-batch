package org.location.batch.process.entity;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "locations")
public class Location {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	@Column(name = "city", nullable = false)
	private String city;
	@Column(name = "area", nullable = false)
	private String area;
	@Column(name = "subarea")
	private String subarea;
	@Column(name = "date", nullable = false)
	private Date date;
	@Column(name = "time", nullable = false)
	private Time time;
}
