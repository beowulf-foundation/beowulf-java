/*
 *  Copyright (c) 2019.
 *  @author trongcauta
 *  @email trongcauhcmus@gmail.com
 */

package com.beowulfchain.beowulfj.protocol.operations;

import com.beowulfchain.beowulfj.base.models.FutureExtensions;
import com.beowulfchain.beowulfj.enums.OperationType;
import com.beowulfchain.beowulfj.enums.PrivateKeyType;
import com.beowulfchain.beowulfj.enums.ValidationType;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.interfaces.SignatureObject;
import com.beowulfchain.beowulfj.protocol.AccountName;
import com.beowulfchain.beowulfj.protocol.Asset;
import com.beowulfchain.beowulfj.protocol.AssetInfo;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joou.UInteger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SmtCreateOperation extends Operation {

    @JsonProperty("control_account")
    private AccountName controlAccount;
    @JsonProperty("creator")
    private AccountName creator;
    @JsonProperty("symbol")
    private AssetInfo symbol;
    @JsonProperty("smt_creation_fee")
    private Asset smtCreationFee;
    @JsonProperty("precision")
    private UInteger precision;
    @JsonProperty("extensions")
    private List<FutureExtensions> extensions;
    @JsonProperty("max_supply")
    private long maxSupply;

    @JsonCreator
    public SmtCreateOperation(@JsonProperty("control_account") AccountName controlAccount,
                              @JsonProperty("creator") AccountName creator,
                              @JsonProperty("symbol") AssetInfo symbol,
                              @JsonProperty("smt_creation_fee") Asset smtCreationFee,
                              @JsonProperty("precision") UInteger precision,
                              @JsonProperty("extensions") List<FutureExtensions> extensions,
                              @JsonProperty("max_supply") long maxSupply) {
        super(false);
        this.setControlAccount(controlAccount);
        this.setCreator(creator);
        this.setSmtCreationFee(smtCreationFee);
        this.setPrecision(precision);
        this.setSymbol(symbol);
        this.setExtensions(extensions);
        this.setMaxSupply(maxSupply);

    }

    public AccountName getControlAccount() {
        return controlAccount;
    }

    public void setControlAccount(AccountName controlAccount) {
        this.controlAccount = controlAccount;
    }

    public AssetInfo getSymbol() {
        return symbol;
    }

    public void setSymbol(AssetInfo symbol) {
        this.symbol = symbol;
    }

    public Asset getSmtCreationFee() {
        return smtCreationFee;
    }

    public void setSmtCreationFee(Asset smtCreationFee) {
        this.smtCreationFee = smtCreationFee;
    }

    public UInteger getPrecision() {
        return precision;
    }

    public void setPrecision(UInteger precision) {
        this.precision = precision;
    }

    public AccountName getCreator() {
        return creator;
    }

    public void setCreator(AccountName creator) {
        this.creator = creator;
    }

    public long getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(long maxSupply) {
        this.maxSupply = maxSupply;
    }

    public List<FutureExtensions> getExtensions() {
        if (extensions == null) {
            extensions = new ArrayList<>();
        }
        return extensions;
    }

    public void setExtensions(List<FutureExtensions> extensions) {
        this.extensions = extensions;
    }

    @Override
    public Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getControlAccount(), PrivateKeyType.OWNER);
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedSmtCreateOperation = new ByteArrayOutputStream()) {
            serializedSmtCreateOperation.write(
                    BeowulfJUtils.transformIntToVarIntByteArray(OperationType.SMT_CREATE_OPERATION.getOrderId()));
            serializedSmtCreateOperation.write(this.getControlAccount().toByteArray());
            serializedSmtCreateOperation.write(this.getSymbol().toByteArray());
            serializedSmtCreateOperation.write(this.getCreator().toByteArray());
            serializedSmtCreateOperation.write(this.getSmtCreationFee().toByteArray());
            serializedSmtCreateOperation.write(BeowulfJUtils.transformByteToLittleEndian(this.getPrecision().byteValue()));

            serializedSmtCreateOperation.write(BeowulfJUtils.transformIntToVarIntByteArray(this.getExtensions().size()));
            serializedSmtCreateOperation.write(BeowulfJUtils.transformLongToByteArray(this.getMaxSupply()));
            for (FutureExtensions extension :
                    this.getExtensions()) {
                serializedSmtCreateOperation.write(extension.toByteArray());
            }
            return serializedSmtCreateOperation.toByteArray();
        } catch (IOException e) {
            throw new BeowulfInvalidTransactionException(
                    "A problem occured while transforming the operation into a byte array.", e);
        }
    }

    @Override
    public void validate(ValidationType validationType) {

    }
}
