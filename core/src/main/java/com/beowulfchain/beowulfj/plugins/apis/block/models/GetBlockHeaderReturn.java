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
package com.beowulfchain.beowulfj.plugins.apis.block.models;

import com.beowulfchain.beowulfj.protocol.BlockHeader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class is the java implementation of the Beowulf "get_block_header_return"
 * object.
 */
public class GetBlockHeaderReturn {
    @JsonProperty("header")
    private BlockHeader header;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private GetBlockHeaderReturn() {
    }

    /**
     * @return the header
     */
    public Optional<BlockHeader> getHeader() {
        return Optional.fromNullable(header);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
