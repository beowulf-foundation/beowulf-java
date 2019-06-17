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
package com.beowulfchain.beowulfj.plugins.apis.condenser.models.deserializer;

import com.beowulfchain.beowulfj.communication.CommunicationHandler;
import com.beowulfchain.beowulfj.plugins.apis.account.history.models.AppliedOperation;
import com.beowulfchain.beowulfj.plugins.apis.condenser.models.AccountHistoryReturn;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.joou.UInteger;

import java.io.IOException;

public class AccountHistoryDeserializer extends JsonDeserializer<AccountHistoryReturn> {

    @Override
    public AccountHistoryReturn deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonToken currentToken = jsonParser.currentToken();
        if (JsonToken.START_ARRAY.equals(currentToken)) {
            ObjectCodec codec = jsonParser.getCodec();
            TreeNode rootNode = codec.readTree(jsonParser);

            if (rootNode.isArray()) {
                ArrayNode arrayNode = (ArrayNode) rootNode;
                AccountHistoryReturn historyReturn = new AccountHistoryReturn();
                historyReturn.setSequence(UInteger.valueOf(arrayNode.get(0).asLong()));
                historyReturn.setAppliedOperation(CommunicationHandler.getObjectMapper().treeToValue(arrayNode.get(1), AppliedOperation.class));
                return historyReturn;
            }
        }
        throw new IllegalArgumentException("Found '" + currentToken + "' instead of '" + JsonToken.START_ARRAY + "'.");
    }
}
