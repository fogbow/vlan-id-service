package cloud.fogbow.vlanid.core;


import cloud.fogbow.vlanid.core.datastore.DatabaseManager;
import cloud.fogbow.vlanid.core.model.VlanIdState;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

public class VlanIdStateHolder {
    private static VlanIdStateHolder instance;

    private Map<Integer, VlanIdState> vlanIdStates;
    private Queue<Integer> availableVlanIds;
    private Queue<Integer> allocatedVlanIds;

    private VlanIdStateHolder() {
        // retrieve from database
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        this.vlanIdStates = databaseManager.retrieveVlanIdStates();
        this.vlanIdStates.put(255, new VlanIdState(255));
        this.initializeVlanStates();
    }

    public static synchronized VlanIdStateHolder getInstance() {
        if (instance == null) {
            instance = new VlanIdStateHolder();
        }
        return instance;
    }

    private void initializeVlanStates() {
        this.availableVlanIds = new ConcurrentLinkedDeque<>();
        this.allocatedVlanIds = new ConcurrentLinkedDeque<>();

        this.availableVlanIds.addAll(this.vlanIdStates.keySet().stream().filter(
                vlanId -> this.vlanIdStates.get(vlanId).isAvailable()).collect(Collectors.toList()));

        this.allocatedVlanIds.addAll(this.vlanIdStates.keySet().stream().filter(
                vlanId -> !this.vlanIdStates.get(vlanId).isAvailable()).collect(Collectors.toList()));
    }

    public Map<Integer, VlanIdState> getVlanIdStates() {
        return this.vlanIdStates;
    }

    public Queue<Integer> getAvailableVlanIds() {
        return this.availableVlanIds;
    }

    public Queue<Integer> getAllocatedVlanIds() {
        return this.allocatedVlanIds;
    }
}
