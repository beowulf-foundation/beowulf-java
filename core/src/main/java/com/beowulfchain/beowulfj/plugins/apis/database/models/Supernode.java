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

import com.beowulfchain.beowulfj.base.models.Version;
import com.beowulfchain.beowulfj.enums.WitnessScheduleType;
import com.beowulfchain.beowulfj.fc.TimePointSec;
import com.beowulfchain.beowulfj.protocol.AccountName;
import com.beowulfchain.beowulfj.protocol.HardforkVersion;
import com.beowulfchain.beowulfj.protocol.Price;
import com.beowulfchain.beowulfj.protocol.PublicKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.math.BigInteger;
import java.net.URL;

/**
 * This class represents a Beowulf "witness_api_object" object.
 */
public class Supernode {
    private int id;
    private AccountName owner;
    private TimePointSec created;
    private URL url;
    // The original type is "uint32_t".
    @JsonProperty("total_missed")
    private long totalMissed;
    // The original type is "uint64_t".
    @JsonProperty("last_aslot")
    private BigInteger lastAslot;
    // The original type is "uint64_t".
    @JsonProperty("last_confirmed_block_num")
    private BigInteger lastConfirmedBlockNum;
    /**
     * Some supernodes have the job because they did a proof of work, this field
     * indicates where they were in the POW order. After each round, the supernode
     * with the lowest pow_worker value greater than 0 is removed.
     */
    // The original type is "uint64_t".
    @JsonProperty("pow_worker")
    private BigInteger powWorker;
    @JsonProperty("signing_key")
    private PublicKey signingKey;
    @JsonProperty("wd_exchange_rate")
    private Price wdExchangeRate;
    @JsonProperty("last_wd_exchange_update")
    private TimePointSec lastWdExchangeUpdate;
    /**
     * The total votes for this supernode. This determines how the supernode is
     * ranked for scheduling. The top N supernodes by votes are scheduled every
     * round, every one else takes turns being scheduled proportional to their
     * votes.
     */
    // The original type is "share_type" which is an "int64".
    private long votes;
    /**
     * How the supernode was scheduled the last time it was scheduled.
     */
    private WitnessScheduleType schedule;
    /**
     * These fields are used for the supernode scheduling algorithm which uses
     * virtual time to ensure that all supernodes are given proportional time for
     * producing blocks.
     * <p>
     * {@link #votes} is used to determine speed. The
     * {@link #virtualScheduledTime} is the expected time at which this supernode
     * should complete a virtual lap which is defined as the position equal to
     * 1000 times MAXVOTES.
     * <p>
     * virtual_scheduled_time = virtual_last_update + (1000*MAXVOTES -
     * virtual_position) / votes
     * <p>
     * Every time the number of votes changes the virtual_position and
     * virtual_scheduled_time must update. There is a global current
     * virtual_scheduled_time which gets updated every time a supernode is
     * scheduled. To update the virtual_position the following math is
     * performed.
     * <p>
     * virtual_position = virtual_position + votes * (virtual_current_time -
     * virtual_last_update) virtual_last_update = virtual_current_time votes +=
     * delta_vote virtual_scheduled_time = virtual_last_update + (1000*MAXVOTES
     * - virtual_position) / votes
     */
    // Original type is "fc::uint128".
    @JsonProperty("virtual_last_update")
    private String virtualLastUpdate;
    // Original type is "fc::uint128".
    @JsonProperty("virtual_position")
    private String virtualPosition;
    // Original type is "fc::uint128".
    @JsonProperty("virtual_scheduled_time")
    private BigInteger virtualScheduledTime;

    // Original type is "digest_type" which is a "fc:sha256" object.
    @JsonProperty("last_work")
    private String lastWork;
    @JsonProperty("running_version")
    private Version runningVersion;
    @JsonProperty("hardfork_version_vote")
    private HardforkVersion hardforkVersionVote;
    @JsonProperty("hardfork_time_vote")
    private TimePointSec hardforkTimeVote;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private Supernode() {
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the owner
     */
    public AccountName getOwner() {
        return owner;
    }

    /**
     * @return the created
     */
    public TimePointSec getCreated() {
        return created;
    }

    /**
     * @return the url
     */
    public URL getUrl() {
        return url;
    }

    /**
     * @return the totalMissed
     */
    public long getTotalMissed() {
        return totalMissed;
    }

    /**
     * @return the lastAslot
     */
    public BigInteger getLastAslot() {
        return lastAslot;
    }

    /**
     * @return the lastConfirmedBlockNum
     */
    public BigInteger getLastConfirmedBlockNum() {
        return lastConfirmedBlockNum;
    }

    /**
     * @return the powWorker
     */
    public BigInteger getPowWorker() {
        return powWorker;
    }

    /**
     * @return the signingKey
     */
    public PublicKey getSigningKey() {
        return signingKey;
    }

    /**
     * @return the wdExchangeRate
     */
    public Price getWdExchangeRate() {
        return wdExchangeRate;
    }

    /**
     * @return the lastWdExchangeUpdate
     */
    public TimePointSec getLastWdExchangeUpdate() {
        return lastWdExchangeUpdate;
    }

    /**
     * @return the votes
     */
    public long getVotes() {
        return votes;
    }

    /**
     * @return the schedule
     */
    public WitnessScheduleType getSchedule() {
        return schedule;
    }

    /**
     * @return the virtualLastUpdate
     */
    public String getVirtualLastUpdate() {
        return virtualLastUpdate;
    }

    /**
     * @return the virtualPosition
     */
    public String getVirtualPosition() {
        return virtualPosition;
    }

    /**
     * @return the virtualScheduledTime
     */
    public BigInteger getVirtualScheduledTime() {
        return virtualScheduledTime;
    }

    /**
     * @return the lastWork
     */
    public String getLastWork() {
        return lastWork;
    }

    /**
     * @return the runningVersion
     */
    public Version getRunningVersion() {
        return runningVersion;
    }

    /**
     * @return the hardforkVersionVote
     */
    public HardforkVersion getHardforkVersionVote() {
        return hardforkVersionVote;
    }

    /**
     * @return the hardforkTimeVote
     */
    public TimePointSec getHardforkTimeVote() {
        return hardforkTimeVote;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
