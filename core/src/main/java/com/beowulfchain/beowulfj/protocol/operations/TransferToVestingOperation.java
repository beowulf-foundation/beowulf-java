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
import com.beowulfchain.beowulfj.enums.ValidationType;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.protocol.AccountName;
import com.beowulfchain.beowulfj.protocol.Asset;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;

/**
 * This class represents the Beowulf "transfer_to_vesting_operation" object.
 */
public class TransferToVestingOperation extends AbstractTransferOperation {
    /**
     * Create a new transfer to vesting operation to transfer Beowulf Power to
     * other users.
     * <p>
     * This operation converts BWF into VFS (Vesting Fund Shares) at the
     * current exchange rate. With this operation it is possible to give another
     * account vesting shares so that faucets can pre-fund new accounts with
     * vesting shares.
     *
     * @param from   The account to transfer the vestings from (see
     *               {@link #setFrom(AccountName)}).
     * @param to     The account that will receive the transfered vestings (see
     *               {@link #setTo(AccountName)}).
     * @param amount The amount of vests to transfer (see
     *               {@link #setAmount(Asset)}).
     * @throws InvalidParameterException If one of the arguments does not fulfill the requirements.
     */
    @JsonCreator
    public TransferToVestingOperation(@JsonProperty("from") AccountName from, @JsonProperty("to") AccountName to,
                                      @JsonProperty("amount") Asset amount, @JsonProperty("fee") Asset fee) {
        super(false);
        this.setFrom(from);
        this.setTo(to);
        this.setAmount(amount);
        this.setFee(fee);
    }

    /**
     * Like
     * {@link #TransferToVestingOperation(AccountName, AccountName, Asset, Asset)}, but
     * will transform the <code>asset</code> from the <code>from</code> account
     * to the <code>from</code> account.
     *
     * @param from   The account to transfer the vestings from (see
     *               {@link #setFrom(AccountName)}).
     * @param amount The amount of vests to transfer (see
     *               {@link #setAmount(Asset)}).
     * @throws InvalidParameterException If one of the arguments does not fulfill the requirements.
     */
    public TransferToVestingOperation(AccountName from, Asset amount, Asset fee) {
        this(from, from, amount, fee);
    }

    /**
     * Set the account name of the user who will received the
     * <code>amount</code>.
     *
     * @param to The account name of the user who will received the
     *           <code>amount</code>.
     */
    @Override
    public void setTo(AccountName to) {
        if (to == null) {
            this.to = new AccountName();
        } else {
            this.to = to;
        }
    }

    /**
     * Set the <code>amount</code> of that will be send.
     *
     * @param amount The <code>amount</code> of that will be send.
     * @throws InvalidParameterException If the <code>amount</code> is null, not of symbol type BWF
     *                                   or less than 1.
     */
    @Override
    public void setAmount(Asset amount) {
        this.amount = BeowulfJUtils.setIfNotNull(amount, "The amount can't be null.");
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedTransferToVestingOperation = new ByteArrayOutputStream()) {
            serializedTransferToVestingOperation.write(BeowulfJUtils
                    .transformIntToVarIntByteArray(OperationType.TRANSFER_TO_VESTING_OPERATION.getOrderId()));
            serializedTransferToVestingOperation.write(this.getFrom().toByteArray());
            serializedTransferToVestingOperation.write(this.getTo().toByteArray());
            serializedTransferToVestingOperation.write(this.getAmount().toByteArray());
            serializedTransferToVestingOperation.write(this.getFee().toByteArray());

            return serializedTransferToVestingOperation.toByteArray();
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
    public void validate(ValidationType validationType) {
        if (!ValidationType.SKIP_VALIDATION.equals(validationType)
                && !ValidationType.SKIP_ASSET_VALIDATION.equals(validationType)
                && (!amount.getName().equals(BeowulfJConfig.getInstance().getTokenSymbol()))) {
            throw new InvalidParameterException("The amount must be of type BWF.");
        }
    }
}
