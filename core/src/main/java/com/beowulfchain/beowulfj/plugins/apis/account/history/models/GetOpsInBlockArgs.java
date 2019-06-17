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

import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

/**
 * This class implements the Beowulf "get_ops_in_block_args" object.
 */
public class GetOpsInBlockArgs {
    @JsonProperty("block_num")
    private UInteger blockNum;
    @JsonProperty("only_virtual")
    private boolean onlyVirtual;

    /**
     * @param blockNum    The <code>blockNum</code> defines for which block number
     *                    operations are requested.
     * @param onlyVirtual Define if only virtual (<code>true</code>) or all operation
     *                    types (<code>false</code>) are requested.
     */
    @JsonCreator()
    public GetOpsInBlockArgs(@JsonProperty("block_num") UInteger blockNum,
                             @JsonProperty("onlyVirtual") boolean onlyVirtual) {
        this.setBlockNum(blockNum);
        this.setOnlyVirtual(onlyVirtual);
    }

    /**
     * @return The block number wrapped by this instance.
     */
    public UInteger getBlockNum() {
        return blockNum;
    }

    /**
     * Override the current <code>blockNum</code> field wrapped by this
     * instance.
     * <p>
     * The <code>blockNum</code> defines for which block number operations are
     * requested.
     *
     * @param blockNum The block number to set.
     */
    public void setBlockNum(UInteger blockNum) {
        this.blockNum = BeowulfJUtils.setIfNotNull(blockNum, "The block number cannot be null.");
    }

    /**
     * Check if only virtual operations (<code>true</code>) or all operation
     * types (<code>false</code>) are requested .
     *
     * @return <code>true</code>, if only virtual operations are requested or
     * <code>false</code> if not.
     */
    public boolean getOnlyVirtual() {
        return onlyVirtual;
    }

    /**
     * Override the current <code>onlyVirtual</code> field wrapped by this
     * instance.
     *
     * @param onlyVirtual Define if only virtual (<code>true</code>) or all operation
     *                    types (<code>false</code>) are requested.
     */
    public void setOnlyVirtual(boolean onlyVirtual) {
        this.onlyVirtual = onlyVirtual;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
