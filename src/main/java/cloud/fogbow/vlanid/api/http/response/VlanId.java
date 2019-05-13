package cloud.fogbow.vlanid.api.http.response;

public class VlanId {

    private int vlanId;

    public VlanId() {}

    public VlanId(int vlanId) {
        this.vlanId = vlanId;
    }

    public int getVlanId() {
        return vlanId;
    }

    public void setVlanId(int vlanId) {
        this.vlanId = vlanId;
    }
}
