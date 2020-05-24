package org.batch.process.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JacksonXmlRootElement(localName = "charge")
public class Model {
	private String date;
	private String userId;
	private String chargeName;
	private Double amountWithoutTax;
	private Double taxAmount;
	private Double tax;
	private Double amountWithTax;
}
