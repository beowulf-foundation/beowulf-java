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
package com.beowulfchain.beowulfj.chain;

import com.beowulfchain.beowulfj.base.models.BlockId;
import com.beowulfchain.beowulfj.base.models.FutureExtensions;
import com.beowulfchain.beowulfj.fc.TimePointSec;
import com.beowulfchain.beowulfj.protocol.TransactionId;
import com.beowulfchain.beowulfj.protocol.operations.Operation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joou.UInteger;
import org.joou.UShort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * this class present Transaction had been mined
 */
public class CompletedTransaction extends SignedTransaction {
    private static final long serialVersionUID = 4821422578657270330L;
    private static final Logger LOGGER = LoggerFactory.getLogger(CompletedTransaction.class);

    @JsonProperty("transaction_id")
    protected TransactionId transactionId;
    @JsonProperty("block_num")
    protected long blockNum;
    @JsonProperty("transaction_num")
    protected long transactionNum;

    /**
     * This constructor is only used to create the POJO from a JSON response.
     */
    @JsonCreator
    private CompletedTransaction(@JsonProperty("ref_block_num") UShort refBlockNum,
                                 @JsonProperty("ref_block_prefix") UInteger refBlockPrefix,
                                 @JsonProperty("expiration") TimePointSec expirationDate,
                                 @JsonProperty("operations") List<Operation> operations,
                                 @JsonProperty("extensions") List<FutureExtensions> extensions,
                                 @JsonProperty("signatures") List<String> signatures,
                                 @JsonProperty("created_time") Long createdTime,
                                 @JsonProperty("transaction_id") TransactionId transactionId,
                                 @JsonProperty("block_num") Long blockNum,
                                 @JsonProperty("transaction_num") Long transactionNum) {
        super(refBlockNum, refBlockPrefix, expirationDate, operations, extensions, createdTime);
        this.setTransactionId(transactionId);
        this.setBlockNum(blockNum);
        this.setTransactionNum(transactionNum);
    }

    /**
     * Create a new signed transaction object.
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
     */
    public CompletedTransaction(UShort refBlockNum, UInteger refBlockPrefix, TimePointSec expirationDate,
                                List<Operation> operations, List<FutureExtensions> extensions, Long createdTime,
                                TransactionId transactionId, Long blockNum, Long transactionNum) {
        super(refBlockNum, refBlockPrefix, expirationDate, operations, extensions, createdTime);
        this.setTransactionId(transactionId);
        this.setBlockNum(blockNum);
        this.setTransactionNum(transactionNum);

    }

    /**
     * Like
     * {@link #CompletedTransaction(BlockId, List, List)},
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
    public CompletedTransaction(BlockId blockId, List<Operation> operations, List<FutureExtensions> extensions) {
        super(blockId, operations, extensions);
    }

    public TransactionId getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(TransactionId transactionId) {
        this.transactionId = transactionId;
    }

    public long getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(long blockNum) {
        this.blockNum = blockNum;
    }

    public long getTransactionNum() {
        return transactionNum;
    }

    public void setTransactionNum(long transactionNum) {
        this.transactionNum = transactionNum;
    }
}
