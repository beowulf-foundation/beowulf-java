package com.beowulfchain.beowulfj.plugins.apis.sidechain.model;

import java.util.List;

public class FindOneReturn {
    private long _id;
    private String issuer;
    private String symbol;
    private String name;
    private String orgName;
    private String productName;
    private String metadata;
    private long maxSupply;
    private long supply;
    private long circulatingSupply;
    private boolean delegationEnabled;
    private long undelegationCooldown;
    List<Object> authorizedIssuingAccounts;
    List<Object> authorizedIssuingContracts;
    Object properties;
    List<Object> groupBy;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public long getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(long maxSupply) {
        this.maxSupply = maxSupply;
    }

    public long getSupply() {
        return supply;
    }

    public void setSupply(long supply) {
        this.supply = supply;
    }

    public long getCirculatingSupply() {
        return circulatingSupply;
    }

    public void setCirculatingSupply(long circulatingSupply) {
        this.circulatingSupply = circulatingSupply;
    }

    public boolean isDelegationEnabled() {
        return delegationEnabled;
    }

    public void setDelegationEnabled(boolean delegationEnabled) {
        this.delegationEnabled = delegationEnabled;
    }

    public long getUndelegationCooldown() {
        return undelegationCooldown;
    }

    public void setUndelegationCooldown(long undelegationCooldown) {
        this.undelegationCooldown = undelegationCooldown;
    }

    public List<Object> getAuthorizedIssuingAccounts() {
        return authorizedIssuingAccounts;
    }

    public void setAuthorizedIssuingAccounts(List<Object> authorizedIssuingAccounts) {
        this.authorizedIssuingAccounts = authorizedIssuingAccounts;
    }

    public List<Object> getAuthorizedIssuingContracts() {
        return authorizedIssuingContracts;
    }

    public void setAuthorizedIssuingContracts(List<Object> authorizedIssuingContracts) {
        this.authorizedIssuingContracts = authorizedIssuingContracts;
    }

    public Object getProperties() {
        return properties;
    }

    public void setProperties(Object properties) {
        this.properties = properties;
    }

    public List<Object> getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(List<Object> groupBy) {
        this.groupBy = groupBy;
    }
}
