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
 * This class represents the Beowulf "transfer_operation" object.
 */
public class TransferOperation extends AbstractTransferOperation {
    @JsonProperty("memo")
    private String memo;

    /**
     * Create a new transfer operation. Use this operation to transfer an asset
     * from one account to another.
     *
     * @param from   The account to transfer the vestings from (see
     *               {@link #setFrom(AccountName)}).
     * @param to     The account that will receive the transfered vestings (see
     *               {@link #setTo(AccountName)}).
     * @param amount The amount of vests to transfer (see
     *               {@link #setAmount(Asset)}).
     * @param memo   An additional message added to the operation (see
     *               {@link #setMemo(String)}).
     * @throws InvalidParameterException If one of the arguments does not fulfill the requirements.
     */
    @JsonCreator
    public TransferOperation(@JsonProperty("from") AccountName from, @JsonProperty("to") AccountName to,
                             @JsonProperty("amount") Asset amount, @JsonProperty("fee") Asset fee, @JsonProperty("memo") String memo) {
        super(false);

        this.setFrom(from);
        this.setTo(to);
        this.setAmount(amount);
        this.setFee(fee);
        this.setMemo(memo);
    }

    /**
     * Set the <code>amount</code> of that will be send.
     *
     * @param amount The <code>amount</code> of that will be send.
     * @throws InvalidParameterException If the <code>amount</code> is null, of symbol type M or
     *                                   less than 1.
     */
    @Override
    public void setAmount(Asset amount) {
        this.amount = BeowulfJUtils.setIfNotNull(amount, "The amount can't be null.");
    }

    /**
     * Get the message added to this operation.
     *
     * @return The message added to this operation.
     */
    public String getMemo() {
        return memo;
    }

    /**
     * Add an additional message to this operation.
     *
     * @param memo The message added to this operation.
     * @throws InvalidParameterException If the <code>memo</code> has more than 2048 characters.
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedTransferOperation = new ByteArrayOutputStream()) {
            serializedTransferOperation
                    .write(BeowulfJUtils.transformIntToVarIntByteArray(OperationType.TRANSFER_OPERATION.getOrderId()));
            serializedTransferOperation.write(this.getFrom().toByteArray());
            serializedTransferOperation.write(this.getTo().toByteArray());
            serializedTransferOperation.write(this.getAmount().toByteArray());
            serializedTransferOperation.write(this.getFee().toByteArray());
            serializedTransferOperation.write(BeowulfJUtils.transformStringToVarIntByteArray(this.getMemo()));

            return serializedTransferOperation.toByteArray();
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
        if (!ValidationType.SKIP_VALIDATION.equals(validationType)) {
            super.validate(validationType);

            if (!ValidationType.SKIP_ASSET_VALIDATION.equals(validationType)) {
                if (amount.getName().equals(BeowulfJConfig.getInstance().getVestsSymbol())) {
                    throw new InvalidParameterException("Transfering Beowulf Power (M) is not allowed.");
                } else if (amount.getAmount() <= 0) {
                    throw new InvalidParameterException("Must transfer a nonzero amount.");
                }
            }

            if (memo.length() > 2048) {
                throw new InvalidParameterException("The memo is too long. Only 2048 characters are allowed.");
            }
        }
    }
}
