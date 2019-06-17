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
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
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

/**
 * This class represents the Beowulf "account_create_operation" object.
 */
public class AccountCreateOperation extends AbstractAccountCreateOperation {
    /**
     * Create a new create account operation. Use this operation to create a new
     * account.
     *
     * @param creator        Set the account that will pay the <code>fee</code> to create
     *                       the <code>newAccountName</code> (see
     *                       {@link #setCreator(AccountName)}).
     * @param fee            Set the fee the <code>creator</code> will pay (see
     *                       {@link #setFee(Asset)}).
     * @param newAccountName Set the new account name (see
     *                       {@link #setNewAccountName(AccountName)}).
     * @param owner          The new owner authority or null if the owner authority should
     *                       not be updated (see {@link #setOwner(Authority)}).
     * @param jsonMetadata   Set the additional information for the <code>account</code>
     *                       (see {@link #setJsonMetadata(String)}).
     * @throws InvalidParameterException If one of the arguments does not fulfill the requirements.
     */
    @JsonCreator
    public AccountCreateOperation(@JsonProperty("creator") AccountName creator, @JsonProperty("fee") Asset fee,
                                  @JsonProperty("new_account_name") AccountName newAccountName, @JsonProperty("owner") Authority owner,
                                  @JsonProperty("json_metadata") String jsonMetadata) {
        super(false);

        this.setCreator(creator);
        this.setFee(fee);
        this.setNewAccountName(newAccountName);
        this.setOwner(owner);
        this.setJsonMetadata(jsonMetadata);
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedAccountCreateOperation = new ByteArrayOutputStream()) {
            serializedAccountCreateOperation.write(
                    BeowulfJUtils.transformIntToVarIntByteArray(OperationType.ACCOUNT_CREATE_OPERATION.getOrderId()));
            serializedAccountCreateOperation.write(this.getFee().toByteArray());
            serializedAccountCreateOperation.write(this.getCreator().toByteArray());
            serializedAccountCreateOperation.write(this.getNewAccountName().toByteArray());
            serializedAccountCreateOperation.write(this.getOwner().toByteArray());
            serializedAccountCreateOperation
                    .write(BeowulfJUtils.transformStringToVarIntByteArray(this.getJsonMetadata()));

            return serializedAccountCreateOperation.toByteArray();
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
