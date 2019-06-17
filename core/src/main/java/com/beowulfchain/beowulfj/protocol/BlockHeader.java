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
package com.beowulfchain.beowulfj.protocol;

import com.beowulfchain.beowulfj.base.models.BlockHeaderExtensions;
import com.beowulfchain.beowulfj.base.models.BlockId;
import com.beowulfchain.beowulfj.base.models.Checksum;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.fc.TimePointSec;
import com.beowulfchain.beowulfj.interfaces.ByteTransformable;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.util.ArrayList;
import java.util.List;

public class BlockHeader implements ByteTransformable {
    protected BlockId previous;
    protected TimePointSec timestamp;
    protected AccountName supernode;
    @JsonProperty("transaction_merkle_root")
    protected Checksum transactionMerkleRoot;
    // Original type is "block_header_extensions_type" which is an array of
    // "block_header_extensions".
    protected List<BlockHeaderExtensions> extensions;
    @JsonProperty("block_reward")
    private Asset blockReward;

    /**
     * @return The block id of the previous block.
     */
    public BlockId getPrevious() {
        return previous;
    }

    /**
     * @param previous The block id of the previous block to set.
     */
    public void setPrevious(BlockId previous) {
        this.previous = previous;
    }

    /**
     * @return the supernode
     */
    public AccountName getSupernode() {
        return supernode;
    }

    /**
     * @param supernode the supernode to set
     */
    public void setSupernode(AccountName supernode) {
        this.supernode = supernode;
    }

    /**
     * @return the transactionMerkleRoot
     */
    public Checksum getTransactionMerkleRoot() {
        return transactionMerkleRoot;
    }

    /**
     * @param transactionMerkleRoot the transactionMerkleRoot to set
     */
    public void setTransactionMerkleRoot(Checksum transactionMerkleRoot) {
        this.transactionMerkleRoot = transactionMerkleRoot;
    }

    /**
     * Get the list of configured extensions.
     *
     * @return All extensions.
     */
    public List<BlockHeaderExtensions> getExtensions() {
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
    public void setExtensions(List<BlockHeaderExtensions> extensions) {
        this.extensions = extensions;
    }

    /**
     * @return the timestamp
     */
    public TimePointSec getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(TimePointSec timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        // TODO Auto-generated method stub
        // serializedRecoverAccountOperation
        // .write(BeowulfJUtils.transformIntToVarIntByteArray(this.getExtensions().size()));
        return null;
    }
}
