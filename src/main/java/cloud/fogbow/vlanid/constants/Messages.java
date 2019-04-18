package cloud.fogbow.vlanid.constants;

public class Messages {

    public static class Info {
        public static final String NO_REMOTE_COMMUNICATION_CONFIGURED = "No remote communication configured.";
    }

    public static class Error {
        public static final String NO_PACKET_SENDER = "PacketSender was not initialized. Trying again.";
        public static final String VLAN_ID_UNAVAILABLE = "There is no VLAN ID available.";
        public static final String VLAN_ID_NOT_ALLOCATED = "The VLAN ID %s is not allocated. Unable to turn it available.";
    }
}
