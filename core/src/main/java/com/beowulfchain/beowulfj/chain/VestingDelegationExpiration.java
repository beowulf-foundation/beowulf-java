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
package com.beowulfchain.beowulfj.chain;

import com.beowulfchain.beowulfj.fc.TimePointSec;
import com.beowulfchain.beowulfj.protocol.AccountName;
import com.beowulfchain.beowulfj.protocol.Asset;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VestingDelegationExpiration {
    // Original type is "id_type" so we use long here.
    @JsonProperty("id")
    private long id;
    @JsonProperty("delegator")
    private AccountName delegator;
    @JsonProperty("vesting_shares")
    private Asset vestingShares;
    @JsonProperty("expiration")
    private TimePointSec expiration;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private VestingDelegationExpiration() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the delegator
     */
    public AccountName getDelegator() {
        return delegator;
    }

    /**
     * @param delegator the delegator to set
     */
    public void setDelegator(AccountName delegator) {
        this.delegator = delegator;
    }

    /**
     * @return the vestingShares
     */
    public Asset getVestingShares() {
        return vestingShares;
    }

    /**
     * @param vestingShares the vestingShares to set
     */
    public void setVestingShares(Asset vestingShares) {
        this.vestingShares = vestingShares;
    }

    /**
     * @return the expiration
     */
    public TimePointSec getExpiration() {
        return expiration;
    }

    /**
     * @param expiration the expiration to set
     */
    public void setExpiration(TimePointSec expiration) {
        this.expiration = expiration;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
