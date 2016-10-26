package com.drfits.aem.meetup.healthchecks;

import org.apache.sling.hc.api.HealthCheck;
import org.apache.sling.hc.api.Result;
import org.apache.sling.hc.api.ResultLog;
import org.apache.sling.hc.util.FormattingResultLog;

/**
 * Created by Evgeniy Fitsner <drfits@drfits.com>
 */
public abstract class BaseHc implements HealthCheck {

    public static final String TAG_NAME = "meetup";

    /**
     * Get result depends on {@link ResultLog} entry status.<br/>
     * <b>OK</b> if {@link ResultLog} <b>DO NOT</b> contains <b>WARN</b>, <b>CRITICAL</b>,
     * <b>HEALTH_CHECK_ERROR</b> entries
     *
     * @param resultLog for which result object will be generated
     * @return result of health check
     */
    protected Result getResult(final FormattingResultLog resultLog) {
        for (final ResultLog.Entry entry : resultLog) {
            switch (entry.getStatus()) {
                case WARN:
                case CRITICAL:
                case HEALTH_CHECK_ERROR:
                    return new Result(resultLog);
            }
        }
        return new Result(Result.Status.OK, "Check is passed.");
    }
}
