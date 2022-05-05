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

import com.beowulfchain.beowulfj.enums.PrivateKeyType;
import com.beowulfchain.beowulfj.enums.ValidationType;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.interfaces.SignatureObject;
import com.beowulfchain.beowulfj.protocol.Authority;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.security.InvalidParameterException;
import java.util.Map;

/**
 * This class contains fields that exist in all Beowulf Operations
 */
public class ContractOperation extends Operation {
    @JsonProperty("require_owners")
    protected Authority require_owners;
    @JsonProperty("scid")
    protected String scid;
    @JsonProperty("sc_operation")
    protected String sc_operation;

    /**
     * Create a new Operation object by providing the operation type.
     *
     * @param virtual Define if the operation instance is a virtual
     *                (<code>true</code>) or a market operation
     *                (<code>false</code>).
     */
    protected ContractOperation(boolean virtual) {
        super(virtual);
    }

    @Override
    public Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getRequire_owners(), PrivateKeyType.OWNER);
    }

    /**
     * Get the require_owners {@link Authority
     * Authority}.
     *
     * @return The require_owners authority.
     */
    public Authority getRequire_owners() {
        return require_owners;
    }

    /**
     * Set the require_owners {@link Authority
     * Authority}.
     *
     * @param require_owners The require_owners authority.
     */
    public void setRequire_owners(Authority require_owners) {
        this.require_owners = require_owners;
    }

    /**
     * Get the sc_operation metadata that has been added to this operation.
     *
     * @return The sc_operation metadata that has been added to this operation.
     */
    public String getSc_operation() {
        return sc_operation;
    }

    /**
     * Add sc_operation to this operation.
     *
     * @param sc_operation The contract sidechain operation.
     */
    public void setSc_operation(String sc_operation) {
        this.sc_operation = sc_operation;
    }

    public String getScid() {
        return scid;
    }

    public void setScid(String scid) {
        this.scid = scid;
    }

    @Override
    public void validate(ValidationType validationType) {
        if (!ValidationType.SKIP_VALIDATION.equals(validationType)
                && (!sc_operation.isEmpty() && !BeowulfJUtils.verifyJsonString(sc_operation))) {
            throw new InvalidParameterException("The given sc_operation metadata is no valid JSON");
        }
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        return new byte[0];
    }
}
