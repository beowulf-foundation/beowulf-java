package com.beowulfchain.beowulfj.protocol.extensions;

import com.beowulfchain.beowulfj.base.models.FutureExtensions;
import com.beowulfchain.beowulfj.enums.ExtensionType;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class VoidExtension extends FutureExtensions {
    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedFutureExtensions = new ByteArrayOutputStream()) {
            serializedFutureExtensions.write(BeowulfJUtils.transformByteToLittleEndian(ExtensionType.EXTENSION_JSON_TYPE.getOrderId()));
            return serializedFutureExtensions.toByteArray();
        } catch (IOException e) {
            throw new BeowulfInvalidTransactionException(
                    "A problem occured while transforming the extension into a byte array.", e);
        }
    }
}
