package cloud.fogbow.vlanid.core.xmpp;

import cloud.fogbow.vlanid.core.ApplicationFacade;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.jamppa.component.handler.AbstractQueryHandler;
import org.xmpp.packet.IQ;

public class RemoteReleaseVlanIdRequestHandler extends AbstractQueryHandler {
    private static final Logger LOGGER = Logger.getLogger(RemoteReleaseVlanIdRequestHandler.class);

    private static final String QUERY = "query";
    private static final String RELEASE_VLAN_ID = "releaseVlanId";
    private static final String VLAN_ID = "vlanId";

    public RemoteReleaseVlanIdRequestHandler() {
        super(RELEASE_VLAN_ID);
    }

    @Override
    public IQ handle(IQ iq) {
        String vlanId = unmarshalOrderId(iq);
        IQ response = IQ.createResultIQ(iq);

        try {
             ApplicationFacade.getInstance().releaseVlanId(Integer.parseInt(vlanId));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return response;
    }

    private String unmarshalOrderId(IQ iq) {
        Element queryElement = iq.getElement().element(QUERY);
        Element remoteOrderIdElement = queryElement.element(VLAN_ID);
        String vlanId = remoteOrderIdElement.getText();

        return vlanId;
    }
}
