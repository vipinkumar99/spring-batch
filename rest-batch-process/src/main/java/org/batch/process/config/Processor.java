package org.batch.process.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.batch.process.entity.Charge;
import org.batch.process.model.Model;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class Processor implements ItemProcessor<Charge, Model> {

	@Override
	public Model process(Charge item) throws Exception {
		Model model = new Model();
		model.setDate(format(item.getDate(), "dd-MM-yyyy"));
		model.setUserId(item.getUserId());
		model.setChargeName(item.getChargeName());
		model.setAmountWithoutTax(item.getAmountWithoutTax());
		model.setAmountWithTax(item.getAmountWithTax());
		model.setTax(item.getTax());
		model.setTaxAmount(item.getTaxAmount());
		return model;
	}

	public static String format(java.sql.Date date, String format) {
		Date current = new Date(date.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(current);
	}
}
