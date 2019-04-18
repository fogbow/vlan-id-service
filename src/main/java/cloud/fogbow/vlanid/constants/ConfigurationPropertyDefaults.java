package cloud.fogbow.vlanid.constants;

import java.util.concurrent.TimeUnit;

public class ConfigurationPropertyDefaults {
    public static final String BUILD_NUMBER = "[testing mode]";

    // INTERCOMPONENT CONF DEFAULTS
    public static final String XMPP_TIMEOUT = Long.toString(TimeUnit.SECONDS.toMillis(5));
    // reference value is 5 seconds
    public static final String XMPP_CSC_PORT = Integer.toString(5347);

    public static final String VLAN_IDS = "vlan_ids";
}
