package cloud.fogbow.vlanid.core.datastore;

import cloud.fogbow.vlanid.core.model.VlanIdState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VlanIdStateRepository extends JpaRepository<VlanIdState, Integer> {
}
