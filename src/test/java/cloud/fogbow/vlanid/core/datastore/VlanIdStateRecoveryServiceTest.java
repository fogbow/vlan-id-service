package cloud.fogbow.vlanid.core.datastore;

import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.vlanid.core.model.VlanIdState;
import com.sun.istack.NotNull;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Ignore
@PowerMockIgnore({"javax.management.*", "org.apache.http.conn.ssl.*", "org.apache.http.conn.util.*",
       "javax.net.ssl.*" , "javax.crypto.*"})
@PrepareForTest({DatabaseManager.class})
@RunWith(PowerMockRunner.class)
//@PowerMockRunnerDelegate(SpringRunner.class)
@SpringBootTest
public class VlanIdStateRecoveryServiceTest {

    private static final int VLAN_ID = 0;

    @Autowired
    private VlanIdStateRecoveryService recoveryService;

    @Autowired
    private VlanIdStateRepository vlanIdStateRepository;

    private DatabaseManager databaseManager;

    private VlanIdState vlanIdState;

    @Before
    public void setUp() {
        this.databaseManager = Mockito.mock(DatabaseManager.class);
        PowerMockito.mockStatic(DatabaseManager.class);
        BDDMockito.given(DatabaseManager.getInstance()).willReturn(this.databaseManager);
        System.out.println(this.databaseManager.getInstance());
        this.vlanIdState = this.createVlanIdState();
    }

    @After
    public void tearDown() {
        for (VlanIdState vlanIdState : this.vlanIdStateRepository.findAll()) {
            this.vlanIdStateRepository.delete(vlanIdState);
        }
    }

    @Test
    public void testRecoveryFederatedNetwork() throws UnexpectedException {
        //set up
        Map<Integer, VlanIdState> vlanIdStateHashMap = new HashMap<>();
        vlanIdStateHashMap.put(this.vlanIdState.getId(), this.vlanIdState);
        Mockito.when(this.databaseManager.retrieveVlanIdStates()).thenReturn(vlanIdStateHashMap);

        //exercise
        this.recoveryService.put(this.vlanIdState);
        List<VlanIdState> vlanIdStates = new ArrayList<>(this.recoveryService.readVlanIdStates().values());

        //verify
        Assert.assertEquals(1, vlanIdStates.size());
        Assert.assertEquals(this.vlanIdState, vlanIdStates.get(0));
    }

    @NotNull
    private VlanIdState createVlanIdState() {
        return new VlanIdState(VLAN_ID);
    }
}
