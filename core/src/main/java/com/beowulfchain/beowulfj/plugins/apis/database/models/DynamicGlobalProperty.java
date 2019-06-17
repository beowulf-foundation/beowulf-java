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
package com.beowulfchain.beowulfj.plugins.apis.database.models;

import com.beowulfchain.beowulfj.base.models.BlockId;
import com.beowulfchain.beowulfj.fc.TimePointSec;
import com.beowulfchain.beowulfj.plugins.apis.condenser.models.ExtendedDynamicGlobalProperties;
import com.beowulfchain.beowulfj.protocol.AccountName;
import com.beowulfchain.beowulfj.protocol.Asset;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.math.BigInteger;

/**
 * This class represents the Beowulf "dynamic_global_property_api_obj" object.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DynamicGlobalProperty {
    // Original type is id_type which is a uint16_t so we use int here.
    private int id;
    // Original type is uint32_t so we use long here.
    @JsonProperty("head_block_number")
    private long headBlockNumber;
    @JsonProperty("head_block_id")
    private BlockId headBlockId;
    @JsonProperty("time")
    private TimePointSec time;
    @JsonProperty("current_witness")
    private AccountName currentWitness;
    @JsonProperty("virtual_supply")
    private Asset virtualSupply;
    @JsonProperty("current_supply")
    private Asset currentSupply;
    @JsonProperty("current_wd_supply")
    private Asset currentWdSupply;
    @JsonProperty("total_vesting_fund_beowulf")
    private Asset totalVestingFundBeowulf;
    @JsonProperty("total_vesting_shares")
    private Asset totalVestingShares;
    @JsonProperty("total_reward_fund_beowulf")
    private Asset totalRewardFundBeowulf;
    @JsonProperty("pending_rewarded_vesting_beowulf")
    private Asset pendingRewardedVestingBeowulf;
    // Original type is uint16_t so we use int here.
    @JsonProperty("wd_interest_rate")
    private int wdInterestRate;
    // Original type is uint64_t so we use BigInteger here.
    @JsonProperty("current_aslot")
    private BigInteger currentAslot;
    // Original type is uint128 so we use BigInteger here.
    @JsonProperty("recent_slots_filled")
    private BigInteger recentSlotsFilled;
    // Original type is uint8_t so we use short here.
    @JsonProperty("participation_count")
    private short participationCount;
    // Original type is uint32_t so we use long here.
    @JsonProperty("last_irreversible_block_num")
    private long lastIrreversibleBlockNum;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     * <p>
     * Visibility <code>protected</code> as
     * {@link ExtendedDynamicGlobalProperties} is a child class.
     */
    protected DynamicGlobalProperty() {
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the headBlockNumber
     */
    public long getHeadBlockNumber() {
        return headBlockNumber;
    }

    /**
     * @return the headBlockId
     */
    public BlockId getHeadBlockId() {
        return headBlockId;
    }

    /**
     * @return the currentWitness
     */
    public AccountName getCurrentWitness() {
        return currentWitness;
    }

    /**
     * @return the virtualSupply
     */
    public Asset getVirtualSupply() {
        return virtualSupply;
    }

    /**
     * @return the currentSupply
     */
    public Asset getCurrentSupply() {
        return currentSupply;
    }

    /**
     * @return the currentWdSupply
     */
    public Asset getCurrentWdSupply() {
        return currentWdSupply;
    }

    /**
     * @return the totalVestingFundBeowulf
     */
    public Asset getTotalVestingFundBeowulf() {
        return totalVestingFundBeowulf;
    }

    /**
     * @return the totalVestingShares
     */
    public Asset getTotalVestingShares() {
        return totalVestingShares;
    }

    /**
     * @return the totalRewardFundBeowulf
     */
    public Asset getTotalRewardFundBeowulf() {
        return totalRewardFundBeowulf;
    }

    /**
     * @return the pendingRewardedVestingBeowulf
     */
    public Asset getPendingRewardedVestingBeowulf() {
        return pendingRewardedVestingBeowulf;
    }

    /**
     * @return This property defines the interest rate that W deposits
     * receive.
     */
    public int getWdInterestRate() {
        return wdInterestRate;
    }

    /**
     * @return The current absolute slot number. Equal to the total number of
     * slots since genesis. Also equal to the total number of missed
     * slots plus head_block_number.
     */
    public BigInteger getCurrentAslot() {
        return currentAslot;
    }

    /**
     * @return The recentSlotsFilled - Used to compute supernode participation.
     */
    public BigInteger getRecentSlotsFilled() {
        return recentSlotsFilled;
    }

    /**
     * @return the participationCount
     */
    public short getParticipationCount() {
        return participationCount;
    }

    /**
     * @return the lastIrreversibleBlockNum
     */
    public long getLastIrreversibleBlockNum() {
        return lastIrreversibleBlockNum;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
