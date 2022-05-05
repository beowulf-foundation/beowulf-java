/*
 *     This file is part of BeowulfJ (formerly known as 'Beowulf-Java-Api-Wrapper')
 *
 *     BeowulfJ is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     BeowulfJ is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.beowulfchain.beowulfj.protocol.operations;

import com.beowulfchain.beowulfj.enums.OperationType;
import com.beowulfchain.beowulfj.enums.PrivateKeyType;
import com.beowulfchain.beowulfj.enums.ValidationType;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.interfaces.SignatureObject;
import com.beowulfchain.beowulfj.protocol.AccountName;
import com.beowulfchain.beowulfj.protocol.Asset;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

/**
 * This class contains fields that exist in all Beowulf Operations
 */
public class SmartContractOperation extends Operation {
    @JsonProperty("require_owners")
    protected List<AccountName> require_owners;
    @JsonProperty("scid")
    protected String scid;
    @JsonProperty("sc_operation")
    protected String sc_operation;
    @JsonProperty("fee")
    protected Asset fee;

    /**
     * Create a new Operation object by providing the operation type.
     *
     * @param virtual Define if the operation instance is a virtual
     *                (<code>true</code>) or a market operation
     *                (<code>false</code>).
     */
    protected SmartContractOperation(boolean virtual) {
        super(virtual);
    }

    @Override
    public Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getRequire_owners(), PrivateKeyType.OWNER);
    }

    /**
     * Get the require_owners {@link AccountName
     * AccountName}.
     *
     * @return The require_owners authority.
     */
    public List<AccountName> getRequire_owners() {
        return require_owners;
    }

    /**
     * Set the require_owners {@link AccountName
     * AccountName}.
     *
     * @param require_owners The require_owners authority.
     */
    public void setRequire_owners(List<AccountName> require_owners) {
        this.require_owners = require_owners;
    }

    /**
     * Get the sc_operation metadata that has been added to this operation.
     *
     * @return The sc_operation metadata that has been added to this operation.
     */
    public String getSc_operation() {
        return sc_operation;
    }

    /**
     * Add sc_operation to this operation.
     *
     * @param sc_operation The contract sidechain operation.
     */
    public void setSc_operation(String sc_operation) {
        this.sc_operation = sc_operation;
    }

    public String getScid() {
        return scid;
    }

    public void setScid(String scid) {
        this.scid = scid;
    }

    public Asset getFee() {
        return fee;
    }

    public void setFee(Asset fee) {
        this.fee = fee;
    }

    @Override
    public void validate(ValidationType validationType) {
        if (!ValidationType.SKIP_VALIDATION.equals(validationType)
                && (!sc_operation.isEmpty() && !BeowulfJUtils.verifyJsonString(sc_operation))) {
            throw new InvalidParameterException("The given sc_operation metadata is no valid JSON");
        }
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedSmartContractOperation = new ByteArrayOutputStream()) {
            serializedSmartContractOperation.write(
                    BeowulfJUtils.transformIntToVarIntByteArray(OperationType.SMART_CONTRACT_OPERATION.getOrderId()));

            serializedSmartContractOperation.write(BeowulfJUtils.transformIntToVarIntByteArray(this.getRequire_owners().size()));
            for (AccountName accountName :
                    this.getRequire_owners()) {
                serializedSmartContractOperation.write(accountName.toByteArray());
            }
            serializedSmartContractOperation.write(BeowulfJUtils.transformStringToVarIntByteArray(this.getScid()));
            serializedSmartContractOperation.write(BeowulfJUtils.transformStringToVarIntByteArray(this.getSc_operation()));
            serializedSmartContractOperation.write(this.getFee().toByteArray());
            return serializedSmartContractOperation.toByteArray();
        } catch (IOException e) {
            throw new BeowulfInvalidTransactionException(
                    "A problem occured while transforming the operation into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
