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

import com.beowulfchain.beowulfj.configuration.BeowulfJConfig;
import com.beowulfchain.beowulfj.enums.OperationType;
import com.beowulfchain.beowulfj.enums.PrivateKeyType;
import com.beowulfchain.beowulfj.enums.ValidationType;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.interfaces.SignatureObject;
import com.beowulfchain.beowulfj.protocol.AccountName;
import com.beowulfchain.beowulfj.protocol.Asset;
import com.beowulfchain.beowulfj.protocol.PublicKey;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Map;

/**
 * This class represents the Beowulf "supernode_update_operation" object.
 */
public class SupernodeUpdateOperation extends Operation {
    @JsonProperty("owner")
    private AccountName owner;
    @JsonProperty("block_signing_key")
    private PublicKey blockSigningKey;
    @JsonProperty("fee")
    private Asset fee;

    /**
     * Create a new supernode update operation.
     * <p>
     * Users who wish to become a supernode must pay a {@link #getFee() fee}
     * acceptable to the current supernodes to apply for the position and allow
     * voting to begin.
     * <p>
     * If the {@link #getOwner() owner} isn't a supernode they will become a
     * supernode. Supernodes are charged a fee equal to 1 weeks worth of supernode
     * pay which in turn is derived from the current share supply. The fee is
     * only applied if the owner is not already a supernode.
     * <p>
     * If the {@link #getBlockSigningKey() blockSigningKey} is null then the
     * supernode is removed from contention. The network will pick the top 21
     * supernodes for producing blocks.
     *
     * @param owner           The Supernode account name to set (see
     * @param blockSigningKey The public part of the key used to sign a block (see
     *                        {@link #setBlockSigningKey(PublicKey)}).
     * @param fee             The fee to pay for this update (see {@link #setFee(Asset)}).
     */
    @JsonCreator
    public SupernodeUpdateOperation(@JsonProperty("owner") AccountName owner,
                                    @JsonProperty("block_signing_key") PublicKey blockSigningKey,
                                    @JsonProperty("fee") Asset fee) {
        super(false);

        this.setOwner(owner);
        this.setBlockSigningKey(blockSigningKey);
        this.setFee(fee);
    }

    /**
     * Get the account name of the account for that the supernode update operation
     * has been executed.
     *
     * @return The name of the account that this operation has been executed
     * for.
     */
    public AccountName getOwner() {
        return owner;
    }

    /**
     * Set the account name of the account for that the supernode update operation
     * should be executed. <b>Notice:</b> The private active key of this account
     * needs to be stored in the key storage.
     *
     * @param owner The name of the account that this operation should be executed
     *              for.
     * @throws InvalidParameterException If the owner is null.
     */
    public void setOwner(AccountName owner) {
        this.owner = BeowulfJUtils.setIfNotNull(owner, "The owner can't be null.");
    }

    /**
     * Get the public key of the key pair that will be used to sign blocks.
     *
     * @return The public key of the key pair that will be used to sign blocks.
     */
    public PublicKey getBlockSigningKey() {
        return blockSigningKey;
    }

    /**
     * Set the public key of the key pair that will be used to sign blocks.
     *
     * @param blockSigningKey The public key of the key pair that will be used to sign
     *                        blocks.
     * @throws InvalidParameterException If the blockSigningKey is null.
     */
    public void setBlockSigningKey(PublicKey blockSigningKey) {
        this.blockSigningKey = BeowulfJUtils.setIfNotNull(blockSigningKey, "You need to provide a block signing key.");
    }

    /**
     * Get the fee that has been paid for this supernode update.
     *
     * @return The fee that has been paid for this supernode update.
     */
    public Asset getFee() {
        return fee;
    }

    /**
     * Set the fee that should be paid for this supernode update. The
     * <code>fee</code> paid to register a new supernode, should be 10x current
     * block production pay.
     *
     * @param fee The fee that should be paid for this supernode update.
     * @throws InvalidParameterException If the provided asset object is null.
     */
    public void setFee(Asset fee) {
        this.fee = BeowulfJUtils.setIfNotNull(fee, "The fee can't be null.");
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedSupernodeUpdateOperation = new ByteArrayOutputStream()) {
            serializedSupernodeUpdateOperation.write(
                    BeowulfJUtils.transformIntToVarIntByteArray(OperationType.SUPERNODE_UPDATE_OPERATION.getOrderId()));
            serializedSupernodeUpdateOperation.write(this.getOwner().toByteArray());
            serializedSupernodeUpdateOperation.write(this.getBlockSigningKey().toByteArray());
            serializedSupernodeUpdateOperation.write(this.getFee().toByteArray());

            return serializedSupernodeUpdateOperation.toByteArray();
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
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getOwner(), PrivateKeyType.OWNER);
    }

    @Override
    public void validate(ValidationType validationType) {
        if ((!ValidationType.SKIP_ASSET_VALIDATION.equals(validationType)
                && !ValidationType.SKIP_VALIDATION.equals(validationType))
                && (this.getFee().getAmount() < 0
                || !BeowulfJConfig.getInstance().getTokenSymbol().equals(this.getFee().getName()))) {
            throw new InvalidParameterException("The fee needs to be a positive amount of BWF.");
        }
    }
}
