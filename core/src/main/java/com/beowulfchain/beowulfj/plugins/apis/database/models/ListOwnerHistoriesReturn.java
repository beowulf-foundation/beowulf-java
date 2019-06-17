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

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.util.List;

/**
 * This class represents a Beowulf "list_owner_histories_return" object.
 */
public class ListOwnerHistoriesReturn {
    @JsonProperty("owner_auths")
    private List<OwnerAuthorityHistory> ownerAuths;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     * <p>
     * Visibility set to <code>protected</code> as it is the parent of the
     * {@link FindOwnerHistoriesReturn} class.
     */
    protected ListOwnerHistoriesReturn() {
    }

    /**
     * @return the ownerAuths
     */
    public List<OwnerAuthorityHistory> getOwnerAuths() {
        return ownerAuths;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
