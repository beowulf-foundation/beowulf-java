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
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Map;

/**
 * This class represents the Beowulf "withdraw_vesting_operation" object.
 */
public class WithdrawVestingOperation extends Operation {
    @JsonProperty("account")
    private AccountName account;
    @JsonProperty("vesting_shares")
    private Asset vestingShares;
    @JsonProperty("fee")
    private Asset fee;

    /**
     * Create a new withdraw vesting operation.
     * <p>
     * At any given point in time an account can be withdrawing from their
     * vesting shares. A user may change the number of shares they wish to cash
     * out at any time between 0 and their total vesting stake.
     * <p>
     * After applying this operation, {@link #getVestingShares() vestingShares}
     * will be withdrawn at a rate of {@link #getVestingShares()
     * vestingShares}/104 per week for two years starting one week after this
     * operation is included in the blockchain.
     * <p>
     * This operation is not valid if the user has no vesting shares.
     *
     * @param account       Set the account that wants to start powering down (see
     *                      {@link #setAccount(AccountName)}).
     * @param vestingShares Set the amount of M to power down (see
     *                      {@link #setVestingShares(Asset)}).
     * @param fee           The fee of transaction.
     */
    @JsonCreator
    public WithdrawVestingOperation(@JsonProperty("account") AccountName account,
                                    @JsonProperty("vesting_shares") Asset vestingShares,
                                    @JsonProperty("fee") Asset fee) {
        super(false);
        this.setFee(fee);
        this.setAccount(account);
        this.setVestingShares(vestingShares);
    }

    /**
     * Get the account name of the account that the withdraw vesting operation
     * has been executed for.
     *
     * @return The account name for which the withdraw vesting operation has
     * been executed for.
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * Set the account name of the account that the withdraw vesting operation
     * should be executed for. <b>Notice:</b> The private active key of this
     * account needs to be stored in the key storage.
     *
     * @param account The account name for which the withdraw vesting operation
     *                should be executed for.
     * @throws InvalidParameterException If no account name has been provided.
     */
    public void setAccount(AccountName account) {
        this.account = BeowulfJUtils.setIfNotNull(account, "An account name needs to be provided.");
    }

    /**
     * Get the amount that has been requested for withdrawing.
     *
     * @return the vestingShares The amount that has been requested for
     * withdrawing.
     */
    public Asset getVestingShares() {
        return vestingShares;
    }

    /**
     * Set the amount that should be requested for withdrawing.
     *
     * @param vestingShares The amount that should be requested for withdrawing.
     * @throws InvalidParameterException If the asset type is null.
     */
    public void setVestingShares(Asset vestingShares) {
        this.vestingShares = BeowulfJUtils.setIfNotNull(vestingShares, "The vesting shares can't be null.");
    }

    public Asset getFee() {
        return fee;
    }

    public void setFee(Asset fee) {
        this.fee = fee;
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedWithdrawVestingOperation = new ByteArrayOutputStream()) {
            serializedWithdrawVestingOperation.write(
                    BeowulfJUtils.transformIntToVarIntByteArray(OperationType.WITHDRAW_VESTING_OPERATION.getOrderId()));
            serializedWithdrawVestingOperation.write(this.getAccount().toByteArray());
            serializedWithdrawVestingOperation.write(this.getVestingShares().toByteArray());
            serializedWithdrawVestingOperation.write(this.getFee().toByteArray());

            return serializedWithdrawVestingOperation.toByteArray();
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
        if ((!ValidationType.SKIP_ASSET_VALIDATION.equals(validationType)
                && !ValidationType.SKIP_VALIDATION.equals(validationType))
                && (!BeowulfJConfig.getInstance().getVestsSymbol().getName().equals(this.getVestingShares().getName()))) {
            throw new InvalidParameterException("The vesting shares needs to be provided in " +
                    BeowulfJConfig.getInstance().getVestsSymbol().getName() +
                    ".");
        }
    }
}
