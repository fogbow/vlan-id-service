package cloud.fogbow.vlanid.constants;

public class Messages {

    public static class Info {
        public static final String RECEIVING_GET_VLAN_ID_REQUEST = "Get VLAN ID request received.";
        public static final String RECEIVING_RELEASE_VLAN_ID_REQUEST = "Release VLAN ID request  received.";

    }

    public static class Error {
        public static final String VLAN_ID_UNAVAILABLE = "There is no VLAN ID available.";
        public static final String VLAN_ID_NOT_ALLOCATED = "The VLAN ID %s is not allocated. Unable to release it.";
    }

    public static class Exception {
        public static final String GENERIC_EXCEPTION = "Operation returned error: %s";
    }
}
