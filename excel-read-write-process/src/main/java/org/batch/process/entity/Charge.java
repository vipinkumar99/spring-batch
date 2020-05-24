package org.batch.process.entity;

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
@Table(name = "charges")
public class Charge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private java.sql.Date date;
	private String userId;
	private String type;
	private String chargeName;
	private String serviceName;
	private Double amountWithTax;
	private Double tax;
	private Double taxAmount;
	private Double amountWithoutTax;
	private String status;
}
