package com.beowulfchain.beowulfj.protocol.operations.sidechain.payload;

import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.interfaces.ByteTransformable;
import com.beowulfchain.beowulfj.protocol.AccountName;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class NftTransferPayload extends ContractPayload {
    @JsonProperty("to")
    private AccountName to;
    @JsonProperty("nfts")
    private List<TokenId> nfts;

    public AccountName getTo() {
        return to;
    }

    public void setTo(AccountName to) {
        this.to = to;
    }

    public List<TokenId> getNfts() {
        return nfts;
    }

    public void setNfts(List<TokenId> nfts) {
        this.nfts = nfts;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static class TokenId {
        @JsonProperty("symbol")
        private String symbol;
        @JsonProperty("ids")
        private List<String> ids;

        public List<String> getIds() {
            return ids;
        }

        public void setIds(List<String> ids) {
            this.ids = ids;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }
    }
}
