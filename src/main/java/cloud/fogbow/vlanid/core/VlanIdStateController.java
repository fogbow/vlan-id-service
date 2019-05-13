package cloud.fogbow.vlanid.core;

import cloud.fogbow.common.exceptions.InvalidParameterException;
import cloud.fogbow.common.exceptions.NoAvailableResourcesException;
import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.vlanid.constants.Messages;
import org.apache.log4j.Logger;

public class VlanIdStateController {
    private static final Logger LOGGER = Logger.getLogger(VlanIdStateController.class);

    public synchronized int getFreeVlanId() throws UnexpectedException, NoAvailableResourcesException {
        VlanIdStateHolder vlanIdStateHolder = VlanIdStateHolder.getInstance();
        Integer freeVlanId = vlanIdStateHolder.getAvailableVlanIds().poll();

        if (freeVlanId == null) throw new NoAvailableResourcesException(Messages.Error.VLAN_ID_UNAVAILABLE);

        vlanIdStateHolder.getVlanIdStates().get(freeVlanId).setAvailable(false);
        vlanIdStateHolder.getAllocatedVlanIds().add(freeVlanId);

        return freeVlanId;
    }

    public synchronized void releaseVlanId(int vlanId) throws UnexpectedException, InvalidParameterException {
        VlanIdStateHolder vlanIdStateHolder = VlanIdStateHolder.getInstance();
        boolean freeVlanId = vlanIdStateHolder.getAllocatedVlanIds().remove(vlanId);

        if (freeVlanId) {
            vlanIdStateHolder.getVlanIdStates().get(vlanId).setAvailable(true);
            boolean deleted = vlanIdStateHolder.getAvailableVlanIds().add(vlanId);
        } else {
            throw new InvalidParameterException(String.format(Messages.Error.VLAN_ID_NOT_ALLOCATED, vlanId));
        }
    }
}
