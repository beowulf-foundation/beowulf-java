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

import com.beowulfchain.beowulfj.protocol.AccountName;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.math.BigInteger;
import java.util.List;

/**
 * This class represents a Beowulf "supernode_schedule_object" object.
 */
public class SupernodeSchedule {
    // Original type is "id_type".
    private long id;
    @JsonProperty("current_virtual_time")
    // Original type is "uint128".
    private BigInteger currentVirtualTime;
    // Original type is "uint32_t".
    @JsonProperty("next_shuffle_block_num")
    private int nextShuffleBlockNum;
    @JsonProperty("current_shuffled_supernodes")
    private List<AccountName> currentShuffledSupernodes;
    // Original type is "uint8_t".
    @JsonProperty("num_scheduled_supernodes")
    private short numScheduledSupernodes;
    // Original type is "uint8_t".
    @JsonProperty("top19_weight")
    private short top19Weight;
    // Original type is "uint8_t".
    @JsonProperty("hardfork_required_supernodes")
    private short hardforkRequiredSupernodes;
    // Original type is "uint8_t".
    @JsonProperty("max_voted_supernodes")
    private short maxVotedSupernodes;
    // Original type is "uint8_t".
    @JsonProperty("max_runner_supernodes")
    private short maxRunnerSupernodes;
    // Original type is "uint8_t".
    @JsonProperty("max_miner_supernodes")
    private short maxMinerSupernodes;
    // Original type is "uint8_t".
    @JsonProperty("timeshare_weight")
    private short timeshareWeight;
    // Original type is "uint8_t".
    @JsonProperty("miner_weight")
    private short minerWeight;
    // Original type is "uint32_t".
    @JsonProperty("supernode_pay_normalization_factor")
    private int supernodePayNormalizationFactor;
    // Original type is version which is a "uint32_t". The actual returned value
    // is the real version (e.g. 0.19.0) so we use String here.
    @JsonProperty("majority_version")
    private String majorityVersion;
    @JsonProperty("elected_weight")
    private short electedWeight;
    @JsonProperty("permanent_weight")
    private short permanentWeight;
    @JsonProperty("max_permanent_supernodes")
    private short maxPermanentSupernodes;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private SupernodeSchedule() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the currentVirtualTime
     */
    public BigInteger getCurrentVirtualTime() {
        return currentVirtualTime;
    }

    /**
     * @return the nextShuffleBlockNum
     */
    public int getNextShuffleBlockNum() {
        return nextShuffleBlockNum;
    }

    /**
     * @return the currentShuffledSupernodes
     */
    public List<AccountName> getCurrentShuffledSupernodes() {
        return currentShuffledSupernodes;
    }

    /**
     * @return the numScheduledSupernodes
     */
    public short getNumScheduledSupernodes() {
        return numScheduledSupernodes;
    }

    /**
     * @return the top19Weight
     */
    public short getTop19Weight() {
        return top19Weight;
    }

    /**
     * @return the hardforkRequiredSupernodes
     */
    public short getHardforkRequiredSupernodes() {
        return hardforkRequiredSupernodes;
    }

    /**
     * @return the maxVotedSupernodes
     */
    public short getMaxVotedSupernodes() {
        return maxVotedSupernodes;
    }

    /**
     * @return the maxRunnerSupernodes
     */
    public short getMaxRunnerSupernodes() {
        return maxRunnerSupernodes;
    }

    /**
     * @return the maxMinerSupernodes
     */
    public short getMaxMinerSupernodes() {
        return maxMinerSupernodes;
    }

    /**
     * @return the timeshareWeight
     */
    public short getTimeshareWeight() {
        return timeshareWeight;
    }

    /**
     * @return the minerWeight
     */
    public short getMinerWeight() {
        return minerWeight;
    }

    /**
     * @return the supernodePayNormalizationFactor
     */
    public int getSupernodePayNormalizationFactor() {
        return supernodePayNormalizationFactor;
    }

    /**
     * @return the majorityVersion
     */
    public String getMajorityVersion() {
        return majorityVersion;
    }

    public short getElectedWeight() {
        return electedWeight;
    }

    public short getPermanentWeight() {
        return permanentWeight;
    }

    public short getMaxPermanentSupernodes() {
        return maxPermanentSupernodes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
