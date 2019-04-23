package cloud.fogbow.vlanid.core.xmpp;

import org.apache.log4j.Logger;
import org.jamppa.component.XMPPComponent;

public class XmppComponentManager extends XMPPComponent {
    private static Logger LOGGER = Logger.getLogger(XmppComponentManager.class);

    public XmppComponentManager(String jid, String password, String xmppServerIp, int xmppServerPort, long timeout) {
        super(jid, password, xmppServerIp, xmppServerPort, timeout);

        addSetHandler(new RemoteReleaseVlanIdRequestHandler());
        addGetHandler(new RemoteGetVlanIdRequestHandler());
    }
}
