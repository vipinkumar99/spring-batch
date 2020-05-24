package org.batch.process.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {
	public boolean error;
	public String statusCode;
	public String message;
	public List<Model> data;

}
