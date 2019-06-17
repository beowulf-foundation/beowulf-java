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

import com.beowulfchain.beowulfj.enums.PrivateKeyType;
import com.beowulfchain.beowulfj.interfaces.ByteTransformable;
import com.beowulfchain.beowulfj.interfaces.SignatureObject;
import com.beowulfchain.beowulfj.interfaces.Validatable;
import com.beowulfchain.beowulfj.protocol.operations.virtual.FillVestingWithdrawOperation;
import com.beowulfchain.beowulfj.protocol.operations.virtual.HardforkOperation;
import com.beowulfchain.beowulfj.protocol.operations.virtual.ProducerRewardOperation;
import com.beowulfchain.beowulfj.protocol.operations.virtual.ShutdownSupernodeOperation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is a wrapper for the different kinds of operations that an user
 * can perform.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_ARRAY)
@JsonSubTypes({
        @Type(value = TransferOperation.class, name = "transfer"),
        @Type(value = TransferToVestingOperation.class, name = "transfer_to_vesting"),
        @Type(value = WithdrawVestingOperation.class, name = "withdraw_vesting"),
        @Type(value = AccountCreateOperation.class, name = "account_create"),
        @Type(value = AccountUpdateOperation.class, name = "account_update"),
        @Type(value = SupernodeUpdateOperation.class, name = "supernode_update"),
        @Type(value = AccountSupernodeVoteOperation.class, name = "account_supernode_vote"),
        @Type(value = SmtCreateOperation.class, name = "smt_create"),
        @Type(value = FillVestingWithdrawOperation.class, name = "fill_vesting_withdraw"),
        @Type(value = ShutdownSupernodeOperation.class, name = "shutdown_supernode"),
        @Type(value = HardforkOperation.class, name = "hardfork"),
        @Type(value = ProducerRewardOperation.class, name = "producer_reward")
})
public abstract class Operation extends BaseOperation implements ByteTransformable, Validatable {
    /**
     * This field is used to store the operation type.
     */
    @JsonIgnore
    private boolean virtual;

    /**
     * Create a new Operation object by providing the operation type.
     *
     * @param virtual Define if the operation instance is a virtual
     *                (<code>true</code>) or a market operation
     *                (<code>false</code>).
     */
    protected Operation(boolean virtual) {
        this.virtual = virtual;
    }

    /**
     * Returns {@code true} if, and only if, the operation is a virtual
     * operation.
     *
     * @return {@code true} if the operation is a virtual operation, otherwise
     * {@code false}
     */
    public boolean isVirtual() {
        return virtual;
    }

    /**
     * Add the authorities which are required to sign this operation to an
     * existing map.
     *
     * @param requiredAuthoritiesBase A map to which the required authorities of this operation
     *                                should be added to.
     * @return A map of required authorities.
     */
    public abstract Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase);

    /**
     * Use this helper method to merge a single <code>accountName</code> into
     * the <code>requiredAuthoritiesBase</code>.
     *
     * @param requiredAuthoritiesBase A map to which the required authorities of this operation
     *                                should be added to.
     * @param signatureObject         The signature object (e.g. an account names) to merge into the
     *                                list.
     * @param privateKeyType          The required key type.
     * @return The merged set of signature objects and required private key
     * types.
     */
    protected Map<SignatureObject, PrivateKeyType> mergeRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase, SignatureObject signatureObject,
            PrivateKeyType privateKeyType) {
        Map<SignatureObject, PrivateKeyType> requiredAuthorities = requiredAuthoritiesBase;
        if (requiredAuthorities == null) {
            requiredAuthorities = new HashMap<>();
        } else if (requiredAuthorities.containsKey(signatureObject)
                && requiredAuthorities.get(signatureObject) != null) {
            // Only the most powerful key is needed to sign the transaction, so
            // if there is already the same key type or a higher key type there
            // is no need to change it.
            if (requiredAuthorities.get(signatureObject).ordinal() > privateKeyType.ordinal()) {
                requiredAuthorities.put(signatureObject, privateKeyType);
            }
        } else {
            requiredAuthorities.put(signatureObject, privateKeyType);
        }

        return requiredAuthorities;
    }

    /**
     * Use this helper method to merge a a list of account names into the
     * <code>requiredAuthoritiesBase</code>.
     *
     * @param requiredAuthoritiesBase A map to which the required authorities of this operation
     *                                should be added to.
     * @param signatureObjects        The signature objects (e.g. a list of account names) to merge
     *                                into the list.
     * @param privateKeyType          The required key type.
     * @return The merged set of signature objects and required private key
     * types.
     */
    protected Map<SignatureObject, PrivateKeyType> mergeRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase,
            List<? extends SignatureObject> signatureObjects, PrivateKeyType privateKeyType) {
        Map<SignatureObject, PrivateKeyType> requiredAuthorities = requiredAuthoritiesBase;

        for (SignatureObject signatureObject : signatureObjects) {
            requiredAuthorities = mergeRequiredAuthorities(requiredAuthorities, signatureObject, privateKeyType);
        }

        return requiredAuthorities;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
