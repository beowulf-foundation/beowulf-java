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
package com.beowulfchain.beowulfj;

import com.beowulfchain.beowulfj.configuration.BeowulfJConfig;
import com.beowulfchain.beowulfj.enums.SynchronizationType;
import com.beowulfchain.beowulfj.exceptions.BeowulfCommunicationException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public abstract class BaseIT extends BaseTest {
    protected static BeowulfJConfig config;
    protected static BeowulfJ beowulfJ;

    /**
     * Prepare a the environment for standard integration tests.
     */
    protected static void setupIntegrationTestEnvironment() {
        config = BeowulfJConfig.getNewInstance();
        config.setResponseTimeout(0);
        config.setSynchronizationLevel(SynchronizationType.PROPERTIES_ONLY);

        try {
            beowulfJ = BeowulfJ.getInstance();
        } catch (BeowulfCommunicationException e) {
            throw new RuntimeException("Could not create a BeowulfJ instance. - Test execution stopped.", e);
        }
    }

    /**
     * Call this method in case the tests should be fired against a WebSocket
     * endpoint instead of using the default HTTPS endpoint.
     *
     * @throws URISyntaxException If the URL is wrong.
     */
    public static void configureBeowulfWebSocketEndpoint() throws URISyntaxException {
        ArrayList<Pair<URI, Boolean>> endpoints = new ArrayList<>();

        ImmutablePair<URI, Boolean> webSocketEndpoint;
        webSocketEndpoint = new ImmutablePair<>(new URI("https://testnet-bw.beowulfchain.com/rpc"), true);

        endpoints.add(webSocketEndpoint);
        config.setEndpointURIs(endpoints);
    }

    /**
     * Call this method in case the tests should be fired against the TestNet
     * endpoint instead of using the default HTTPS endpoint.
     *
     * @throws URISyntaxException If the URL is wrong.
     */
    public static void configureTestNetHttpEndpoint() throws URISyntaxException {
        ArrayList<Pair<URI, Boolean>> endpoints = new ArrayList<>();

        ImmutablePair<URI, Boolean> webSocketEndpoint;
        webSocketEndpoint = new ImmutablePair<>(new URI("https://testnet-bw.beowulfchain.com/rpc"), true);

        endpoints.add(webSocketEndpoint);
        config.setEndpointURIs(endpoints);
    }

    /**
     * Call this method in case the tests should be fired against the TestNet
     * endpoint using the WebSocket protocol..
     *
     * @throws URISyntaxException If the URL is wrong.
     */
    public static void configureTestNetWebsocketEndpoint() throws URISyntaxException {
        ArrayList<Pair<URI, Boolean>> endpoints = new ArrayList<>();

        ImmutablePair<URI, Boolean> webSocketEndpoint;
        webSocketEndpoint = new ImmutablePair<>(new URI("https://testnet-bw.beowulfchain.com/rpc"), true);

        endpoints.add(webSocketEndpoint);
        config.setEndpointURIs(endpoints);
    }
}
