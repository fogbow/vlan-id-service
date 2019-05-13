package cloud.fogbow.vlanid.core;

import cloud.fogbow.common.exceptions.InvalidParameterException;
import cloud.fogbow.common.exceptions.NoAvailableResourcesException;
import cloud.fogbow.common.exceptions.UnexpectedException;

public class ApplicationFacade {

    private static ApplicationFacade instance;
    private VlanIdStateController vlanIdStateController;

    public static synchronized ApplicationFacade getInstance() {
        if (instance == null) {
            instance = new ApplicationFacade();
        }
        return instance;
    }

    public int getVlanId() throws UnexpectedException, NoAvailableResourcesException {
        return this.vlanIdStateController.getFreeVlanId();
    }

    public void releaseVlanId(int vlanId) throws UnexpectedException, InvalidParameterException {
        this.vlanIdStateController.releaseVlanId(vlanId);
    }

    public void setVlanIdStateController(VlanIdStateController vlanIdStateController) {
        this.vlanIdStateController = vlanIdStateController;
    }
}
