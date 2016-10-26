package com.drfits.aem.meetup.healthchecks;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.hc.api.HealthCheck;
import org.apache.sling.hc.api.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * Created by Evgeniy Fitsner <drfits@drfits.com>
 */
@Component(metatype = true)
@Properties({
        @Property(name = HealthCheck.NAME, value = ScheduledLocalhostAvailabilityHC.HC_NAME, propertyPrivate = true),
        @Property(name = HealthCheck.TAGS, value = {BaseHc.TAG_NAME}),
        @Property(
                name = HealthCheck.MBEAN_NAME,
                value = ScheduledLocalhostAvailabilityHC.HC_NAME,
                label = "MBean Name",
                propertyPrivate = true
        ),
        @Property(
                name = HealthCheck.ASYNC_CRON_EXPRESSION,
                value = "0 0/1 * 1/1 * ? *",
                label = "Cron expression"
        )
})
@Service(value = HealthCheck.class)
public class ScheduledLocalhostAvailabilityHC extends BaseHc {

    public static final String HC_NAME = "ScheduledLocalhostAvailability";
    private static final Logger log = LoggerFactory.getLogger(LocalhostAvailabilityHC.class);

    public Result execute() {
        final String currentTime = LocalDateTime.now().toString();
        log.debug("Checked by cron: {}", currentTime);
        return new Result(Result.Status.OK, "Checked at: " + currentTime);
    }

}
