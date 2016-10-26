package com.drfits.aem.meetup.healthchecks;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.hc.api.HealthCheck;
import org.apache.sling.hc.api.Result;
import org.apache.sling.hc.util.FormattingResultLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by Evgeniy Fitsner <drfits@drfits.com>
 */
@Component(
        metatype = true,
        description = "HC for not reachable host with timout in 1 sec"
)
@Properties({
        @Property(name = HealthCheck.NAME, value = IncorrectLocalhostHC.HC_NAME, propertyPrivate = true),
        @Property(name = HealthCheck.TAGS, value = {BaseHc.TAG_NAME}),
        @Property(
                name = HealthCheck.MBEAN_NAME,
                value = IncorrectLocalhostHC.HC_NAME,
                label = "MBean Name",
                propertyPrivate = true
        )
})
@Service(value = HealthCheck.class)
public class IncorrectLocalhostHC extends BaseHc {

    public static final String HC_NAME = "IncorrectLocalhost";
    private static final Logger log = LoggerFactory.getLogger(IncorrectLocalhostHC.class);
    private static final int PING_TIMEOUT_MS = 1 * 1000;
    private static final byte[] BAD_LOCALHOST_IP = new byte[]{126, 0, 0, 1};

    public Result execute() {
        final FormattingResultLog resultLog = new FormattingResultLog();
        try {
            final InetAddress inetAddress = InetAddress.getByAddress(BAD_LOCALHOST_IP);
            if (inetAddress.isReachable(PING_TIMEOUT_MS)) {
                resultLog.info("Host is reachable.");
            } else {
                resultLog.critical("Host is not reachable");
            }
        } catch (IOException e) {
            log.debug(e.getMessage(), e);
            log.error(e.getMessage());
            resultLog.healthCheckError(e.getMessage());
        }
        return getResult(resultLog);
    }

}
