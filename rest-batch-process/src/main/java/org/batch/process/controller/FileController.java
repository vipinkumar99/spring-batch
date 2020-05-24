package org.batch.process.controller;

import org.batch.process.service.ChargeService;
import org.batch.process.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/batch/")
public class FileController {
	@Autowired
	private ChargeService chargeService;

	@GetMapping("/download")
	public ResponseEntity<Resource> getResource(@RequestParam(required = false) String type) throws Exception {
		if (type.equals("excel")) {
			return FileUtil.getExcelResourceResponse(chargeService.getExcelFile(), "Charge");
		} else if (type.equals("csv")) {
			return FileUtil.getCsvResourceResponse(chargeService.getCsvFile(), "Charge");
		} else if (type.equals("json")) {
			return FileUtil.getJsonResourceResponse(chargeService.getJsonFile(), "Charge");
		} else if (type.equals("xml")) {
			return FileUtil.getXmlResourceResponse(chargeService.getXmlFile(), "Charge");
		} else {
			throw new Exception("File type is invalid!");
		}
	}

}
