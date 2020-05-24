package org.batch.process.config;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.batch.process.entity.Reason;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.stereotype.Component;

@Component
public class ReasonPreparedStatementSetter implements ItemPreparedStatementSetter<Reason> {

	@Override
	public void setValues(Reason item, PreparedStatement ps) throws SQLException {
		ps.setDate(1, item.getDate());
		ps.setString(2, item.getReasonName());
		ps.setString(3, item.getDepartmentName());
		ps.setString(4, item.getServiceName());
	}

}
