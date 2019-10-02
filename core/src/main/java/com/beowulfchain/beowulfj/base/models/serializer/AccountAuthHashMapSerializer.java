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

import com.beowulfchain.beowulfj.protocol.AccountName;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

public class AccountAuthHashMapSerializer extends JsonSerializer<Map<AccountName, Integer>> {

    @Override
    public void serialize(Map<AccountName, Integer> accountAuthMap, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        for (Entry<AccountName, Integer> accountAuth : accountAuthMap.entrySet()) {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeString(accountAuth.getKey().getName());
            jsonGenerator.writeNumber(accountAuth.getValue());
            jsonGenerator.writeEndArray();
        }
        jsonGenerator.writeEndArray();
    }
}
