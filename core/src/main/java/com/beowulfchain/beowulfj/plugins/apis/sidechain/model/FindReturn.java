package com.beowulfchain.beowulfj.plugins.apis.sidechain.model;

public class FindReturn {
    private long _id;
    private String account;
    private String ownedBy;
    Object lockedTokens;
    Object properties;
    private String previousAccount;
    private String previousOwnedBy;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    public Object getLockedTokens() {
        return lockedTokens;
    }

    public void setLockedTokens(Object lockedTokens) {
        this.lockedTokens = lockedTokens;
    }

    public Object getProperties() {
        return properties;
    }

    public void setProperties(Object properties) {
        this.properties = properties;
    }

    public String getPreviousAccount() {
        return previousAccount;
    }

    public void setPreviousAccount(String previousAccount) {
        this.previousAccount = previousAccount;
    }

    public String getPreviousOwnedBy() {
        return previousOwnedBy;
    }

    public void setPreviousOwnedBy(String previousOwnedBy) {
        this.previousOwnedBy = previousOwnedBy;
    }
}
