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
package com.beowulfchain.beowulfj.plugins.apis.account.by.key.models;

import com.beowulfchain.beowulfj.communication.CommunicationHandler;
import com.beowulfchain.beowulfj.plugins.apis.block.BlockApi;
import com.beowulfchain.beowulfj.plugins.apis.block.models.GetBlockHeaderArgs;
import com.beowulfchain.beowulfj.protocol.PublicKey;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;
import java.util.Arrays;
import java.util.List;

/**
 * This class is the java implementation of the Beowulf "get_key_references_args"
 * object.
 */
public class GetKeyReferencesArgs {
    private List<PublicKey> keys;

    /**
     * Create a new {@link GetBlockHeaderArgs} instance to be passed to the getBlock method.
     *
     * @param blockNumber The block number to search for.
     */
    @JsonCreator()
    public GetKeyReferencesArgs(@JsonProperty("keys") List<PublicKey> keys) {
        this.setKeys(keys);
    }

    public GetKeyReferencesArgs(PublicKey key) {
        this.setKeys(Arrays.asList(key));
    }

    /**
     * @return the keys
     */
    public List<PublicKey> getKeys() {
        return keys;
    }

    /**
     * @param keys the keys to set
     */
    public void setKeys(List<PublicKey> keys) {
        this.keys = keys;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
