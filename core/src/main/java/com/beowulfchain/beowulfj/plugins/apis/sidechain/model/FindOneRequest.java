package com.beowulfchain.beowulfj.plugins.apis.sidechain.model;

import java.util.Map;

public class FindOneRequest {
    private String contract;
    private String table;
    private Map<String, Object> query;

    public FindOneRequest() {
    }

    public FindOneRequest(String contract, String table, Map<String, Object> query) {
        this.contract = contract;
        this.table = table;
        this.query = query;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Map<String, Object> getQuery() {
        return query;
    }

    public void setQuery(Map<String, Object> query) {
        this.query = query;
    }
}
