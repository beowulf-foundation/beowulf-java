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

import com.beowulfchain.beowulfj.communication.CommunicationHandler;
import com.beowulfchain.beowulfj.plugins.apis.account.history.AccountHistoryApi;
import com.beowulfchain.beowulfj.protocol.AccountName;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;
import org.joou.ULong;
import javax.annotation.Nullable;

/**
 * This class implements the Beowulf "get_account_history_args" object.
 */
public class GetAccountHistoryArgs {
    @JsonProperty("account")
    private AccountName account;
    @JsonProperty("start")
    private ULong start;
    @JsonProperty("limit")
    private UInteger limit;

    /**
     * Create a new {@link GetAccountHistoryArgs} instance to be passed to the
     * {@link AccountHistoryApi#getAccountHistory(CommunicationHandler, GetAccountHistoryArgs)}
     * method.
     * <p>
     * Define how many operations (see {@link #setLimit(UInteger)} and
     * {@link #setStart(ULong)}) for which <code>account</code> are requested.
     *
     * @param account The account name to request the history for.
     * @param start   The id of the first operation to return. If not provided,
     *                <code>-1</code> will be used as a default value.
     * @param limit   The number of results to return. If not provided,
     *                <code>1000</code> will be used as a default value.
     */
    @JsonCreator()
    public GetAccountHistoryArgs(@JsonProperty("account") AccountName account,
                                 @Nullable @JsonProperty("start") ULong start, @Nullable @JsonProperty("limit") UInteger limit) {
        this.setAccount(account);
        this.setStart(start);
        this.setLimit(limit);
    }

    /**
     * @return The currently configured <code>account</code> to request the
     * history for.
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * Override the currently configured <code>account</code> to request the
     * history for.
     *
     * @param account The <code>account</code> to request the history for.
     */
    public void setAccount(AccountName account) {
        this.account = BeowulfJUtils.setIfNotNull(account, "The account is required.");
    }

    /**
     * @return The currently configured <code>start</code> parameter.
     */
    public ULong getStart() {
        return start;
    }

    /**
     * Override the currently configured <code>start</code> parameter. The
     * <code>start</code> parameter defines the first operation returned.
     *
     * @param start The <code>start</code> parameter to set.
     */
    public void setStart(ULong start) {
        // If not provided set the start to its default value.
        this.start = BeowulfJUtils.setIfNotNull(start, ULong.valueOf(-1));
    }

    /**
     * @return the limit
     */
    public UInteger getLimit() {
        return limit;
    }

    /**
     * Override the currently configured <code>limit</code> parameter. The
     * <code>limit</code> parameter defines how many operations made by the
     * {@link #getAccount() Account} are requested.
     *
     * @param limit The <code>limit</code> parameter to set.
     */
    public void setLimit(@Nullable UInteger limit) {
        // If not provided set the limit to its default value.
        this.limit = BeowulfJUtils.setIfNotNull(limit, UInteger.valueOf(1000));
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
