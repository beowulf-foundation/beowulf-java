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
package com.beowulfchain.beowulfj.plugins.apis.account.history.models;

import com.beowulfchain.beowulfj.fc.TimePointSec;
import com.beowulfchain.beowulfj.protocol.TransactionId;
import com.beowulfchain.beowulfj.protocol.operations.Operation;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;
import org.joou.ULong;
import org.joou.UShort;

/**
 * This class is the java implementation of the Beowulf "api_operation_object"
 * object.
 */
public class AppliedOperation {
    @JsonProperty("trx_id")
    private TransactionId trxId;
    // Original type is uint32_t.
    @JsonProperty("block")
    private UInteger block;
    // Original type is uint32_t.
    @JsonProperty("trx_in_block")
    private UInteger trxInBlock;
    // Original type is uint16_t.
    @JsonProperty("op_in_trx")
    private UShort opInTrx;
    // Original type is uint64_t.
    @JsonProperty("virtual_op")
    private ULong virtualOp;
    @JsonProperty("timestamp")
    private TimePointSec timestamp;
    @JsonProperty("op")
    private Operation op;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private AppliedOperation() {
    }

    /**
     * Get the id of this transaction.
     *
     * @return The transaction id.
     */
    public TransactionId getTrxId() {
        return trxId;
    }

    /**
     * Get the block number.
     *
     * @return The block number.
     */
    public UInteger getBlock() {
        return block;
    }

    /**
     * Get the index of the transaction inside the block.
     *
     * @return The transaction index in the block.
     */
    public UInteger getTrxInBlock() {
        return trxInBlock;
    }

    /**
     * Get the index of the operation inside the transaction.
     *
     * @return The operation index in the transaction.
     */
    public UShort getOpInTrx() {
        return opInTrx;
    }

    /**
     * Get the index of the virtual operation inside the transaction.
     *
     * @return The virtual operation index in the transaction.
     */
    public ULong getVirtualOp() {
        return virtualOp;
    }

    /**
     * Get the time point at which this transaction has been submitted.
     *
     * @return The submission date and time.
     */
    public TimePointSec getTimestamp() {
        return timestamp;
    }

    /**
     * Get the whole operation object.
     *
     * @return The operation object.
     */
    public Operation getOp() {
        return op;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
