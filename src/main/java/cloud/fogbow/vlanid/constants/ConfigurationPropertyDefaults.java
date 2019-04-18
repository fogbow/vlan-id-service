package cloud.fogbow.vlanid.constants;

import java.util.concurrent.TimeUnit;

public class ConfigurationPropertyDefaults {
   // RAS CONF DEFAULTS
    // reference value is 1 second
    public static final String OPEN_ORDERS_SLEEP_TIME = Long.toString(TimeUnit.SECONDS.toMillis(1));
    // reference value is 1 second
    public static final String CLOSED_ORDERS_SLEEP_TIME = Long.toString(TimeUnit.SECONDS.toMillis(1));
    // reference value is 10 seconds
    public static final String SPAWNING_ORDERS_SLEEP_TIME = Long.toString(TimeUnit.SECONDS.toMillis(10));
    // reference value is 10 seconds
    public static final String FULFILLED_ORDERS_SLEEP_TIME = Long.toString(TimeUnit.SECONDS.toMillis(10));
    // reference value is 10 seconds
    public static final String FAILED_ORDERS_SLEEP_TIME = Long.toString(TimeUnit.SECONDS.toMillis(10));
    public static final String BUILD_NUMBER = "[testing mode]";

    // INTERCOMPONENT CONF DEFAULTS
    public static final String XMPP_TIMEOUT = Long.toString(TimeUnit.SECONDS.toMillis(5));
    // reference value is 5 seconds
    public static final String XMPP_CSC_PORT = Integer.toString(5347);

    // SSH CONF DEFAULTS
    public static final String SSH_COMMON_USER = "fogbow";
}
