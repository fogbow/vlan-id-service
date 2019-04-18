package cloud.fogbow.vlanid.core.datastore;

import cloud.fogbow.common.datastore.FogbowDatabaseService;
import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.vlanid.core.model.VlanIdState;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class VlanIdStateRecoveryService extends FogbowDatabaseService<VlanIdState> {
    @Autowired
    private VlanIdStateRepository vlanIdStateRepository;

    private static final Logger LOGGER = Logger.getLogger(VlanIdStateRecoveryService.class);

    public void put(VlanIdState vlanIdState) throws UnexpectedException {
        safeSave(vlanIdState, this.vlanIdStateRepository);
    }

    public Map<Integer, VlanIdState> readVlanIdStates() {
        Map<Integer, VlanIdState> vlanIdStatesMap = new ConcurrentHashMap<>();

        for (VlanIdState vlanIdState : this.vlanIdStateRepository.findAll()) {
            vlanIdStatesMap.put(vlanIdState.getId(), vlanIdState);
        }

        return vlanIdStatesMap;
    }
}
