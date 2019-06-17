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

import com.beowulfchain.beowulfj.protocol.Asset;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Locale;

public class AssetSerializer extends JsonSerializer<Asset> {

    @Override
    public void serialize(Asset asset, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        //todo: edit serialize for name token
        String amountFormat = "%." + asset.getPrecision().intValue() + "f";
        jsonGenerator.writeString(String.format(Locale.US, amountFormat, asset.toReal()) + " "
                + asset.getName().toUpperCase());
    }
}
