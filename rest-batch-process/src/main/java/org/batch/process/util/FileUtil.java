package org.batch.process.util;

import java.io.ByteArrayOutputStream;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class FileUtil {

	public static <T> ResponseEntity<Resource> getExcelResourceResponse(Resource resource, String fileName)
			throws Exception {
		return ResponseEntity.ok()
				.contentType(
						MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + ".xls").body(resource);
	}

	public static <T> ResponseEntity<Resource> getCsvResourceResponse(ByteArrayOutputStream fileStream, String fileName)
			throws Exception {
		Resource resource = new ByteArrayResource(fileStream.toByteArray());
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("text/csv"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + ".csv").body(resource);
	}

	public static <T> ResponseEntity<Resource> getCsvResourceResponse(Resource resource, String fileName)
			throws Exception {
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("text/csv"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + ".csv").body(resource);
	}

	public static <T> ResponseEntity<Resource> getJsonResourceResponse(Resource resource, String fileName)
			throws Exception {
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/json"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + ".json").body(resource);
	}

	public static <T> ResponseEntity<Resource> getXmlResourceResponse(Resource resource, String fileName)
			throws Exception {
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/xml"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + ".xml").body(resource);
	}

}
