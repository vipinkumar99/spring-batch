package org.batch.process.configuration;

import org.batch.process.entity.Voltage;
import org.springframework.batch.item.ItemProcessor;

public class VoltageProcessor implements ItemProcessor<Voltage, Voltage> {

	@Override
	public Voltage process(Voltage volt) throws Exception {
		final Voltage voltage = new Voltage();
		voltage.setVolt(volt.getVolt());
		voltage.setTime(volt.getTime());
		return voltage;
	}

}
