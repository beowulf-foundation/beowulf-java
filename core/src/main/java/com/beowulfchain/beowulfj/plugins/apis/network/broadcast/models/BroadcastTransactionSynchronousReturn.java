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
package com.beowulfchain.beowulfj.plugins.apis.network.broadcast.models;

import com.beowulfchain.beowulfj.protocol.TransactionId;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Beowulf "broadcast_transaction_synchronous_return"
 * object.
 */
public class BroadcastTransactionSynchronousReturn {
    @JsonProperty("id")
    private TransactionId id;
    @JsonProperty("block_num")
    private int blockNum = 0;
    @JsonProperty("trx_num")
    private int trxNum = 0;
    @JsonProperty("expired")
    private boolean expired = false;
    @JsonProperty("created_time")
    private long createdTime;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected BroadcastTransactionSynchronousReturn() {
    }

    /**
     * Get the Id of the applied transaction.
     *
     * @return The transaction Id.
     */
    public TransactionId getId() {
        return id;
    }

    /**
     * Get the block number the applied transaction has been processed with.
     *
     * @return The block number.
     */
    public int getBlockNum() {
        return blockNum;
    }

    /**
     * Get the transaction number inside the block.
     *
     * @return The transaction number.
     */
    public int getTrxNum() {
        return trxNum;
    }

    /**
     * Check if the applied transaction is already expired.
     *
     * @return <code>True</code> if the transaction is already expired or
     * <code>false</code> if not.
     */
    public boolean isExpired() {
        return expired;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
