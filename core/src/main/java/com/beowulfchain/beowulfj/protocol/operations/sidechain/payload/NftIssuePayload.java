package com.beowulfchain.beowulfj.protocol.operations.sidechain.payload;

import com.beowulfchain.beowulfj.protocol.AccountName;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class NftIssuePayload extends ContractPayload {
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("to")
    private AccountName to;
    @JsonProperty("toType")
    private String toType;
    @JsonProperty("feeSymbol")
    private String feeSymbol;

    public NftIssuePayload() {
    }

    public NftIssuePayload(String symbol, AccountName to, String toType, String feeSymbol) {
        this.symbol = symbol;
        this.to = to;
        this.toType = toType;
        this.feeSymbol = feeSymbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public AccountName getTo() {
        return to;
    }

    public void setTo(AccountName to) {
        this.to = to;
    }

    public String getToType() {
        return toType;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public String getFeeSymbol() {
        return feeSymbol;
    }

    public void setFeeSymbol(String feeSymbol) {
        this.feeSymbol = feeSymbol;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
