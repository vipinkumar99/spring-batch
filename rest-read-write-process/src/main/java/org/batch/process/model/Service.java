package org.batch.process.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Service {
	private String name;
	private String iconUrl;
	private List<Object> partnersResellers;
	private Integer id;
	private String created;
	private String updated;
}
