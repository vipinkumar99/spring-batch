package org.batch.process.config;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.batch.process.entity.Charge;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;

@Component
public class ChargeExportWriter implements ItemStreamWriter<Charge> {
	public static long count = 0l;

	private HSSFWorkbook workbook;
	private WritableResource resource;
	private int row;

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		String file = "C:\\Users\\Dell\\Desktop\\New folder\\charges.xls";
		workbook = new HSSFWorkbook();
		HSSFPalette palette = workbook.getCustomPalette();
		HSSFSheet sheet = workbook.createSheet();
		resource = new FileSystemResource(file);
		row = 0;
		createTitleRow(sheet, palette);
		createHeaderRow(sheet);
	}

	private void createTitleRow(HSSFSheet s, HSSFPalette palette) {
		HSSFColor redish = palette.findSimilarColor((byte) 0xE6, (byte) 0x50, (byte) 0x32);
		palette.setColorAtIndex(redish.getIndex(), (byte) 0xE6, (byte) 0x50, (byte) 0x32);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setWrapText(true);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setFillForegroundColor(redish.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);

		HSSFRow r = s.createRow(row);

		Cell c = r.createCell(0);
		c.setCellValue("Charge Details");
		r.createCell(1).setCellStyle(headerStyle);
		r.createCell(2).setCellStyle(headerStyle);
		s.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
		c.setCellStyle(headerStyle);

		CellUtil.setAlignment(c, HorizontalAlignment.CENTER);

		row++;
	}

	private void createHeaderRow(HSSFSheet s) {
		CellStyle cs = workbook.createCellStyle();
		cs.setWrapText(true);
		cs.setAlignment(HorizontalAlignment.LEFT);

		HSSFRow r = s.createRow(row);
		r.setRowStyle(cs);

		Cell c = r.createCell(0);
		c.setCellValue("Date");
		s.setColumnWidth(0, poiWidth(18.0));
		c = r.createCell(1);
		c.setCellValue("UserId");
		s.setColumnWidth(1, poiWidth(24.0));
		c = r.createCell(2);
		c.setCellValue("Charge");
		s.setColumnWidth(2, poiWidth(24.0));
		c = r.createCell(3);
		c.setCellValue("AmountWithoutTax");
		s.setColumnWidth(3, poiWidth(18.0));
		c = r.createCell(4);
		c.setCellValue("TaxAmount");
		s.setColumnWidth(4, poiWidth(18.0));
		c = r.createCell(5);
		c.setCellValue("Tax%");
		s.setColumnWidth(5, poiWidth(18.0));
		c = r.createCell(6);
		c.setCellValue("AmountWithTax");
		s.setColumnWidth(6, poiWidth(18.0));
		row++;
	}

	private int poiWidth(double width) {
		return (int) Math.round(width * 256 + 200);
	}

	@Override
	public void write(List<? extends Charge> items) throws Exception {
		HSSFSheet s = workbook.getSheetAt(0);
		for (Charge charge : items) {
			Row r = s.createRow(row++);
			Cell c = r.createCell(0);
			c.setCellValue(format(charge.getDate(), "dd-MM-yyyy"));
			c = r.createCell(1);
			c.setCellValue(charge.getUserId());
			c = r.createCell(2);
			c.setCellValue(charge.getChargeName());
			c = r.createCell(3);
			c.setCellValue(charge.getAmountWithoutTax().doubleValue());
			c = r.createCell(4);
			c.setCellValue(charge.getTaxAmount().doubleValue());
			c = r.createCell(5);
			c.setCellValue(charge.getTax().doubleValue());
			c = r.createCell(6);
			c.setCellValue(charge.getAmountWithTax().doubleValue());
			count++;
		}
	}

	@Override
	public void close() throws ItemStreamException {
		if (workbook == null) {
			return;
		}
		try (BufferedOutputStream bos = new BufferedOutputStream(resource.getOutputStream())) {
			workbook.write(bos);
			bos.flush();
			workbook.close();
		} catch (IOException ex) {
			throw new ItemStreamException("Error writing to output file", ex);
		}
		row = 0;
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {

	}

	public static String format(java.sql.Date date, String format) {
		Date current = new Date(date.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(current);
	}

}
