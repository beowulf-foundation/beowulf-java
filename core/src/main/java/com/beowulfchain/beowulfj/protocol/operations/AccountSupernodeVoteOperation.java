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
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Map;

/**
 * This class represents the Beowulf "account_supernode_vote_operation" object.
 */
public class AccountSupernodeVoteOperation extends Operation {
    @JsonProperty("account")
    private AccountName account;
    @JsonProperty("supernode")
    private AccountName supernode;
    @JsonProperty("approve")
    private boolean approve;
    @JsonProperty("fee")
    private Asset fee;

    /**
     * Create a new account supernode vote operation.
     * <p>
     * All accounts with a VFS can vote for or against any supernode.
     * <p>
     * If a proxy is specified then all existing votes are removed.
     *
     * @param account   Set the <code>account</code> that votes for a
     *                  <code>supernode</code> (see {@link #getAccount()}).
     * @param supernode Set the <code>supernode</code> to vote for (see
     *                  {@link #setSupernode(AccountName)}).
     * @param approve   Define if the vote is be approved or not (see
     *                  {@link #setApprove(boolean)}).
     * @throws InvalidParameterException If one of the parameters does not fulfill the requirements.
     */
    @JsonCreator
    public AccountSupernodeVoteOperation(@JsonProperty("account") AccountName account,
                                         @JsonProperty("supernode") AccountName supernode,
                                         @JsonProperty("approve") boolean approve,
                                         @JsonProperty("fee") Asset fee) {
        super(false);

        this.setAccount(account);
        this.setSupernode(supernode);
        this.setApprove(approve);
        this.setFee(fee);
    }

    /**
     * Like
     * {@link #AccountSupernodeVoteOperation(AccountName, AccountName, boolean, Asset)},
     * but the <code>approve</code> parameter is automatically set to true.
     *
     * @param account   Set the <code>account</code> that votes for a
     *                  <code>supernode</code> (see {@link #getAccount()}).
     * @param supernode Set the <code>supernode</code> to vote for (see
     *                  {@link #setSupernode(AccountName)}).
     * @throws InvalidParameterException If one of the parameters does not fulfill the requirements.
     */
    public AccountSupernodeVoteOperation(AccountName account, AccountName supernode, Asset fee) {
        // Set default values:
        this(account, supernode, true, fee);
    }

    /**
     * Get the account name that has performed the vote.
     *
     * @return The account name that has performed the vote.
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * Set the account name that should perform the vote. <b>Notice:</b> The
     * private active key of this account needs to be stored in the key storage.
     *
     * @param account The account name that should perform the vote.
     * @throws InvalidParameterException If the <code>account</code> account is null
     */
    public void setAccount(AccountName account) {
        this.account = BeowulfJUtils.setIfNotNull(account, "The supernode acccount can't be null.");
    }

    /**
     * Get the supernode that has been voted for.
     *
     * @return The supernode that has been voted for.
     */
    public AccountName getSupernode() {
        return supernode;
    }

    /**
     * Set the supernode that should be voted for.
     *
     * @param supernode The supernode that should be voted for.
     * @throws InvalidParameterException If the <code>supernode</code> account is null
     */
    public void setSupernode(AccountName supernode) {
        this.supernode = BeowulfJUtils.setIfNotNull(supernode, "The supernode acccount can't be null.");
    }

    /**
     * Get the information if this vote has been approved or not.
     *
     * @return The information if this vote has been approved or not.
     */
    public boolean getApprove() {
        return approve;
    }

    /**
     * Define if this vote is approved or not.
     *
     * @param approve Define if this vote is approved or not.
     */
    public void setApprove(boolean approve) {
        this.approve = approve;
    }

    public Asset getFee() {
        return fee;
    }

    public void setFee(Asset fee) {
        this.fee = fee;
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedAccountSupernodeVoteOperation = new ByteArrayOutputStream()) {
            serializedAccountSupernodeVoteOperation.write(BeowulfJUtils
                    .transformIntToVarIntByteArray(OperationType.ACCOUNT_SUPERNODE_VOTE_OPERATION.getOrderId()));
            serializedAccountSupernodeVoteOperation.write(this.getAccount().toByteArray());
            serializedAccountSupernodeVoteOperation.write(this.getSupernode().toByteArray());
            serializedAccountSupernodeVoteOperation.write(BeowulfJUtils.transformBooleanToByteArray(this.getApprove()));
            serializedAccountSupernodeVoteOperation.write(this.getFee().toByteArray());

            return serializedAccountSupernodeVoteOperation.toByteArray();
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
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getAccount(), PrivateKeyType.OWNER);
    }

    @Override
    public void validate(ValidationType validationType) {
        return;
    }
}
