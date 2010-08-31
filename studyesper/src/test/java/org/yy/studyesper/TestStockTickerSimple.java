package org.yy.studyesper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.yy.studyesper.eventbean.PriceLimit;
import org.yy.studyesper.eventbean.StockTick;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.time.TimerControlEvent;

public class TestStockTickerSimple {
	EPServiceProvider epService;

	@Before
	public void setUp() throws Exception {

		Configuration configuration = new Configuration();
		// configuration.addEventTypeAutoName("org.yy.studyesper.eventbean");
		configuration.addEventType("PriceLimit", PriceLimit.class.getName());
		configuration.addEventType("StockTick", StockTick.class.getName());

		epService = EPServiceProviderManager.getProvider(
				"TestStockTickerSimple", configuration);
		// epService.getEPRuntime().addEmittedListener(listener, null);

		// statement.addListener(listener);
		// To reduce logging noise and get max performance
		epService.getEPRuntime().sendEvent(
				new TimerControlEvent(
						TimerControlEvent.ClockType.CLOCK_EXTERNAL));
	}

	@Test
	public void testStockTicker() throws Exception {
		log.info(".testStockTicker");
		String eplStatement = "every tick=StockTick()";
		EPStatement statement = epService.getEPAdministrator().createPattern(
				eplStatement);
		statement.addListener(new UpdateListener(){

			public void update(EventBean[] newEvents, EventBean[] oldEvents) {
				Object tick = newEvents[0].get("tick");
				log.debug(tick);
				
			}});
		
		epService.getEPRuntime().sendEvent(new StockTick("ABC",20d));
		
		epService.getEPRuntime().sendEvent(new StockTick("ABC",20.1d));
		epService.getEPRuntime().sendEvent(new StockTick("ABC",20.2d));
	}

	private static final Log log = LogFactory
			.getLog(TestStockTickerSimple.class);
}