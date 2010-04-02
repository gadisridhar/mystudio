package org.yy.studyesper.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.espertech.esper.client.EPRuntime;

/**
 * Utility methods for monitoring a EPRuntime instance.
 */
public class EPRuntimeUtil
{
    public static boolean awaitCompletion(EPRuntime epRuntime,
                                       int numEventsExpected,
                                       int numSecAwait,
                                       int numSecThreadSleep,
                                       int numSecThreadReport)
    {
        log.info(".awaitCompletion Waiting for completion, expecting " + numEventsExpected +
                 " events within " + numSecAwait + " sec");
        
        int secondsWaitTotal = numSecAwait;
        long lastNumEventsProcessed = 0;
        int secondsUntilReport = 0;

        long startTimeMSec = System.currentTimeMillis();
        long endTimeMSec = 0;

        while (secondsWaitTotal > 0)
        {
            try
            {
                Thread.sleep(numSecThreadSleep * 1000);
            }
            catch (InterruptedException ex)
            {
            }

            secondsWaitTotal -= numSecThreadSleep;
            secondsUntilReport += numSecThreadSleep;
            long currNumEventsProcessed = epRuntime.getNumEventsEvaluated();

            if (secondsUntilReport > numSecThreadReport)
            {
                long numPerSec = (currNumEventsProcessed - lastNumEventsProcessed) / numSecThreadReport;
                log.info(".awaitCompletion received=" + epRuntime.getNumEventsEvaluated() +
                         "  emitted=" + epRuntime.getNumEventsEvaluated() +
                         "  processed=" + currNumEventsProcessed +
                         "  perSec=" + numPerSec);
                lastNumEventsProcessed = currNumEventsProcessed;
                secondsUntilReport = 0;
            }

            // Completed loop if the total event count has been reached
            if (epRuntime.getNumEventsEvaluated() == numEventsExpected)
            {
                endTimeMSec = System.currentTimeMillis();
                break;
            }
        }

        if (endTimeMSec == 0)
        {
            log.info(".awaitCompletion Not completed within " + numSecAwait + " seconds");
            return false;
        }

        long totalUnitsProcessed = epRuntime.getNumEventsEvaluated();
        long deltaTimeSec = (endTimeMSec - startTimeMSec) / 1000;

        long numPerSec = 0;
        if (deltaTimeSec > 0)
        {
            numPerSec = (totalUnitsProcessed) / deltaTimeSec;
        }
        else
        {
            numPerSec = -1;
        }

        log.info(".awaitCompletion Completed, sec=" + deltaTimeSec + "  avgPerSec=" + numPerSec);

        long numReceived = epRuntime.getNumEventsEvaluated();
        long numReceivedPerSec = 0;
        if (deltaTimeSec > 0)
        {
            numReceivedPerSec = (numReceived) / deltaTimeSec;
        }
        else
        {
            numReceivedPerSec = -1;
        }

        log.info(".awaitCompletion Runtime reports, numReceived=" + numReceived +
                 "  numProcessed=" + epRuntime.getNumEventsEvaluated() +
                 "  perSec=" +  numReceivedPerSec +
                 "  numEmitted=" + epRuntime.getNumEventsEvaluated()
                 );

        return true;
    }
    
    private static final Log log = LogFactory.getLog(EPRuntimeUtil.class);
}
