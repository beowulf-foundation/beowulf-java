package com.beowulfchain.beowulfj.protocol.operations.sidechain;

import com.beowulfchain.beowulfj.communication.CommunicationHandler;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.interfaces.ByteTransformable;
import com.beowulfchain.beowulfj.protocol.operations.sidechain.payload.ContractPayload;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SideChainOperation implements ByteTransformable {
    @JsonProperty("contractName")
    protected String contractName;
    @JsonProperty("contractAction")
    protected String contractAction;
    @JsonProperty("contractPayload")
    protected ContractPayload contractPayload;

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
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedSideChainOperation = new ByteArrayOutputStream()) {
            serializedSideChainOperation.write(BeowulfJUtils.transformStringToVarIntByteArray(CommunicationHandler.getObjectMapper().writeValueAsString(this)));
            return serializedSideChainOperation.toByteArray();
        } catch (IOException e) {
            throw new BeowulfInvalidTransactionException(
                    "A problem occured while transforming the operation into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
