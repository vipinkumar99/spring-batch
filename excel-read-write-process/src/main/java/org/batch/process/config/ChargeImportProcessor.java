package org.batch.process.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.batch.process.entity.Charge;
import org.batch.process.model.Model;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ChargeImportProcessor implements ItemProcessor<Model, Charge> {

	@Override
	public Charge process(Model item) throws Exception {
		if (item == null) {
			return null;
		}
		Charge charge = new Charge();
		if (!StringUtils.isEmpty(item.getCreated())) {
			charge.setDate(parse(item.getCreated(), "dd-MM-yyyy HH:mm:ss"));
		}
		charge.setUserId(item.getUserId());
		charge.setType(item.getCrmType());
		charge.setChargeName(item.getChargeName());
		charge.setServiceName(item.getServiceType());
		charge.setStatus(item.getStatus());
		if (!StringUtils.isEmpty(item.getPrice())) {
			Double amountWithTax = Double.valueOf(item.getPrice());
			if (amountWithTax != null && amountWithTax > 0) {
				double tax = 10d;
				double taxAmount = (amountWithTax * tax) / 100;
				double amountWithoutTax = (amountWithTax - taxAmount);
				charge.setAmountWithoutTax(amountWithoutTax);
				charge.setAmountWithTax(amountWithTax);
				charge.setTaxAmount(taxAmount);
				charge.setTax(tax);
			}
		}
		return charge;
	}

	public static java.sql.Date parse(String date, String format) {
		Date current = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			current = sdf.parse(date);
		} catch (ParseException e) {
			current = new Date();
			e.printStackTrace();
		}
		return new java.sql.Date(current.getTime());
	}

}
