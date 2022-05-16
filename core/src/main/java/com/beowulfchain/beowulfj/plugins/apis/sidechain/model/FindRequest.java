package com.beowulfchain.beowulfj.plugins.apis.sidechain.model;

import java.util.List;
import java.util.Map;

public class FindRequest extends FindOneRequest {
    private long limit;
    private long offset;
    private List<Index> indexes;

    public FindRequest() {
    }

    public FindRequest(String contract, String table, Map<String, Object> query, long limit, long offset, List<Index> indexes) {
        super(contract, table, query);
        this.limit = limit;
        this.offset = offset;
        this.indexes = indexes;
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

    public List<Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Index> indexes) {
        this.indexes = indexes;
    }
}
