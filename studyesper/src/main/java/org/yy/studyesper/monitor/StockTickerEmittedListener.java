package org.yy.studyesper.monitor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class StockTickerEmittedListener implements UpdateListener {
	private List<Object> matchEvents = Collections
			.synchronizedList(new LinkedList<Object>());

	public void emitted(Object object) {
		log.info(".emitted Received emitted " + object);
		matchEvents.add(object);
	}

	public int getSize() {
		return matchEvents.size();
	}

	public List<Object> getMatchEvents() {
		return matchEvents;
	}

	public void clearMatched() {
		matchEvents.clear();
	}

	private static final Log log = LogFactory
			.getLog(StockTickerEmittedListener.class);

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null && newEvents.length > 1) {
			Object object = newEvents[0].getUnderlying();
			log.info(".emitted Received emitted " + object);
			matchEvents.add(object);
		}

	}
}