package org.batch.process.config;

import org.batch.process.model.Model;
import org.batch.process.model.Tracking;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TrackingProcessor implements ItemProcessor<Tracking, Model> {

	@Override
	public Model process(Tracking item) throws Exception {
		if (item == null) {
			return null;
		}
		Model model = new Model();
		model.setBatteryPercentage(item.getBatteryPercentage());
		model.setLongitude(item.getLongitude());
		model.setCalculatedSpeed(item.getCalculatedSpeed());
		model.setMobileCharging(item.isMobileCharging() ? "CHARGED" : "NOT_CHARGED");
		model.setCalculatedDistanceInKm(item.getCalculatedDistanceInKm());
		model.setSimOperator(StringUtils.isEmpty(item.getSimOperator()) ? "0000" : item.getSimOperator());
		model.setSpeed(item.getSpeed());
		model.setNetworkOperator(StringUtils.isEmpty(item.getNetworkOperator()) ? "0000" : item.getNetworkOperator());
		model.setDeviceTime(item.getDeviceTime());
		model.setLatitude(item.getLatitude());
		model.setProvider(StringUtils.isEmpty(item.getProvider()) ? "no" : item.getProvider());
		if (StringUtils.isEmpty(item.getDeviceTimeStamp())) {
			model.setDeviceTimeStamp("07-07-2019T00:00");
		} else {
			model.setDeviceTimeStamp(item.getDeviceTimeStamp().replace(" ", "T"));
		}
		if (StringUtils.isEmpty(item.getCreated())) {
			model.setCreated("15-05-2020T00:00");
		} else {
			model.setCreated(item.getCreated().replace(" ", "T"));
		}
		return model;
	}

}
