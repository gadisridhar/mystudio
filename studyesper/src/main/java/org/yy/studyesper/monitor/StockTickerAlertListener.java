package org.yy.studyesper.monitor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yy.studyesper.eventbean.LimitAlert;
import org.yy.studyesper.eventbean.PriceLimit;
import org.yy.studyesper.eventbean.StockTick;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class StockTickerAlertListener implements UpdateListener
{
    private final EPServiceProvider epService;
    private final PriceLimit limit;
    private final StockTick initialPriceTick;

    public StockTickerAlertListener(EPServiceProvider epService, PriceLimit limit, StockTick initialPriceTick)
    {
        this.epService = epService;
        this.limit = limit;
        this.initialPriceTick = initialPriceTick;
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        Object event =  newEvents[0].get("tick");
        StockTick tick = (StockTick) event;

        log.debug(".update Alert for stock=" + tick.getStockSymbol() +
                  "  price=" + tick.getPrice() +
                  "  initialPriceTick=" + initialPriceTick.getPrice() +
                  "  limt=" + limit.getLimitPct());

        LimitAlert alert = new LimitAlert(tick, limit, initialPriceTick.getPrice());
        epService.getEPRuntime().sendEvent(alert);
    }

    private static final Log log = LogFactory.getLog(StockTickerAlertListener.class);
}

