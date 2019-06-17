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
import com.beowulfchain.beowulfj.configuration.BeowulfJConfig;
import com.beowulfchain.beowulfj.exceptions.BeowulfCommunicationException;
import com.beowulfchain.beowulfj.exceptions.BeowulfResponseException;
import com.beowulfchain.beowulfj.exceptions.BeowulfTimeoutException;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;
import org.glassfish.tyrus.client.SslContextConfigurator;
import org.glassfish.tyrus.client.SslEngineConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WebsocketClient extends AbstractClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketClient.class);

    /**
     * The client.
     */
    private ClientManager client;
    /**
     * A {@link CountDownLatch} used to indicate that a message is expected.
     */
    private CountDownLatch responseCountDownLatch;
    /**
     * The current session.
     */
    private Session session;
    /**
     * The {@link WebsocketEndpoint} instance that will handle the incoming
     * messages.
     */
    private WebsocketEndpoint websocketEndpoint;
    /**
     * The endpoint this client instance is currently connected to.
     */
    private URI currentEndpointUri;

    /**
     * Initialize a new Websocket Client.
     */
    public WebsocketClient() {
        // Initialize fields.
        this.client = ClientManager.createClient();

        this.client.setDefaultMaxSessionIdleTimeout(BeowulfJConfig.getInstance().getIdleTimeout());
        this.client.getProperties().put(ClientProperties.RECONNECT_HANDLER, new WebsocketReconnectHandler());

        this.websocketEndpoint = new WebsocketEndpoint(this);
        this.responseCountDownLatch = new CountDownLatch(1);
    }

    @Override
    public JsonRPCResponse invokeAndReadResponse(JsonRPCRequest requestObject, URI endpointUri,
                                                 boolean sslVerificationDisabled) throws BeowulfCommunicationException, BeowulfResponseException {
        if (session == null || !session.isOpen() || currentEndpointUri == null
                || !currentEndpointUri.equals(endpointUri)) {
            connect(endpointUri, sslVerificationDisabled);
            // "Save" the URI we are currently connected to.
            currentEndpointUri = endpointUri;
        }

        responseCountDownLatch = new CountDownLatch(1);

        try {
            String request = requestObject.toJson();
            LOGGER.debug("Sending {}.", request);
            session.getBasicRemote().sendObject(request);
        } catch (IOException | EncodeException e) {
            // Throw an Exception and let the CommunicationHandler handle the
            // reconnect to another node.
            throw new BeowulfCommunicationException("Could not transfer the data to the Beowulf Node. - Reconnecting.", e);
        }

        try {
            // Wait until we received a response from the Server.
            do {
                if (BeowulfJConfig.getInstance().getResponseTimeout() == 0) {
                    responseCountDownLatch.await();
                } else {
                    if (!responseCountDownLatch.await(BeowulfJConfig.getInstance().getResponseTimeout(),
                            TimeUnit.MILLISECONDS)) {
                        throw new BeowulfTimeoutException(
                                "Timeout occured. The WebSocket server was not able to answer in "
                                        + BeowulfJConfig.getInstance().getResponseTimeout() + " millisecond(s).");
                    }
                }
            } while (websocketEndpoint.getLatestResponse() == null);
        } catch (InterruptedException e) {
            LOGGER.warn("Thread has been interrupted.", e);
            Thread.currentThread().interrupt();
        }

        return websocketEndpoint.getLatestResponse();
    }

    @Override
    public void closeConnection() throws IOException {
        if (session != null && session.isOpen()) {
            LOGGER.debug("Closing existing session.");
            session.close();
        }
    }

    /**
     * Get the {@link CountDownLatch} used by this instance to change its
     * counter.
     *
     * @return The {@link CountDownLatch} used by this instance.
     */
    protected CountDownLatch getResponseCountDownLatch() {
        return this.responseCountDownLatch;
    }

    /**
     * Get the current {@link Session}.
     *
     * @return The session used by this instance.
     */
    protected Session getSession() {
        return session;
    }

    /**
     * Update the {@link Session} this instance should use.
     *
     * @param session The session to set.
     */
    protected void setSession(Session session) {
        this.session = session;
    }

    /**
     * This method establishes a new connection to the web socket Server.
     *
     * @throws BeowulfCommunicationException
     */
    private synchronized void connect(URI endpointURI, boolean sslVerificationDisabled)
            throws BeowulfCommunicationException {
        // Tyrus expects a SSL connection if the SSL_ENGINE_CONFIGURATOR
        // property is present. This leads to a "connection failed" error when
        // a non SSL secured protocol is used. Due to this we only add the
        // property when connecting to a SSL secured node.
        if (sslVerificationDisabled && endpointURI.getScheme().equals("wss")) {
            SslEngineConfigurator sslEngineConfigurator = new SslEngineConfigurator(new SslContextConfigurator());
            sslEngineConfigurator.setHostnameVerifier(new HostnameVerifier() {
                // Could be "sslEngineConfigurator.setHostnameVerifier((String
                // host, SSLSession sslSession) -> true);" when Java 8 is used.
                @Override
                public boolean verify(String host, SSLSession sslSession) {
                    return true;
                }
            });

            client.getProperties().put(ClientProperties.SSL_ENGINE_CONFIGURATOR, sslEngineConfigurator);
        }

        try {
            // Close the current session in case it is still open.
            closeConnection();

            LOGGER.info("Connecting to {}.", endpointURI);

            session = client.connectToServer(websocketEndpoint, BeowulfJConfig.getInstance().getClientEndpointConfig(),
                    endpointURI);
        } catch (DeploymentException | IOException e) {
            // Throw an Exception and let the CommunicationHandler handle the
            // reconnect to another node.
            throw new BeowulfCommunicationException("Could not connect to the node - Trying to reconnect.", e);
        }
    }
}
