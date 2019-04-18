package cloud.fogbow.vlanid.core.datastore;

import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.vlanid.core.model.VlanIdState;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class DatabaseManager implements StableStorage {
    private static final Logger LOGGER = Logger.getLogger(DatabaseManager.class);

    private static DatabaseManager instance;

    @Autowired
    private VlanIdStateRecoveryService vlanIdStateRecoveryService;

    private DatabaseManager() {
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    @Override
    public void put(VlanIdState vlanIdState) throws UnexpectedException {
        this.vlanIdStateRecoveryService.put(vlanIdState);
    }

    @Override
    public Map<Integer, VlanIdState> retrieveVlanIdStates() {
        return this.vlanIdStateRecoveryService.readVlanIdStates();
    }

    public void setVlanIdStateRecoveryService(VlanIdStateRecoveryService vlanService) {
        this.vlanIdStateRecoveryService = vlanService;
    }
}
