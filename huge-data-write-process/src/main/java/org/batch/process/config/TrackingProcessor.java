package org.batch.process.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.batch.process.entity.Tracking;
import org.batch.process.model.Model;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TrackingProcessor implements ItemProcessor<Model, Tracking> {

	@Override
	public Tracking process(Model item) throws Exception {
		if (item == null) {
			return null;
		}
		Tracking model = new Tracking();
		model.setBatteryPercentage(item.getBatteryPercentage());
		model.setLongitude(item.getLongitude());
		model.setCalculatedSpeed(item.getCalculatedSpeed());
		model.setMobileCharging(item.getMobileCharging());
		model.setCalculatedDistanceInKm(item.getCalculatedDistanceInKm());
		model.setSimOperator(item.getSimOperator());
		model.setSpeed(item.getSpeed());
		model.setNetworkOperator(item.getNetworkOperator());
		model.setDeviceTime(item.getDeviceTime());
		model.setLatitude(item.getLatitude());
		model.setProvider(item.getProvider());
		if (!StringUtils.isEmpty(item.getDeviceTimeStamp())) {
			model.setDeviceTimeStamp(parse(item.getDeviceTimeStamp().replace("T", " "), "dd-MM-yyyy HH:mm:ss"));
		}
		if (!StringUtils.isEmpty(item.getCreated())) {
			model.setCreated(parse(item.getDeviceTimeStamp().replace("T", " "), "dd-MM-yyyy HH:mm:ss"));
		}
		return model;
	}

	public static Date parse(String date, String format) {
		Date current = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			current = sdf.parse(date);
		} catch (ParseException e) {
			current = new Date();
			e.printStackTrace();
		}
		return current;
	}

}
