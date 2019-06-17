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

import com.beowulfchain.beowulfj.fc.TimePointSec;
import com.beowulfchain.beowulfj.protocol.AccountName;
import com.beowulfchain.beowulfj.protocol.Asset;
import com.beowulfchain.beowulfj.protocol.Authority;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.util.List;

@JsonIgnoreProperties
public class Account {
    // TODO: Original type is "account_id_type".
    private long id;
    private AccountName name;
    private Authority owner;
    private Authority active;
    @JsonProperty("json_metadata")
    private String jsonMetadata;
    //    private AccountName proxy;
    @JsonProperty("last_owner_update")
    private TimePointSec lastOwnerUpdate;
    @JsonProperty("last_account_update")
    private TimePointSec lastAccountUpdate;
    private TimePointSec created;
    //    private boolean mined;
    @JsonProperty("owner_challenged")
    private boolean ownerChallenged;
    @JsonProperty("active_challenged")
    private boolean activeChallenged;
    @JsonProperty("last_owner_proved")
    private TimePointSec lastOwnerProved;
    @JsonProperty("last_active_proved")
    private TimePointSec lastActiveProved;
    // Orginial type is uint16, but we have to use int here.
    @JsonProperty("voting_power")
    private int votingPower;
    @JsonProperty("last_vote_time")
    private TimePointSec lastVoteTime;
    private Asset balance;
    @JsonProperty("wd_balance")
    private Asset wdBalance;
    @JsonProperty("vesting_shares")
    private Asset vestingShares;
    @JsonProperty("vesting_withdraw_rate")
    private Asset vestingWithdrawRate;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("withdrawn")
    private long withdrawn;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("to_withdraw")
    private long toWithdraw;
    // Original type is uint16, but we have to use int here.
    @JsonProperty("supernodes_voted_for")
    private int supernodesVotedFor;
    @JsonProperty("next_vesting_withdrawal")
    private TimePointSec nextVestingWithdraw;
    @JsonProperty("token_list")
    private List<Asset> tokenList;
    @JsonProperty("vesting_balance")
    private Asset vestingBalance;
    @JsonProperty("supernode_votes")
    private List<String> supernodeVotes;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected Account() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the name
     */
    public AccountName getName() {
        return name;
    }

    /**
     * @return the owner
     */
    public Authority getOwner() {
        return owner;
    }

    /**
     * @return the active
     */
    public Authority getActive() {
        return active;
    }

    /**
     * @return the jsonMetadata
     */
    public String getJsonMetadata() {
        return jsonMetadata;
    }

    /**
     * @return the lastOwnerUpdate
     */
    public TimePointSec getLastOwnerUpdate() {
        return lastOwnerUpdate;
    }

    /**
     * @return the lastAccountUpdate
     */
    public TimePointSec getLastAccountUpdate() {
        return lastAccountUpdate;
    }

    /**
     * @return the created
     */
    public TimePointSec getCreated() {
        return created;
    }

    /**
     * @return the ownerChallenged
     */
    public boolean isOwnerChallenged() {
        return ownerChallenged;
    }

    /**
     * @return the activeChallenged
     */
    public boolean isActiveChallenged() {
        return activeChallenged;
    }

    /**
     * @return the lastOwnerProved
     */
    public TimePointSec getLastOwnerProved() {
        return lastOwnerProved;
    }

    /**
     * @return the lastActiveProved
     */
    public TimePointSec getLastActiveProved() {
        return lastActiveProved;
    }

    /**
     * @return the votingPower
     */
    public int getVotingPower() {
        return votingPower;
    }

    /**
     * @return the lastVoteTime
     */
    public TimePointSec getLastVoteTime() {
        return lastVoteTime;
    }

    /**
     * @return the balance
     */
    public Asset getBalance() {
        return balance;
    }

    /**
     * @return the wdBalance
     */
    public Asset getWdBalance() {
        return wdBalance;
    }

    /**
     * @return the vestingShares
     */
    public Asset getVestingShares() {
        return vestingShares;
    }

    /**
     * @return the vestingWithdrawRate
     */
    public Asset getVestingWithdrawRate() {
        return vestingWithdrawRate;
    }

    /**
     * @return the withdrawn
     */
    public long getWithdrawn() {
        return withdrawn;
    }

    /**
     * @return the toWithdraw
     */
    public long getToWithdraw() {
        return toWithdraw;
    }

    /**
     * @return the supernodesVotedFor
     */
    public int getSupernodesVotedFor() {
        return supernodesVotedFor;
    }

    public TimePointSec getNextVestingWithdraw() {
        return nextVestingWithdraw;
    }

    public List<Asset> getTokenList() {
        return tokenList;
    }

    public Asset getVestingBalance() {
        return vestingBalance;
    }

    public List<String> getSupernodeVotes() {
        return supernodeVotes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
