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
import com.beowulfchain.beowulfj.protocol.TransactionId;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.security.InvalidParameterException;

/**
 * This class implements the Beowulf "get_transaction_args" object.
 */
public class GetTransactionArgs {
    @JsonProperty("id")
    private TransactionId id;

    /**
     * Create a new {@link GetAccountHistoryArgs} instance to be passed to the
     * {@link AccountHistoryApi#getAccountHistory(CommunicationHandler, GetAccountHistoryArgs)}
     * method.
     *
     * @param id The {@link TransactionId} to set.
     */
    @JsonCreator()
    public GetTransactionArgs(@JsonProperty("id") TransactionId id) {
        this.setId(id);
    }

    /**
     * @return The {@link TransactionId} wrapped by this instance.
     */
    public TransactionId getId() {
        return id;
    }

    /**
     * Override the current {@link TransactionId} wrapped by this instance.
     * Please notice that the <code>id</code> is required. If not provided, the
     * method will throw an {@link InvalidParameterException}.
     *
     * @param id The {@link TransactionId} to set.
     * @throws InvalidParameterException If the <code>id</code> is null.
     */
    public void setId(TransactionId id) {
        this.id = BeowulfJUtils.setIfNotNull(id, "The id cannot be null.");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
