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
package com.beowulfchain.beowulfj.protocol;

import com.beowulfchain.beowulfj.communication.CommunicationHandler;
import com.beowulfchain.beowulfj.configuration.BeowulfJConfig;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.interfaces.ByteTransformable;
import com.beowulfchain.beowulfj.protocol.operations.BaseOperation;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.joou.UInteger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AssetInfo extends BaseOperation implements ByteTransformable {
    private UInteger decimals;
    private String name;

    public AssetInfo() {
    }

    public AssetInfo(String name, UInteger decimals) throws Exception {
        this.setName(name);
        this.setDecimals(decimals);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if (name.length() > 8) {
            throw new Exception("number character of asset name must lower than 8");
        }
        this.name = name.toUpperCase();
    }

    public UInteger getDecimals() {
        return decimals;
    }

    public void setDecimals(UInteger decimals) throws Exception {
        if (decimals.intValue() > 5) {
            throw new Exception("decimals must lower than 5");
        }
        this.decimals = decimals;
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedSymbol = new ByteArrayOutputStream(12)) {

            // serialize 4 bytes decimals
            serializedSymbol.write(BeowulfJUtils.transformIntToByteArray(this.getDecimals().intValue()));

            // serialize name with 8 bytes
            serializedSymbol
                    .write(this.getName().toUpperCase().getBytes(BeowulfJConfig.getInstance().getEncodingCharset()));
            for (int i = this.getName().length(); i < 9; i++) {
                serializedSymbol.write(0x00);
            }

            return serializedSymbol.toByteArray();
        } catch (IOException e) {
            throw new BeowulfInvalidTransactionException(
                    "A problem occured while transforming the symbol into a byte array.", e);
        }
    }

    public String toJson() throws JsonProcessingException {
        return CommunicationHandler.getObjectMapper().writeValueAsString(this);
    }
}
