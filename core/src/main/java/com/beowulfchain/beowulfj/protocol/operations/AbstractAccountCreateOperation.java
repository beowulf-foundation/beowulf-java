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
import com.beowulfchain.beowulfj.enums.PrivateKeyType;
import com.beowulfchain.beowulfj.enums.ValidationType;
import com.beowulfchain.beowulfj.interfaces.SignatureObject;
import com.beowulfchain.beowulfj.protocol.AccountName;
import com.beowulfchain.beowulfj.protocol.Asset;
import com.beowulfchain.beowulfj.protocol.Authority;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.security.InvalidParameterException;
import java.util.Map;

/**
 * This abstract class contains fields that exist in all Beowulf Operations
 * related to the account creation.
 */
public abstract class AbstractAccountCreateOperation extends AbstractAccountOperation {
    @JsonProperty("fee")
    protected Asset fee;
    @JsonProperty("creator")
    protected AccountName creator;
    @JsonProperty("new_account_name")
    protected AccountName newAccountName;

    /**
     * Create a new Operation object by providing the operation type.
     *
     * @param virtual Define if the operation instance is a virtual
     *                (<code>true</code>) or a market operation
     *                (<code>false</code>).
     */
    protected AbstractAccountCreateOperation(boolean virtual) {
        super(virtual);
    }

    /**
     * Get the fee the {@link #getCreator() creator} has paid to create this new
     * account.
     *
     * @return The fee.
     */
    public Asset getFee() {
        return fee;
    }

    /**
     * Set the fee the {@link #getCreator() creator} will pay to create a new
     * account.
     *
     * @param fee The fee.
     * @throws InvalidParameterException If the <code>fee</code> is null, of symbol type BWF or less
     *                                   than 0.
     */
    public void setFee(Asset fee) {
        this.fee = BeowulfJUtils.setIfNotNull(fee, "The fee can't be null.");
    }

    /**
     * Get the account who created a new account.
     *
     * @return The the user who created a new account.
     */
    public AccountName getCreator() {
        return creator;
    }

    /**
     * Set the user who created a new account. <b>Notice:</b> The private active
     * key of this account needs to be stored in the key storage.
     *
     * @param creator The the user who creates a new account.
     * @throws InvalidParameterException If the <code>creator</code> is null.
     */
    public void setCreator(AccountName creator) {
        this.creator = BeowulfJUtils.setIfNotNull(creator, "The creator can't be null.");
    }

    /**
     * Get the account name of the created account.
     *
     * @return The account name of the user which has been created.
     */
    public AccountName getNewAccountName() {
        return newAccountName;
    }

    /**
     * Set the account name for the new account.
     *
     * @param newAccountName The account name of the user which should be created.
     * @throws InvalidParameterException If the <code>newAccountName</code> is null.
     */
    public void setNewAccountName(AccountName newAccountName) {
        this.newAccountName = BeowulfJUtils.setIfNotNull(newAccountName, "The new account name can't be null.");
    }

    /**
     * Get the owner {@link Authority
     * Authority} of the {@link #getNewAccountName() newAccountName}.
     *
     * @return The owner authority.
     */
    @Override
    public Authority getOwner() {
        return owner;
    }

    /**
     * Set the owner {@link Authority
     * Authority} of the {@link #getNewAccountName() newAccountName}.
     *
     * @param owner The owner authority.
     * @throws InvalidParameterException If the <code>owner</code> is null.
     */
    @Override
    public void setOwner(Authority owner) {
        this.owner = BeowulfJUtils.setIfNotNull(owner, "The owner can't be null.");
    }

    @Override
    public Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getCreator(), PrivateKeyType.OWNER);
    }

    @Override
    public void validate(ValidationType validationType) {
        if (!ValidationType.SKIP_VALIDATION.equals(validationType)) {
            super.validate(validationType);

            if (!ValidationType.SKIP_ASSET_VALIDATION.equals(validationType)) {
                if (!fee.getName().equals(BeowulfJConfig.getInstance().getDollarSymbol().name())) {
                    throw new InvalidParameterException("The fee must be paid in W.");
                } else if (fee.getAmount() < 0) {
                    throw new InvalidParameterException("The fee must be a positive amount.");
                }
            }
        }
    }
}
