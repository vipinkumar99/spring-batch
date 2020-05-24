package org.batch.process.config;

import org.batch.process.model.Model;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.support.rowset.RowSet;
import org.springframework.stereotype.Component;

@Component
public class ChargeExportExcelRowMapper implements RowMapper<Model> {

	@Override
	public Model mapRow(RowSet rs) throws Exception {
		if (rs == null || rs.getCurrentRow() == null) {
			return null;
		}
		Model model = new Model();
		model.setCreated(rs.getColumnValue(0));
		model.setUserId(rs.getColumnValue(1));
		model.setCrmType(rs.getColumnValue(2));
		model.setCustomerName(rs.getColumnValue(3));
		model.setPhoneNo(rs.getColumnValue(4));
		model.setChargeName(rs.getColumnValue(5));
		model.setServiceType(rs.getColumnValue(6));
		model.setPrice(rs.getColumnValue(7));
		model.setRemark(rs.getColumnValue(8));
		model.setType(rs.getColumnValue(9));
		model.setStatus(rs.getColumnValue(10));
		return model;
	}

}
