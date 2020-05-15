package org.batch.process.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "voltages")
public class Voltage {
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	@NotNull
	@Column(name = "volt", precision = 10, scale = 4, nullable = false)
	private BigDecimal volt;
	@NotNull
	@Column(name = "time", nullable = false)
	private Double time;

	@Override
	public String toString() {
		return "Voltage [id=" + id + ", volt=" + volt + ", time=" + time + "]";
	}

}
