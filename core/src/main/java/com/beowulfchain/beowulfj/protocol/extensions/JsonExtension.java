package com.beowulfchain.beowulfj.protocol.extensions;

import com.beowulfchain.beowulfj.base.models.FutureExtensions;
import com.beowulfchain.beowulfj.enums.ExtensionType;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.protocol.ExtensionValue;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class JsonExtension extends FutureExtensions {
    @JsonProperty("value")
    private ExtensionValue value;

    @JsonCreator
    public JsonExtension(@JsonProperty("value") ExtensionValue value) {
        this.setValue(value);
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedFutureExtensions = new ByteArrayOutputStream()) {
            serializedFutureExtensions.write(BeowulfJUtils.transformByteToLittleEndian(ExtensionType.EXTENSION_JSON_TYPE.getOrderId()));
            serializedFutureExtensions.write(this.value.toByteArray());
            return serializedFutureExtensions.toByteArray();
        } catch (IOException e) {
            throw new BeowulfInvalidTransactionException(
                    "A problem occured while transforming the extension into a byte array.", e);
        }
    }

    public ExtensionValue getValue() {
        return value;
    }

    public void setValue(ExtensionValue value) {
        this.value = value;
    }
}