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
package com.beowulfchain.beowulfj.plugins.apis.block.models;

import com.beowulfchain.beowulfj.base.models.Block;
import com.beowulfchain.beowulfj.base.models.BlockId;
import com.beowulfchain.beowulfj.protocol.Asset;
import com.beowulfchain.beowulfj.protocol.PublicKey;
import com.beowulfchain.beowulfj.protocol.SignedBlock;
import com.beowulfchain.beowulfj.protocol.TransactionId;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.util.List;

/**
 * This class is the java implementation of the Beowulf "signed_block_with_info"
 * object.
 */
public class ExtendedSignedBlock extends SignedBlock {
    @JsonProperty("block_id")
    private BlockId blockId;
    @JsonProperty("signing_key")
    private PublicKey signingKey;
    @JsonProperty("transaction_ids")
    private List<TransactionId> transactionIds;
    @JsonProperty("block_reward")
    private Asset blockReward;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     * <p>
     * Visibility set to protected as this is the parent class of {@link Block}.
     */
    protected ExtendedSignedBlock() {
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

    public Asset getBlockReward() {
        return blockReward;
    }

    public void setBlockReward(Asset blockReward) {
        this.blockReward = blockReward;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
