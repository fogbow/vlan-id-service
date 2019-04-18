package cloud.fogbow.vlanid.core.datastore;

import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.vlanid.core.model.VlanIdState;

import java.util.Map;

public interface StableStorage {
    /**
     * Add or update the vlanIdState into database, so it can be recovered in case of a crash.
     * @param vlanIdState {@link VlanIdState}
     */
    public void put(VlanIdState vlanIdState) throws UnexpectedException;

    /**
     * Retrieve all vlan id states.
     * @return A list of {@link VlanIdState}
     */
    public Map<Integer, VlanIdState> retrieveVlanIdStates();
}
