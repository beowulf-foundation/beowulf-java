package com.beowulfchain.beowulfj.protocol.operations.sidechain.payload;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class ContractPayload {
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
