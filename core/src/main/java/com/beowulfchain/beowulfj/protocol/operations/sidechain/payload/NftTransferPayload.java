package com.beowulfchain.beowulfj.protocol.operations.sidechain.payload;

import com.beowulfchain.beowulfj.protocol.AccountName;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class NftTransferPayload extends ContractPayload {
    @JsonProperty("to")
    private AccountName to;
    @JsonProperty("nfts")
    private List<TokenId> nfts;

    public NftTransferPayload() {
    }

    public NftTransferPayload(AccountName to, List<TokenId> nfts) {
        this.to = to;
        this.nfts = nfts;
    }

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

        public TokenId() {
        }

        public TokenId(String symbol, List<String> ids) {
            this.symbol = symbol;
            this.ids = ids;
        }

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
