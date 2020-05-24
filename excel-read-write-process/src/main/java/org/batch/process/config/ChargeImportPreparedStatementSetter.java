package org.batch.process.config;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.batch.process.entity.Charge;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.stereotype.Component;

@Component
public class ChargeImportPreparedStatementSetter implements ItemPreparedStatementSetter<Charge> {

	@Override
	public void setValues(Charge item, PreparedStatement ps) throws SQLException {
		ps.setDate(1, item.getDate());
		ps.setString(2, item.getUserId());
		ps.setString(3, item.getChargeName());
		ps.setString(4, item.getType());
		ps.setString(5, item.getServiceName());
		ps.setDouble(6, item.getAmountWithoutTax());
		ps.setDouble(7, item.getTaxAmount());
		ps.setDouble(8, item.getAmountWithTax());
		ps.setDouble(9, item.getTax());
		ps.setString(10, item.getStatus());
	}

}
