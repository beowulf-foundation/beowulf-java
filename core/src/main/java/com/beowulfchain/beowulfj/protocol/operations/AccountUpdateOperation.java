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
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.interfaces.SignatureObject;
import com.beowulfchain.beowulfj.protocol.AccountName;
import com.beowulfchain.beowulfj.protocol.Asset;
import com.beowulfchain.beowulfj.protocol.Authority;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Map;

/**
 * This class represents the Beowulf "account_update_operation" object.
 */
public class AccountUpdateOperation extends AbstractAccountOperation {
    @JsonProperty("account")
    private AccountName account;
    @JsonProperty("fee")
    private Asset fee;

    /**
     * Create a new create account update operation. Use this operation to
     * update the keys of an existing account.
     *
     * @param account      The account to update (see {@link #setAccount(AccountName)}).
     * @param owner        The new owner authority or null if the owner authority should
     *                     not be updated (see {@link #setOwner(Authority)}).
     * @param jsonMetadata Set the additional information for the <code>account</code>
     *                     (see {@link #setJsonMetadata(String)}).
     * @throws InvalidParameterException If one of the arguments does not fulfill the requirements.
     */
    @JsonCreator
    public AccountUpdateOperation(@JsonProperty("account") AccountName account,
                                  @JsonProperty("owner") Authority owner,
                                  @JsonProperty("json_metadata") String jsonMetadata,
                                  @JsonProperty("fee") Asset fee) {
        super(false);

        this.setAccount(account);
        this.setOwner(owner);
        this.setJsonMetadata(jsonMetadata);
        this.setFee(fee);

    }

    /**
     * Get the owner {@link Authority
     * Authority} of the {@link #getAccount() account}.
     *
     * @return The owner authority.
     */
    @Override
    public Authority getOwner() {
        return owner;
    }

    /**
     * Set the new owner {@link Authority
     * Authority} of the {@link #getAccount() account}.
     *
     * @param owner The owner authority.
     */
    @Override
    public void setOwner(Authority owner) {
        this.owner = owner;
    }

    /**
     * Get the account name of the account that has been changed.
     *
     * @return The account name of the changed account.
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * Set the account name of the account that has been changed. <b>Notice:</b>
     * The private owner key of this account needs to be stored in the key
     * storage.
     *
     * @param account The account name of the account to change.
     * @throws InvalidParameterException If the <code>account</code> is null.
     */
    public void setAccount(AccountName account) {
        this.account = BeowulfJUtils.setIfNotNull(account, "The account can't be null.");
    }

    public Asset getFee() {
        return fee;
    }

    public void setFee(Asset fee) {
        this.fee = fee;
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedAccountUpdateOperation = new ByteArrayOutputStream()) {
            serializedAccountUpdateOperation.write(
                    BeowulfJUtils.transformIntToVarIntByteArray(OperationType.ACCOUNT_UPDATE_OPERATION.getOrderId()));
            serializedAccountUpdateOperation.write(this.getAccount().toByteArray());

            // Handle optional values.
            if (this.getOwner() != null) {
                serializedAccountUpdateOperation.write(this.getOwner().toByteArray());
            }

            serializedAccountUpdateOperation
                    .write(BeowulfJUtils.transformStringToVarIntByteArray(this.getJsonMetadata()));
            serializedAccountUpdateOperation.write(this.getFee().toByteArray());

            return serializedAccountUpdateOperation.toByteArray();
        } catch (IOException e) {
            throw new BeowulfInvalidTransactionException(
                    "A problem occured while transforming the operation into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase) {
        if (this.getOwner() != null) {
            return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getAccount(), PrivateKeyType.OWNER);
        } else {
            return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getAccount(), PrivateKeyType.OWNER);
        }
    }
}
