package com.beowulfchain.beowulfj.plugins.apis.sidechain.model;

public class GetStatusReturn {
    private long lastBlockNumber;
    private long lastBlockRefBeowulfBlockNumber;
    private long lastParsedBeowulfBlockNumber;
    private String SSCnodeVersion;
    private String chainId;

    public long getLastBlockNumber() {
        return lastBlockNumber;
    }

    public void setLastBlockNumber(long lastBlockNumber) {
        this.lastBlockNumber = lastBlockNumber;
    }

    public long getLastBlockRefBeowulfBlockNumber() {
        return lastBlockRefBeowulfBlockNumber;
    }

    public void setLastBlockRefBeowulfBlockNumber(long lastBlockRefBeowulfBlockNumber) {
        this.lastBlockRefBeowulfBlockNumber = lastBlockRefBeowulfBlockNumber;
    }

    public long getLastParsedBeowulfBlockNumber() {
        return lastParsedBeowulfBlockNumber;
    }

    public void setLastParsedBeowulfBlockNumber(long lastParsedBeowulfBlockNumber) {
        this.lastParsedBeowulfBlockNumber = lastParsedBeowulfBlockNumber;
    }

    public String getSSCnodeVersion() {
        return SSCnodeVersion;
    }

    public void setSSCnodeVersion(String SSCnodeVersion) {
        this.SSCnodeVersion = SSCnodeVersion;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }
}
