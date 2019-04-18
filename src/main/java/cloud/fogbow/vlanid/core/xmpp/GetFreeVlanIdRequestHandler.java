package cloud.fogbow.vlanid.core.xmpp;

import cloud.fogbow.vlanid.core.ApplicationFacade;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.jamppa.component.handler.AbstractQueryHandler;
import org.xmpp.packet.IQ;

public class GetFreeVlanIdRequestHandler extends AbstractQueryHandler {
    private static final Logger LOGGER = Logger.getLogger(GetFreeVlanIdRequestHandler.class);

    private static final String QUERY = "query";
    private static final String GET_FREE_VLAN_ID = "getFreeVlanId";
    private static final String AVAILABLE_VLAN_ID = "availableVlanId";
    private static final String AVAILABLE_VLAN_ID_CLASS_NAME = "availableVlanIdClassName";

    public GetFreeVlanIdRequestHandler() {
        super(GET_FREE_VLAN_ID);
    }

    @Override
    public IQ handle(IQ iq) {
        IQ response = IQ.createResultIQ(iq);

        try {
             int freeVlanId = ApplicationFacade.getInstance().getFreeVlanId();
             updateResponse(response, freeVlanId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return response;
    }

    private void updateResponse(IQ response, Integer vlanId) {
        Element queryEl = response.getElement().addElement(QUERY, GET_FREE_VLAN_ID);
        Element availableVlanIdElement = queryEl.addElement(AVAILABLE_VLAN_ID);

        Element availableVlanIdClassNameElement = queryEl.addElement(AVAILABLE_VLAN_ID_CLASS_NAME);
        availableVlanIdClassNameElement.setText(vlanId.getClass().getName());

        availableVlanIdElement.setText(new Gson().toJson(vlanId));
    }
}
