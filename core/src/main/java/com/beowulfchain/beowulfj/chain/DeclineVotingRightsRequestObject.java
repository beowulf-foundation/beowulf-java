package com.beowulfchain.beowulfj.chain;

import com.beowulfchain.beowulfj.fc.TimePointSec;
import com.beowulfchain.beowulfj.protocol.AccountName;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DeclineVotingRightsRequestObject {
    // Original type is "id_type".
    @JsonProperty("id")
    private long id;
    @JsonProperty("account")
    private AccountName account;
    @JsonProperty("effective_date")
    private TimePointSec effectiveDate;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private DeclineVotingRightsRequestObject() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the account
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * @return the effectiveDate
     */
    public TimePointSec getEffectiveDate() {
        return effectiveDate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
