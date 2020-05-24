package org.batch.process.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.batch.process.entity.Reason;
import org.batch.process.model.Model;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ReasonProcessor implements ItemProcessor<Model, Reason> {

	@Override
	public Reason process(Model item) throws Exception {
		if (item == null) {
			return null;
		}
		Reason reason = new Reason();
		if (!StringUtils.isEmpty(item.getCreated())) {
			reason.setDate(parse(item.getCreated(), "dd-MM-yyyy HH:mm:ss"));
		}
		reason.setReasonName(item.getReason());
		if (item.getDepartmentMaster() != null) {
			reason.setDepartmentName(item.getDepartmentMaster().getName());
		}
		if (item.getService() != null) {
			reason.setServiceName(item.getService().getName());
		}
		return reason;
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
