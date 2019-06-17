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
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;

public class HttpClient extends AbstractClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);

    @Override
    public JsonRPCResponse invokeAndReadResponse(JsonRPCRequest requestObject, URI endpointUri,
                                                 boolean sslVerificationDisabled) throws BeowulfCommunicationException {
        try {
            NetHttpTransport.Builder builder = new NetHttpTransport.Builder();
            // Disable SSL verification if needed
            if (sslVerificationDisabled && endpointUri.getScheme().equals("https")) {
                builder.doNotValidateCertificate();
            }

            String requestPayload = requestObject.toJson();
            HttpRequest httpRequest = builder.build().createRequestFactory(new HttpClientRequestInitializer())
                    .buildPostRequest(new GenericUrl(endpointUri),
                            ByteArrayContent.fromString("application/json", requestPayload));

            LOGGER.debug("Sending {}.", requestPayload);

            HttpResponse httpResponse = httpRequest.execute();

            int status = httpResponse.getStatusCode();
            String responsePayload = httpResponse.parseAsString();

            if (status >= 200 && status < 300 && responsePayload != null) {
                return new JsonRPCResponse(CommunicationHandler.getObjectMapper().readTree(responsePayload));
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }

        } catch (GeneralSecurityException | IOException e) {
            throw new BeowulfCommunicationException("A problem occured while processing the request.", e);
        }
    }

    @Override
    public void closeConnection() {
        // A connection is used for exactly one request and closed afterwards.
        // Due to that there is no need to close it here.
    }
}
