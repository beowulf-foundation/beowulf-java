package com.beowulfchain.beowulfj.protocol;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.interfaces.ByteTransformable;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.security.InvalidParameterException;

public class ExtensionValue implements ByteTransformable {
    @JsonProperty("data")
    private String data;

    public ExtensionValue() {
    }

    public ExtensionValue(String data) {
        this.setData(data);
    }

    @JsonValue
    public String getData() {
        return data;
    }

    public void setData(String data) {
        if (data == null) {
            throw new InvalidParameterException("Value of Extension not null");
        } else {
            this.data = data;
        }
    }

    //TODO: need func return Hashcode value

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        return BeowulfJUtils.transformStringToVarIntByteArray(this.data);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (!(otherObject instanceof ExtensionValue)) {
            return false;
        }
        ExtensionValue otherExtensionValue = (ExtensionValue) otherObject;
        return this.getData().equals(otherExtensionValue.getData());
    }

}