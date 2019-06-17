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

import com.beowulfchain.beowulfj.plugins.apis.database.enums.SortOrderType;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

/**
 * This class represents a Beowulf "list_accounts_args" object.
 */
public class ListAccountsArgs {
    // TODO: Original type is "fc::variant".
    @JsonProperty("start")
    private Object start;
    @JsonProperty("limit")
    private UInteger limit;
    @JsonProperty("order")
    private SortOrderType order;

    /**
     * @param start
     * @param limit
     * @param order
     */
    public ListAccountsArgs(@JsonProperty("start") Object start, @JsonProperty("limit") UInteger limit,
                            @JsonProperty("order") SortOrderType order) {
        this.setStart(start);
        this.setLimit(limit);
        this.setOrder(order);
    }

    /**
     * @return the start
     */
    public Object getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Object start) {
        this.start = BeowulfJUtils.setIfNotNull(start, "The start needs to be provided.");
    }

    /**
     * @return the limit
     */
    public UInteger getLimit() {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit(UInteger limit) {
        this.limit = BeowulfJUtils.setIfNotNull(limit, "The limit needs to be provided.");
    }

    /**
     * @return the order
     */
    public SortOrderType getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(SortOrderType order) {
        this.order = BeowulfJUtils.setIfNotNull(order, "The order needs to be provided.");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
