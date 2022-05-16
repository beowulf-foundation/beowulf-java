package com.beowulfchain.beowulfj.plugins.apis.sidechain.model;

public class Index {
    private String index;
    private boolean descending;

    public Index() {
    }

    public Index(String index, boolean descending) {
        this.index = index;
        this.descending = descending;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public boolean isDescending() {
        return descending;
    }

    public void setDescending(boolean descending) {
        this.descending = descending;
    }
}
