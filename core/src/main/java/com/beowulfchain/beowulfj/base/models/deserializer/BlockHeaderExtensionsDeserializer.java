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
package com.beowulfchain.beowulfj.base.models.deserializer;

import com.beowulfchain.beowulfj.base.models.BlockHeaderExtensions;
import com.beowulfchain.beowulfj.base.models.HardforkVersionVote;
import com.beowulfchain.beowulfj.base.models.Version;
import com.beowulfchain.beowulfj.communication.CommunicationHandler;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public class BlockHeaderExtensionsDeserializer extends JsonDeserializer<BlockHeaderExtensions> {
    @Override
    public BlockHeaderExtensions deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.readValueAsTree();

        if (node == null || node.size() != 2 || !node.get(0).isInt()) {
            throw new IllegalArgumentException("The received JSON does not has the required structure.");
        }

        // Get the core class by mapping the given type id.
        int coreTypeId = node.get(0).asInt();
        switch (coreTypeId) {
            case 0:
                return null;
            case 1:
                return CommunicationHandler.getObjectMapper().treeToValue(node.get(1), Version.class);
            case 2:
                return CommunicationHandler.getObjectMapper().treeToValue(node.get(1), HardforkVersionVote.class);
            default:
                throw new IllegalArgumentException("Unknown extension type id '" + coreTypeId + "'.");
        }
    }
}
