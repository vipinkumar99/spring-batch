package org.multiple.job.entity;

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
@Table(name = "towerLocations")
public class TowerLocation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	@Column(name = "city", nullable = false)
	private String city;
	@Column(name = "area", nullable = false)
	private String area;
	@Column(name = "society", nullable = false)
	private String society;
	@Column(name = "tower", nullable = false)
	private String tower;
}
