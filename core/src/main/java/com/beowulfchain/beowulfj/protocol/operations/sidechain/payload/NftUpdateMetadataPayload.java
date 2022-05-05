package com.beowulfchain.beowulfj.protocol.operations.sidechain.payload;

import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.interfaces.ByteTransformable;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class NftUpdateMetadataPayload extends ContractPayload {
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("metadata")
    private Metadata metadata;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static class Metadata implements ByteTransformable {
        @JsonProperty("url")
        private String url;
        @JsonProperty("image")
        private String image;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @Override
        public byte[] toByteArray() throws BeowulfInvalidTransactionException {
            try (ByteArrayOutputStream serializedSideChainOperation = new ByteArrayOutputStream()) {
                serializedSideChainOperation.write(BeowulfJUtils.transformStringToVarIntByteArray(this.getUrl()));
                serializedSideChainOperation.write(BeowulfJUtils.transformStringToVarIntByteArray(this.getImage()));
                return serializedSideChainOperation.toByteArray();
            } catch (IOException e) {
                throw new BeowulfInvalidTransactionException(
                        "A problem occured while transforming the operation into a byte array.", e);
            }
        }
    }
}
