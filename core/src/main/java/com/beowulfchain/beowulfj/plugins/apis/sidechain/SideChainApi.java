package com.beowulfchain.beowulfj.plugins.apis.sidechain;

import com.beowulfchain.beowulfj.communication.CommunicationHandler;
import com.beowulfchain.beowulfj.communication.jrpc.JsonRPCRequest;
import com.beowulfchain.beowulfj.enums.BeowulfApiType;
import com.beowulfchain.beowulfj.enums.RequestMethod;
import com.beowulfchain.beowulfj.exceptions.BeowulfCommunicationException;
import com.beowulfchain.beowulfj.exceptions.BeowulfResponseException;
import com.beowulfchain.beowulfj.plugins.apis.condenser.models.AccountHistoryReturn;
import com.beowulfchain.beowulfj.plugins.apis.sidechain.model.GetBlockInfoReturn;
import com.google.api.client.http.HttpHeaders;

import java.util.Arrays;
import java.util.Collections;

public class SideChainApi {
    private SideChainApi() {
    }

    public static GetBlockInfoReturn getLatestBlockInfo(CommunicationHandler communicationHandler, String scid) throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(null,
                SideChainRequestMethod.getLatestBlockInfo.name(), Collections.emptyList());
        HttpHeaders headers = new HttpHeaders();
        headers.set("scid", scid);
        return communicationHandler.performRequest(requestObject, GetBlockInfoReturn.class, headers).get(0);
    }

    public static GetBlockInfoReturn getBlockInfo(CommunicationHandler communicationHandler, String scid, long blockNumber) throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(null,
                SideChainRequestMethod.getLatestBlockInfo.name(), Collections.singletonMap("blockNumber", blockNumber));
        HttpHeaders headers = new HttpHeaders();
        headers.set("scid", scid);
        return communicationHandler.performRequest(requestObject, GetBlockInfoReturn.class, headers).get(0);
    }
}
