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
package com.beowulfchain.beowulfj.base.models;

import com.beowulfchain.beowulfj.BeowulfJ;
import com.beowulfchain.beowulfj.configuration.BeowulfJConfig;
import com.beowulfchain.beowulfj.enums.PrivateKeyType;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.fc.TimePointSec;
import com.beowulfchain.beowulfj.interfaces.SignatureObject;
import com.beowulfchain.beowulfj.protocol.operations.Operation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.VisibleForTesting;
import eu.bittrade.crypto.core.CryptoUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;
import org.joou.UShort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Transaction implements Serializable {
    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = -3834759301983200246L;
    private static final Logger LOGGER = LoggerFactory.getLogger(Transaction.class);

    /**
     * The ref_block_num indicates a particular block in the past by referring
     * to the block number which has this number as the last two bytes.
     */
    @JsonProperty("ref_block_num")
    protected UShort refBlockNum;
    /**
     * The ref_block_prefix on the other hand is obtain from the block id of
     * that particular reference block.
     */
    @JsonProperty("ref_block_prefix")
    protected UInteger refBlockPrefix;
    @JsonProperty("expiration")
    protected transient TimePointSec expirationDate;
    @JsonProperty("operations")
    protected transient List<Operation> operations;
    // Original type is "extension_type" which is an array of "future_extions".
    @JsonProperty("extensions")
    protected transient List<FutureExtensions> extensions;
    @JsonProperty("created_time")
    protected Long createdTime;

    /**
     * Create a new transaction object.
     *
     * @param refBlockNum    The reference block number (see
     *                       {@link #setRefBlockNum(UShort)}).
     * @param refBlockPrefix The reference block index (see
     *                       {@link #setRefBlockPrefix(UInteger)}).
     * @param expirationDate Define until when the transaction has to be processed (see
     *                       {@link #setExpirationDate(TimePointSec)}).
     * @param operations     A list of operations to process within this Transaction (see
     *                       {@link #setOperations(List)}).
     * @param extensions     Extensions are currently not supported and will be ignored
     *                       (see {@link #setExtensions(List)}).
     * @param createdTime    Timestamp create transaction.
     */
    @JsonCreator
    public Transaction(@JsonProperty("ref_block_num") UShort refBlockNum,
                       @JsonProperty("ref_block_prefix") UInteger refBlockPrefix,
                       @JsonProperty("expiration") TimePointSec expirationDate,
                       @JsonProperty("operations") List<Operation> operations,
                       @JsonProperty("extensions") List<FutureExtensions> extensions,
                       @JsonProperty("created_time") Long createdTime) {
        this.setRefBlockNum(refBlockNum);
        this.setRefBlockPrefix(refBlockPrefix);
        this.setExpirationDate(expirationDate);
        this.setOperations(operations);
        this.setExtensions(extensions);
        this.setCreatedTime(createdTime);

    }

    /**
     * Like {@link #Transaction(BlockId, List, List)} ,
     * but allows you to provide a
     * {@link BlockId} object as the
     * reference block and will also set the <code>expirationDate</code> to the
     * latest possible time.
     *
     * @param blockId    The block reference (see {@link #setRefBlockNum(UShort)} and
     *                   {@link #setRefBlockPrefix(UInteger)}).
     * @param operations A list of operations to process within this Transaction (see
     *                   {@link #setOperations(List)}).
     * @param extensions Extensions are currently not supported and will be ignored
     *                   (see {@link #setExtensions(List)}).
     */
    public Transaction(BlockId blockId, List<Operation> operations, List<FutureExtensions> extensions) {
        this.setRefBlockNum(UShort.valueOf(blockId.getNumberFromHash() & 0xffff));
        this.setRefBlockPrefix(blockId.getHashValue());
        this.setExpirationDate(new TimePointSec(
                System.currentTimeMillis() + BeowulfJConfig.getInstance().getMaximumExpirationDateOffset() - 60000L));
        this.setOperations(operations);
        this.setExtensions(extensions);
        this.setCreatedTime(null);
    }

    /**
     * <b>This method is only used by JUnit-Tests</b>
     * <p>
     * Create a new signed transaction object.
     */
    @VisibleForTesting
    protected Transaction() {
    }

    /**
     * Get the list of configured extensions.
     *
     * @return All extensions.
     */
    public List<FutureExtensions> getExtensions() {
        if (extensions == null) {
            extensions = new ArrayList<>();
        }
        return extensions;
    }

    /**
     * Extensions are currently not supported and will be ignored.
     *
     * @param extensions Define a list of extensions.
     */
    public void setExtensions(List<FutureExtensions> extensions) {
        this.extensions = extensions;
    }

    /**
     * Get all Operations that have been added to this transaction.
     *
     * @return All operations.
     */
    public List<Operation> getOperations() {
        return operations;
    }

    /**
     * Define a list of operations that should be send with this transaction.
     *
     * @param operations A list of operations.
     * @throws InvalidParameterException If the given object does not contain at least one Operation.
     */
    public void setOperations(List<Operation> operations) {
        if (operations == null || operations.isEmpty()) {
            throw new InvalidParameterException("At least one Operation is required.");
        }

        this.operations = operations;
    }

    /**
     * Get the ref block number in its int representation.
     * <p>
     * The ref_block_num indicates a particular block in the past by referring
     * to the block number which has this number as the last two bytes.
     *
     * @return The ref block number.
     */
    public UShort getRefBlockNum() {
        return refBlockNum;
    }

    /**
     * Set the ref block number by providing its int representation.
     * <p>
     * The ref_block_num indicates a particular block in the past by referring
     * to the block number which has this number as the last two bytes.
     *
     * @param refBlockNum The ref block number as int.
     */
    public void setRefBlockNum(UShort refBlockNum) {
        this.refBlockNum = refBlockNum;
    }

    /**
     * Get the ref block prefix in its long representation.
     * <p>
     * The ref_block_prefix on the other hand is obtain from the block id of
     * that particular reference block.
     *
     * @return The ref block prefix.
     */
    public UInteger getRefBlockPrefix() {
        return refBlockPrefix;
    }

    /**
     * Set the ref block prefix by providing its long representation. If you
     * only have the String representation use {@link #setRefBlockPrefix(String)
     * setRefBlockPrefix(String)}.
     *
     * @param refBlockPrefix The ref block prefix.
     */
    public void setRefBlockPrefix(UInteger refBlockPrefix) {
        this.refBlockPrefix = refBlockPrefix;
    }

    /**
     * Set the ref block prefix by providing its String representation. The
     * String representation can be received from the @link
     * {@link BeowulfJ#getDynamicGlobalProperties
     * getDynamicGlobalProperties} method.
     *
     * @param refBlockPrefix The String representation of the ref block prefix.
     */
    public void setRefBlockPrefix(String refBlockPrefix) {
        this.refBlockPrefix = UInteger.valueOf(CryptoUtils.readUint32(CryptoUtils.HEX.decode(refBlockPrefix), 4));
    }

    /**
     * Get the currently configured expiration date. The expiration date defines
     * in which time the operation has to be processed. If not processed in the
     * given time, the transaction will not be accepted. <b>Notice</b> that this
     * method will return the latest possible expiration date if no other time
     * has been configured using the {@link #setExpirationDate(TimePointSec)
     * setExpirationDate(TimePointSec)} method.
     *
     * @return The expiration date.
     */
    public TimePointSec getExpirationDate() {
        if (this.expirationDate == null || this.expirationDate.getDateTimeAsTimestamp() == 0) {
            // The expiration date is not set by the user so we do it on our own
            // by adding the maximal allowed offset to the current time.
            LOGGER.debug("No expiration date has been provided so the latest possible time is used.");
            return new TimePointSec(
                    System.currentTimeMillis() + BeowulfJConfig.getInstance().getMaximumExpirationDateOffset() - 60000L);
        }

        return this.expirationDate;
    }

    /**
     * Define how long this transaction is valid. If not processed in the given
     * time, the transaction will not be accepted.
     *
     * @param expirationDate The expiration date to set.
     */
    public void setExpirationDate(TimePointSec expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * This method collects the required authorities for all operations stored
     * in this transaction. The returned list is already a minimized version to
     * avoid an "irrelevant signature included Unnecessary signature(s)
     * detected" error.
     *
     * @return All required authorities and private key types.
     */
    protected Map<SignatureObject, PrivateKeyType> getRequiredAuthorities() {

        Map<SignatureObject, PrivateKeyType> requiredAuthorities = new HashMap<>();

        // Iterate over all Operations and collect the requried authorities.
        for (Operation operation : this.getOperations()) {
            requiredAuthorities.putAll(operation.getRequiredAuthorities(requiredAuthorities));
        }

        return requiredAuthorities;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        if (createdTime == null) {
            createdTime = System.currentTimeMillis() / 1000;
        }
        this.createdTime = createdTime;
    }

    /**
     * Validate if all fields of this transaction object have acceptable values.
     *
     * @throws BeowulfInvalidTransactionException In case a field does not fulfill the requirements.
     */
    public void validate() throws BeowulfInvalidTransactionException {
        if (this.getExpirationDate().getDateTimeAsTimestamp() > (new Timestamp(System.currentTimeMillis())).getTime()
                + BeowulfJConfig.getInstance().getMaximumExpirationDateOffset()) {
            LOGGER.warn("The configured expiration date for this transaction is to far "
                    + "in the future and may not be accepted by the Beowulf node.");
        } else if (this.getExpirationDate().getDateTimeAsTimestamp() < (new Timestamp(System.currentTimeMillis()))
                .getTime()) {
            throw new BeowulfInvalidTransactionException("The expiration date can't be in the past.");
        }

        boolean isOwnerKeyRequired = false;

        // Posting authority cannot be mixed with active authority in same
        // transaction
        for (Entry<SignatureObject, PrivateKeyType> requiredAuthorities : getRequiredAuthorities().entrySet()) {
            PrivateKeyType keyType = requiredAuthorities.getValue();

            if (keyType.equals(PrivateKeyType.OWNER)) {
                isOwnerKeyRequired = true;
            }
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
