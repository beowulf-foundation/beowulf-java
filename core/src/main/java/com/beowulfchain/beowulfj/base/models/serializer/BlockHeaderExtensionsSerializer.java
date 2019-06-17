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
package com.beowulfchain.beowulfj.base.models.serializer;

import com.beowulfchain.beowulfj.base.models.BlockHeaderExtensions;
import com.beowulfchain.beowulfj.base.models.CommentPayoutBeneficiaries;
import com.beowulfchain.beowulfj.base.models.HardforkVersionVote;
import com.beowulfchain.beowulfj.base.models.Version;
import com.beowulfchain.beowulfj.enums.BlockHeaderExtentsionsType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import java.io.IOException;

public class BlockHeaderExtensionsSerializer extends JsonSerializer<BlockHeaderExtensions> {

    @Override
    public void serialize(BlockHeaderExtensions blockHeaderExtensions, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        JavaType javaType = serializerProvider.constructType(CommentPayoutBeneficiaries.class);
        BeanDescription beanDesc = serializerProvider.getConfig().introspect(javaType);
        JsonSerializer<Object> serializer = BeanSerializerFactory.instance.findBeanSerializer(serializerProvider,
                javaType, beanDesc);

        jsonGenerator.writeStartArray();

        if (blockHeaderExtensions instanceof Version) {
            jsonGenerator.writeNumber(BlockHeaderExtentsionsType.VERSION.ordinal());
            serializer.serialize((Version) blockHeaderExtensions, jsonGenerator, serializerProvider);
        } else if (blockHeaderExtensions instanceof HardforkVersionVote) {
            jsonGenerator.writeNumber(BlockHeaderExtentsionsType.HARDFORK_VERSION_VOTE.ordinal());
            serializer.serialize((HardforkVersionVote) blockHeaderExtensions, jsonGenerator, serializerProvider);
        } else {
            throw new IllegalArgumentException(
                    "Unknown extension type class '" + blockHeaderExtensions.getClass().getSimpleName() + "'.");
        }

        jsonGenerator.writeEndArray();
    }
}
