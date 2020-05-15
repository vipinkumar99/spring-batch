package org.location.batch.process.configuration;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.location.batch.process.entity.Location;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

public class LocationPreparedStatementSetter implements ItemPreparedStatementSetter<Location> {

	@Override
	public void setValues(Location item, PreparedStatement ps) throws SQLException {
		ps.setString(1, item.getCity());
		ps.setString(2, item.getArea());
		ps.setString(3, item.getSubarea());
		ps.setDate(4, item.getDate());
		ps.setTime(5, item.getTime());
	}

}
