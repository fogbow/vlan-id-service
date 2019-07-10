package cloud.fogbow.vlanid.api.http.request;

import cloud.fogbow.common.exceptions.InvalidParameterException;
import cloud.fogbow.common.exceptions.NoAvailableResourcesException;
import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.vlanid.constants.Messages;
import cloud.fogbow.vlanid.core.ApplicationFacade;
import cloud.fogbow.vlanid.constants.ApiDocumentation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = VlanId.VLAN_ID_ENDPOINT)
@Api(description = ApiDocumentation.VlanId.API)
public class VlanId {

    public static final String VLAN_ID_ENDPOINT = "vlanId";

    private final Logger LOGGER = Logger.getLogger(VlanId.class);

    @ApiOperation(value = ApiDocumentation.VlanId.GET_OPERATION)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<cloud.fogbow.vlanid.api.http.response.VlanId> getVlanId() throws UnexpectedException, NoAvailableResourcesException {
        LOGGER.info(Messages.Info.RECEIVING_GET_VLAN_ID_REQUEST);

        try {
            int vlanId = ApplicationFacade.getInstance().getVlanId();
            cloud.fogbow.vlanid.api.http.response.VlanId vlanIdResponse = new cloud.fogbow.vlanid.api.http.response.VlanId(vlanId);
            return new ResponseEntity<>(vlanIdResponse, HttpStatus.OK);
        } catch (NoAvailableResourcesException e) {
            return new ResponseEntity<>(new cloud.fogbow.vlanid.api.http.response.VlanId(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            LOGGER.info(String.format(cloud.fogbow.common.constants.Messages.Exception.OPERATION_RETURNED_ERROR_S, e.getMessage()), e);
            throw e;
        }
    }

    @ApiOperation(value = ApiDocumentation.VlanId.RELEASE_OPERATION)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<cloud.fogbow.vlanid.api.http.response.VlanId> releaseVlanId(
            @ApiParam(value = ApiDocumentation.VlanId.RELEASE_VLAN_ID_REQUEST_BODY)
            @RequestBody cloud.fogbow.vlanid.api.http.response.VlanId vlanId)
            throws Exception {

        try {
            LOGGER.info(String.format(Messages.Info.RECEIVING_RELEASE_VLAN_ID_REQUEST, vlanId));
            ApplicationFacade.getInstance().releaseVlanId(vlanId.getVlanId());
            return new ResponseEntity<>(new cloud.fogbow.vlanid.api.http.response.VlanId(vlanId.getVlanId()), HttpStatus.OK);
        } catch (InvalidParameterException e) {
            return new ResponseEntity<>(new cloud.fogbow.vlanid.api.http.response.VlanId(vlanId.getVlanId()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.info(String.format(Messages.Exception.GENERIC_EXCEPTION, e.getMessage()));
            throw e;
        }
    }
}
