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
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.util.List;

/**
 * This class represents a Beowulf "find_supernodes_args" object.
 */
public class FindSupernodesArgs {
    @JsonProperty("owners")
    private List<AccountName> owners;

    /**
     * @param owners List accounts name.
     */
    @JsonCreator()
    public FindSupernodesArgs(@JsonProperty("owners") List<AccountName> owners) {
        this.setOwners(owners);
    }

    /**
     * @return the owners
     */
    public List<AccountName> getOwners() {
        return owners;
    }

    /**
     * @param owners the owners to set
     */
    public void setOwners(List<AccountName> owners) {
        this.owners = owners;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
