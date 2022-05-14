package com.beowulfchain.beowulfj.plugins.apis.sidechain.model;

import java.util.List;
import java.util.Map;

public class FindRequest {
    private String contract;
    private String table;
    private Map<String, Object> query;
    private long limit;
    private long offset;
    private List<Object> indexes;

    public FindRequest() {
    }

    public FindRequest(String contract, String table, Map<String, Object> query, long limit, long offset, List<Object> indexes) {
        this.contract = contract;
        this.table = table;
        this.query = query;
        this.limit = limit;
        this.offset = offset;
        this.indexes = indexes;
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

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public List<Object> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Object> indexes) {
        this.indexes = indexes;
    }
}
