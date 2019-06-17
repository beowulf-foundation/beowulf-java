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
package com.beowulfchain.beowulfj.protocol.operations;

import com.beowulfchain.beowulfj.enums.ValidationType;
import com.beowulfchain.beowulfj.protocol.Authority;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.security.InvalidParameterException;

/**
 * This abstract class contains fields that exist in all Beowulf Operations
 * related to the account creation / update.
 */
public abstract class AbstractAccountOperation extends Operation {
    @JsonProperty("owner")
    protected Authority owner;
    @JsonProperty("json_metadata")
    protected String jsonMetadata;

    /**
     * Create a new Operation object by providing the operation type.
     *
     * @param virtual Define if the operation instance is a virtual
     *                (<code>true</code>) or a market operation
     *                (<code>false</code>).
     */
    protected AbstractAccountOperation(boolean virtual) {
        super(virtual);
    }

    /**
     * Get the owner {@link Authority
     * Authority}.
     *
     * @return The owner authority.
     */
    public abstract Authority getOwner();

    /**
     * Set the owner {@link Authority
     * Authority}.
     *
     * @param owner The owner authority.
     */
    public abstract void setOwner(Authority owner);

    /**
     * Get the json metadata that has been added to this operation.
     *
     * @return The json metadata that has been added to this operation.
     */
    public String getJsonMetadata() {
        return jsonMetadata;
    }

    /**
     * Add json metadata to this operation.
     *
     * @param jsonMetadata The json metadata.
     */
    public void setJsonMetadata(String jsonMetadata) {
        this.jsonMetadata = jsonMetadata;
    }

    @Override
    public void validate(ValidationType validationType) {
        if (!ValidationType.SKIP_VALIDATION.equals(validationType)
                && (!jsonMetadata.isEmpty() && !BeowulfJUtils.verifyJsonString(jsonMetadata))) {
            throw new InvalidParameterException("The given json metadata is no valid JSON");
        }
    }
}
