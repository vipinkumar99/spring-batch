package org.batch.process.config;

import java.util.List;
import java.util.logging.Logger;

import org.batch.process.model.BaseResponse;
import org.batch.process.model.Model;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

@Component
public class ReasonReader implements ItemReader<Model> {

	private static Logger logger = Logger.getLogger(ReasonReader.class.getName());
	public static int count = 0;

	@Autowired
	private RestTemplate restTemplate;

	private String url = "http://localhost:8080/tripleplay/reasonMaster/all";
	private int nextIndex = 0;
	private List<Model> data;

	@Override
	public Model read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (dataNotInitialized()) {
			data = getDataFromRestApi();
			count = data.size();
		}
		Model next = null;
		if (nextIndex < data.size()) {
			next = data.get(nextIndex);
			nextIndex++;
		}
		return next;
	}

	private boolean dataNotInitialized() {
		return CollectionUtils.isEmpty(this.data);
	}

	private List<Model> getDataFromRestApi() {
		BaseResponse res = restTemplate.getForObject(url, BaseResponse.class);
		if (res == null || res.isError()) {
			return null;
		}
		if (CollectionUtils.isEmpty(res.data)) {
			return null;
		}
		logger.info("data found:" + res.data.size());
		return res.getData();
	}

}
