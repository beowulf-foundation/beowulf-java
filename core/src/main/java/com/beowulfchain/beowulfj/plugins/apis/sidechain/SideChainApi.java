package com.beowulfchain.beowulfj.plugins.apis.sidechain;

import com.beowulfchain.beowulfj.communication.CommunicationHandler;
import com.beowulfchain.beowulfj.communication.jrpc.JsonRPCRequest;
import com.beowulfchain.beowulfj.exceptions.BeowulfCommunicationException;
import com.beowulfchain.beowulfj.exceptions.BeowulfResponseException;
import com.beowulfchain.beowulfj.plugins.apis.sidechain.model.*;
import com.google.api.client.http.HttpHeaders;

import java.util.Collections;
import java.util.List;

public class SideChainApi {
    private SideChainApi() {
    }

    public static SideChainBlockInfo getLatestBlockInfo(CommunicationHandler communicationHandler, String scid) throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(null,
                SideChainRequestMethod.getLatestBlockInfo.name(), Collections.emptyMap());
        HttpHeaders headers = new HttpHeaders();
        headers.set("scid", scid);
        return communicationHandler.performRequest(requestObject, SideChainBlockInfo.class, headers).get(0);
    }

    public static SideChainBlockInfo getBlockInfo(CommunicationHandler communicationHandler, String scid, long blockNumber) throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(null,
                SideChainRequestMethod.getLatestBlockInfo.name(), Collections.singletonMap("blockNumber", blockNumber));
        HttpHeaders headers = new HttpHeaders();
        headers.set("scid", scid);
        return communicationHandler.performRequest(requestObject, SideChainBlockInfo.class, headers).get(0);
    }

    public static GetStatusReturn getStatus(CommunicationHandler communicationHandler, String scid) throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(null,
                SideChainRequestMethod.getStatus.name(), Collections.emptyMap());
        HttpHeaders headers = new HttpHeaders();
        headers.set("scid", scid);
        return communicationHandler.performRequest(requestObject, GetStatusReturn.class, headers).get(0);
    }

    public static SideChainTransactionInfo getTransactionInfo(CommunicationHandler communicationHandler, String scid, String txid) throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(null,
                SideChainRequestMethod.getTransactionInfo.name(), Collections.singletonMap("txid", txid));
        HttpHeaders headers = new HttpHeaders();
        headers.set("scid", scid);
        List<SideChainTransactionInfo> transactionInfos = communicationHandler.performRequest(requestObject, SideChainTransactionInfo.class, headers);
        if (transactionInfos == null || transactionInfos.isEmpty()) {
            return null;
        }
        return transactionInfos.get(0);
    }

    public static FindOneReturn findOne(CommunicationHandler communicationHandler, String scid, FindOneRequest request) throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(null, SideChainRequestMethod.findOne.name(), request);
        HttpHeaders headers = new HttpHeaders();
        headers.set("scid", scid);
        List<FindOneReturn> findOneReturns = communicationHandler.performRequest(requestObject, FindOneReturn.class, headers);
        if (findOneReturns == null || findOneReturns.isEmpty()) {
            return null;
        }
        return findOneReturns.get(0);
    }

    public static FindReturn find(CommunicationHandler communicationHandler, String scid, FindRequest request) throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(null, SideChainRequestMethod.find.name(), request);
        HttpHeaders headers = new HttpHeaders();
        headers.set("scid", scid);
        List<FindReturn> findReturns = communicationHandler.performRequest(requestObject, FindReturn.class, headers);
        if (findReturns == null || findReturns.isEmpty()) {
            return null;
        }
        return findReturns.get(0);
    }
}
