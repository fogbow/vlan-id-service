package cloud.fogbow.vlanid;

import cloud.fogbow.common.exceptions.FatalErrorException;
import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.vlanid.constants.ConfigurationPropertyDefaults;
import cloud.fogbow.vlanid.constants.Messages;
import cloud.fogbow.vlanid.core.ApplicationFacade;
import cloud.fogbow.vlanid.core.PropertiesHolder;
import cloud.fogbow.vlanid.core.VlanIdStateController;
import cloud.fogbow.vlanid.core.datastore.DatabaseManager;
import cloud.fogbow.vlanid.core.datastore.VlanIdStateRecoveryService;
import cloud.fogbow.vlanid.core.model.VlanIdState;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Main implements ApplicationRunner {
    private static final Logger LOGGER = Logger.getLogger(Main.class);

    public static final Pattern RANGE_PATTERN = Pattern.compile("(\\d)+\\-(\\d)+");

    @Autowired
    private VlanIdStateRecoveryService recoveryService;

    private ApplicationFacade applicationFacade = ApplicationFacade.getInstance();

    @Override
    public void run(ApplicationArguments args) {
        try {
            DatabaseManager.getInstance().setVlanIdStateRecoveryService(recoveryService);

            VlanIdStateController vlanIdStateController = new VlanIdStateController();
            this.applicationFacade.setVlanIdStateController(vlanIdStateController);

            String vlanIdsRange = PropertiesHolder.getInstance().getProperty(ConfigurationPropertyDefaults.VLAN_IDS);
            initializeVlanIdDatabase(vlanIdsRange);
        } catch (FatalErrorException|UnexpectedException e) {
            LOGGER.fatal(e.getMessage(), e);
            tryExit();
        }
    }

    private void initializeVlanIdDatabase(String vlanIdsRange) throws UnexpectedException {
        Matcher matcher = RANGE_PATTERN.matcher(vlanIdsRange);
        if (matcher.matches()) {
            int start = Integer.parseInt(vlanIdsRange.split("\\-")[0]);
            int end = Integer.parseInt(vlanIdsRange.split("\\-")[1]);

            for (int i = start; i <= end; i++) {
                try {
                    recoveryService.put(new VlanIdState(i));
                } catch (UnexpectedException e) {
                    throw e;
                }
                // TODO DFNS see the exception that is thrown when the vlan id is already on the db
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void tryExit() {
        if (!Boolean.parseBoolean(System.getenv("SKIP_TEST_ON_TRAVIS")))
            System.exit(1);
    }
}