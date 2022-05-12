package com.beowulfchain.beowulfj.plugins.apis.sidechain.model;

public class SideChainTransactionInfo {
    private long refBeowulfBlockNumber;
    private String transactionId;
    private String sender;
    private String contract;
    private String action;
    private String payload;
    private String executedCodeHash;
    private String hash;
    private String databaseHash;
    private String logs;

    public long getRefBeowulfBlockNumber() {
        return refBeowulfBlockNumber;
    }

    public void setRefBeowulfBlockNumber(long refBeowulfBlockNumber) {
        this.refBeowulfBlockNumber = refBeowulfBlockNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getExecutedCodeHash() {
        return executedCodeHash;
    }

    public void setExecutedCodeHash(String executedCodeHash) {
        this.executedCodeHash = executedCodeHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getDatabaseHash() {
        return databaseHash;
    }

    public void setDatabaseHash(String databaseHash) {
        this.databaseHash = databaseHash;
    }

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }
}
