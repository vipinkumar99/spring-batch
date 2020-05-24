package org.batch.process.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Model {
	private Integer id;
	private String created;
	private String updated;
	private Integer departmentMasterId;
	private String reason;
	private String type;
	private Integer serviceId;
	private Boolean isShifting;
	private Long datetimeValue;
	private String datetimeType;
	private Department departmentMaster;
	private Service service;
}
