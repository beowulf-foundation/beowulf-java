package com.beowulfchain.beowulfj.plugins.apis.sidechain.model;

import java.util.List;

public class GetBlockInfoReturn {
    private long _id;
    private long blockNumber;
    private long refBeowulfBlockNumber;
    private String refBeowulfBlockId;
    private String prevRefBeowulfBlockId;
    private String previousHash;
    private String previousDatabaseHash;
    private String timestamp;
    List<SideChainTransactionInfo> transactions;
    List<Object> virtualTransactions;
    private String hash;
    private String databaseHash;
    private String merkleRoot;
    private String round;
    private String roundHash;
    private String supernode;
    private String signingKey;
    private String roundSignature;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(long blockNumber) {
        this.blockNumber = blockNumber;
    }

    public long getRefBeowulfBlockNumber() {
        return refBeowulfBlockNumber;
    }

    public void setRefBeowulfBlockNumber(long refBeowulfBlockNumber) {
        this.refBeowulfBlockNumber = refBeowulfBlockNumber;
    }

    public String getRefBeowulfBlockId() {
        return refBeowulfBlockId;
    }

    public void setRefBeowulfBlockId(String refBeowulfBlockId) {
        this.refBeowulfBlockId = refBeowulfBlockId;
    }

    public String getPrevRefBeowulfBlockId() {
        return prevRefBeowulfBlockId;
    }

    public void setPrevRefBeowulfBlockId(String prevRefBeowulfBlockId) {
        this.prevRefBeowulfBlockId = prevRefBeowulfBlockId;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getPreviousDatabaseHash() {
        return previousDatabaseHash;
    }

    public void setPreviousDatabaseHash(String previousDatabaseHash) {
        this.previousDatabaseHash = previousDatabaseHash;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<SideChainTransactionInfo> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<SideChainTransactionInfo> transactions) {
        this.transactions = transactions;
    }

    public List<Object> getVirtualTransactions() {
        return virtualTransactions;
    }

    public void setVirtualTransactions(List<Object> virtualTransactions) {
        this.virtualTransactions = virtualTransactions;
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

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public void setMerkleRoot(String merkleRoot) {
        this.merkleRoot = merkleRoot;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getRoundHash() {
        return roundHash;
    }

    public void setRoundHash(String roundHash) {
        this.roundHash = roundHash;
    }

    public String getSupernode() {
        return supernode;
    }

    public void setSupernode(String supernode) {
        this.supernode = supernode;
    }

    public String getSigningKey() {
        return signingKey;
    }

    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }

    public String getRoundSignature() {
        return roundSignature;
    }

    public void setRoundSignature(String roundSignature) {
        this.roundSignature = roundSignature;
    }
}
