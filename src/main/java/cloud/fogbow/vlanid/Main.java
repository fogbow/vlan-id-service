package cloud.fogbow.vlanid;

import cloud.fogbow.common.exceptions.FatalErrorException;
import cloud.fogbow.vlanid.constants.Messages;
import cloud.fogbow.vlanid.core.ApplicationFacade;
import cloud.fogbow.vlanid.core.VlanIdStateController;
import cloud.fogbow.vlanid.core.datastore.DatabaseManager;
import cloud.fogbow.vlanid.core.datastore.VlanIdStateRecoveryService;
import cloud.fogbow.vlanid.core.xmpp.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Main implements ApplicationRunner {

    @Autowired
    VlanIdStateRecoveryService recoveryService;

    private static final Logger LOGGER = Logger.getLogger(Main.class);

    private ApplicationFacade applicationFacade = ApplicationFacade.getInstance();

    @Override
    public void run(ApplicationArguments args) {
        try {
            DatabaseManager.getInstance().setVlanIdStateRecoveryService(recoveryService);

            VlanIdStateController vlanIdStateController = new VlanIdStateController();
            this.applicationFacade.setVlanIdStateController(vlanIdStateController);

            // Starting PacketSender
            while (true) {
                try {
                    PacketSenderHolder.init();
                    break;
                } catch (IllegalStateException e1) {
                    LOGGER.error(Messages.Error.NO_PACKET_SENDER, e1);
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        } catch (FatalErrorException e) {
            LOGGER.fatal(e.getMessage(), e);
            tryExit();
        }
    }

    private void tryExit() {
        if (!Boolean.parseBoolean(System.getenv("SKIP_TEST_ON_TRAVIS")))
            System.exit(1);
    }
}