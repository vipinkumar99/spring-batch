package org.location.batch.process.configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.location.batch.process.entity.Location;
import org.location.batch.process.model.LocationModel;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.util.StringUtils;

public class LocationProcessor implements ItemProcessor<LocationModel, Location> {

	@Override
	public Location process(LocationModel item) throws Exception {
		Location location = new Location();
		location.setCity(item.getCity());
		location.setArea(item.getArea());
		if (!StringUtils.isEmpty(item.getSubarea())) {
			if (!item.getSubarea().equals("\\N")) {
				location.setSubarea(item.getSubarea());
			}
		}
		if (!StringUtils.isEmpty(item.getCreated())) {
			String[] split = item.getCreated().split(" ");
			if (split != null && split.length >= 1) {
				location.setDate(parse(split[0], "yyyy-MM-dd"));
				location.setTime(parsetime(split[1], "HH:mm:ss"));
			} else {
				Date current = new Date();
				java.sql.Date date = new java.sql.Date(current.getTime());
				java.sql.Time time = new java.sql.Time(current.getTime());
				location.setDate(date);
				location.setTime(time);
			}
		}
		return location;
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

	public static java.sql.Time parsetime(String date, String format) {
		Date current = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			current = sdf.parse(date);
		} catch (ParseException e) {
			current = new Date();
			e.printStackTrace();
		}
		return new java.sql.Time(current.getTime());
	}
}
