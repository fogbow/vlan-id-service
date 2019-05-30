package cloud.fogbow.vlanid.constants;

public class ApiDocumentation {
    public static class ApiInfo {
        public static final String API_TITLE = "Fogbow VLAN ID Service API";
        public static final String API_DESCRIPTION =
                "This documentation introduces readers to Fogbow VLAN ID REST API, provides guidelines on\n" +
                        "how to use it, and describes the available features accessible from it.";

        public static final String CONTACT_NAME = "Fogbow";
        public static final String CONTACT_URL = "https://www.fogbow.cloud";
        public static final String CONTACT_EMAIL = "contact@fogbow.cloud";
    }

    public static class VlanId {
        public static final String API = "Acquires and releases a VLAN ID for creating a new advanced federated network.";
        public static final String GET_OPERATION = "Returns an available VLAN ID.";
        public static final String RELEASE_OPERATION = "Releases an allocated VLAN ID.";
        public static final String RELEASE_VLAN_ID_REQUEST_BODY =
                "The body of the request must specify the allocated VLAN ID that should be released.";

    }

    public static class Version {
        public static final String API = "Queries the version of the service's API.";
        public static final String GET_OPERATION = "Returns the version of the API.";
    }
}
