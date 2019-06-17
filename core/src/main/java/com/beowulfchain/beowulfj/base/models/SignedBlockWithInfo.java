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

import com.beowulfchain.beowulfj.protocol.PublicKey;
import com.beowulfchain.beowulfj.protocol.SignedBlock;
import com.beowulfchain.beowulfj.protocol.TransactionId;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.util.List;

public class SignedBlockWithInfo extends SignedBlock {
    @JsonProperty("block_id")
    private BlockId blockId;
    @JsonProperty("signing_key")
    private PublicKey signingKey;
    @JsonProperty("transaction_ids")
    private List<TransactionId> transactionIds;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected SignedBlockWithInfo() {
    }

    /**
     * @return the blockId
     */
    public BlockId getBlockId() {
        return blockId;
    }

    /**
     * @param blockId the blockId to set
     */
    public void setBlockId(BlockId blockId) {
        this.blockId = blockId;
    }

    /**
     * @return the signingKey
     */
    public PublicKey getSigningKey() {
        return signingKey;
    }

    /**
     * @param signingKey the signingKey to set
     */
    public void setSigningKey(PublicKey signingKey) {
        this.signingKey = signingKey;
    }

    /**
     * @return the transactionIds
     */
    public List<TransactionId> getTransactionIds() {
        return transactionIds;
    }

    /**
     * @param transactionIds the transactionIds to set
     */
    public void setTransactionIds(List<TransactionId> transactionIds) {
        this.transactionIds = transactionIds;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
