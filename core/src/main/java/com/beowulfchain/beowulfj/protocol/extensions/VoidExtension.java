package com.beowulfchain.beowulfj.protocol.extensions;

import com.beowulfchain.beowulfj.base.models.FutureExtensions;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class VoidExtension extends FutureExtensions {
    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedFutureExtensions = new ByteArrayOutputStream()) {
            byte[] extension = {0x00};
            serializedFutureExtensions.write(extension);
            return serializedFutureExtensions.toByteArray();
        } catch (IOException e) {
            throw new BeowulfInvalidTransactionException(
                    "A problem occured while transforming the extension into a byte array.", e);
        }
    }
}
