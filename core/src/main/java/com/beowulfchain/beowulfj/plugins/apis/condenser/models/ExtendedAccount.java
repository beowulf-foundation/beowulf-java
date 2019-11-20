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
package com.beowulfchain.beowulfj.plugins.apis.condenser.models;

import com.beowulfchain.beowulfj.base.models.Account;
import com.beowulfchain.beowulfj.protocol.Asset;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * This class represents a Beowulf "extended_account" object.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtendedAccount extends Account {
    /**
     * Convert vesting_shares to vesting Beowulf.
     */
    @JsonProperty("vesting_balance")
    private Asset vestingBalance;
    // Original type is "share_type" which is a "safe<int64_t>".
    private long reputation;
    @JsonProperty("supernode_votes")
    private List<String> supernodeVotes;
    // Original type is "vector<pair<string,uint32_t>>".

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private ExtendedAccount() {
    }

    /**
     * @return the vestingBalance
     */
    public Asset getVestingBalance() {
        return vestingBalance;
    }

    /**
     * @param vestingBalance the vestingBalance to set
     */
    public void setVestingBalance(Asset vestingBalance) {
        this.vestingBalance = vestingBalance;
    }

    /**
     * @return the reputation
     */
    public long getReputation() {
        return reputation;
    }

    /**
     * @param reputation the reputation to set
     */
    public void setReputation(long reputation) {
        this.reputation = reputation;
    }

    public void setSupernodeVotes(List<String> supernodeVotes) {
        this.supernodeVotes = supernodeVotes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
