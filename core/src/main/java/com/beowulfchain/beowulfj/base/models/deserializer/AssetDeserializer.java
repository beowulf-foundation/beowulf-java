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

import com.beowulfchain.beowulfj.chain.smt.objects.AssetObject;
import com.beowulfchain.beowulfj.exceptions.BeowulfCommunicationException;
import com.beowulfchain.beowulfj.exceptions.BeowulfResponseException;
import com.beowulfchain.beowulfj.protocol.Asset;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joou.UInteger;

import java.io.IOException;
import java.math.BigDecimal;


public class AssetDeserializer extends JsonDeserializer<Asset> {
    @Override
    public Asset deserialize(JsonParser jasonParser, DeserializationContext deserializationContext) throws IOException {
        JsonToken currentToken = jasonParser.currentToken();
        if (JsonToken.START_OBJECT.equals(currentToken)) {
            AssetObject assetObject = jasonParser.readValueAs(AssetObject.class);
            return new Asset(assetObject.getAmount(), assetObject.getName(), UInteger.valueOf(assetObject.getPrecision()));
        } else if (JsonToken.VALUE_STRING.equals(currentToken)) {
            String[] assetFields = jasonParser.getText().split(" ");
            if (assetFields.length == 2) {
                try {
                    return Asset.createAsset(new BigDecimal(assetFields[0]), assetFields[1]);
                } catch (BeowulfResponseException | BeowulfCommunicationException e) {
                    e.printStackTrace();
                }
            }
        }

        throw new IllegalArgumentException("Found '" + currentToken + "' instead of '" + JsonToken.VALUE_STRING + "'.");
    }
}
