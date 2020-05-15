package org.batch.process.config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TrackingProcessor implements ItemProcessor<Tracking, Model> {

	private static long count = 0l;

	@Override
	public Model process(Tracking item) throws Exception {
		if (item == null) {
			return null;
		}
		Model model = new Model();
		model.setId(item.getId());
		model.setBatteryPercentage(item.getBatteryPercentage());
		model.setTimeFromLocationApi(item.getTimeFromLocationApi());
		model.setLocationTime(item.getLocationTime());
		model.setLongitude(item.getLongitude());
		model.setCalculatedSpeed(item.getCalculatedSpeed());
		model.setMobileCharging(item.isMobileCharging() ? "CHARGED" : "NOT_CHARGED");
		model.setCalculatedDistanceInKm(item.getCalculatedDistanceInKm());
		model.setSimOperator(StringUtils.isEmpty(item.getSimOperator()) ? "0000" : item.getSimOperator());
		model.setAccuracy(item.getAccuracy());
		model.setAltitude(item.getAltitude());
		model.setSpeed(item.getSpeed());
		model.setNetworkOperator(StringUtils.isEmpty(item.getNetworkOperator()) ? "0000" : item.getNetworkOperator());
		model.setDeviceTime(item.getDeviceTime());
		model.setLatitude(item.getLatitude());
		model.setBearing(item.getBearing());
		model.setSimSignalStrength0(
				StringUtils.isEmpty(item.getSimSignalStrength0()) ? "0000" : item.getSimSignalStrength0());
		model.setSimSignalStrength1(
				StringUtils.isEmpty(item.getSimSignalStrength1()) ? "0000" : item.getSimSignalStrength1());
		model.setProvider(StringUtils.isEmpty(item.getProvider()) ? "no" : item.getProvider());
		model.setEmployeeId(item.getEmployeeId());
		if (StringUtils.isEmpty(item.getDeviceTimeStamp())) {
			model.setDeviceTimeStamp("07-07-2019T00:00");
		} else {
			model.setDeviceTimeStamp(item.getDeviceTimeStamp().replace(" ", "T"));
		}
		model.setDiffInMillis(item.getDiffInMillis());
		model.setDiffInTime(item.getDiffInTime());
		model.setNoInfo(item.isNoInfo() ? "NO_INFORMATION" : "INFORMATION");
		if (StringUtils.isEmpty(item.getCreated())) {
			model.setCreated("15-05-2020");
		} else {
			model.setCreated(item.getCreated().replace(" ", "T"));
		}
		if (StringUtils.isEmpty(item.getUpdated())) {
			model.setUpdated("15-05-2020");
		} else {
			model.setUpdated(item.getUpdated().replace(" ", "T"));
		}
		count++;
		System.out.println("Tracking id:" + item.getId() + " count:" + count);
		return model;
	}

}
