package com.beowulfchain.beowulfj.protocol.operations.sidechain;

import com.beowulfchain.beowulfj.communication.CommunicationHandler;
import com.beowulfchain.beowulfj.protocol.operations.sidechain.payload.ContractPayload;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SideChainOperation {
    @JsonProperty("contractName")
    protected String contractName;
    @JsonProperty("contractAction")
    protected String contractAction;
    @JsonProperty("contractPayload")
    protected ContractPayload contractPayload;

    public SideChainOperation() {
    }

    public SideChainOperation(String contractName, String contractAction, ContractPayload contractPayload) {
        this.contractName = contractName;
        this.contractAction = contractAction;
        this.contractPayload = contractPayload;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractAction() {
        return contractAction;
    }

    public void setContractAction(String contractAction) {
        this.contractAction = contractAction;
    }

    public ContractPayload getContractPayload() {
        return contractPayload;
    }

    public void setContractPayload(ContractPayload contractPayload) {
        this.contractPayload = contractPayload;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String toJson() {
        try {
            return CommunicationHandler.getObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{}";
    }
}
