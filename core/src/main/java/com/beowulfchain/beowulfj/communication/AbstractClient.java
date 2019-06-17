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
package com.beowulfchain.beowulfj.communication;

import com.beowulfchain.beowulfj.communication.jrpc.JsonRPCRequest;
import com.beowulfchain.beowulfj.communication.jrpc.JsonRPCResponse;
import com.beowulfchain.beowulfj.exceptions.BeowulfCommunicationException;
import com.beowulfchain.beowulfj.exceptions.BeowulfResponseException;
import java.io.IOException;
import java.net.URI;

public abstract class AbstractClient {
    /**
     * Use this method to send a <code>requestObject</code> to the
     * <code>endpointUri</code> and to receive an answer.
     *
     * @param requestObject           The object to send.
     * @param endpointUri             The endpoint to connect and send to.
     * @param sslVerificationDisabled Define if the SSL verification should be disabled.
     * @return The response returned by the Beowulf Node wrapped in a
     * {@link JsonRPCResponse} object.
     * @throws BeowulfCommunicationException In case of communication problems.
     * @throws BeowulfResponseException      If the answer received from the node is no valid JSON.
     */
    public abstract JsonRPCResponse invokeAndReadResponse(JsonRPCRequest requestObject, URI endpointUri,
                                                          boolean sslVerificationDisabled) throws BeowulfCommunicationException, BeowulfResponseException;

    /**
     * Use this method to close the connection of this client.
     *
     * @throws IOException If the connection can't be closed.
     */
    public abstract void closeConnection() throws IOException;
}
