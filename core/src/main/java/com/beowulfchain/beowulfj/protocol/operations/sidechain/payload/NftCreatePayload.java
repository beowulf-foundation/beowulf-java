package com.beowulfchain.beowulfj.protocol.operations.sidechain.payload;

import com.beowulfchain.beowulfj.protocol.AccountName;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class NftCreatePayload extends ContractPayload {
    @JsonProperty("name")
    private String name;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("maxSupply")
    private String maxSupply;
    @JsonProperty("authorizedIssuingAccounts")
    private List<AccountName> authorizedIssuingAccounts;

    public NftCreatePayload() {
    }

    public NftCreatePayload(String name, String symbol, String maxSupply, List<AccountName> authorizedIssuingAccounts) {
        this.name = name;
        this.symbol = symbol;
        this.maxSupply = maxSupply;
        this.authorizedIssuingAccounts = authorizedIssuingAccounts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(String maxSupply) {
        this.maxSupply = maxSupply;
    }

    public List<AccountName> getAuthorizedIssuingAccounts() {
        return authorizedIssuingAccounts;
    }

    public void setAuthorizedIssuingAccounts(List<AccountName> authorizedIssuingAccounts) {
        this.authorizedIssuingAccounts = authorizedIssuingAccounts;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
