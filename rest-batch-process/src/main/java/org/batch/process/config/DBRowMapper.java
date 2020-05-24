package org.batch.process.config;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.batch.process.entity.Charge;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class DBRowMapper implements RowMapper<Charge> {

	@Override
	public Charge mapRow(ResultSet rs, int rowNum) throws SQLException {
		Charge charge = new Charge();
		charge.setDate(rs.getDate("date"));
		charge.setUserId(rs.getString("userId"));
		charge.setChargeName(rs.getString("chargeName"));
		charge.setAmountWithoutTax(rs.getDouble("amountWithoutTax"));
		charge.setAmountWithTax(rs.getDouble("amountWithTax"));
		charge.setTax(rs.getDouble("tax"));
		charge.setTaxAmount(rs.getDouble("taxAmount"));
		return charge;
	}

}
