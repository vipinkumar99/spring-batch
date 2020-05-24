package org.batch.process.service;

import org.springframework.core.io.Resource;

public interface ChargeService {
Resource getExcelFile()throws Exception;
Resource getCsvFile()throws Exception;
Resource getXmlFile()throws Exception;
Resource getJsonFile()throws Exception;
}
