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

import com.beowulfchain.beowulfj.configuration.BeowulfJConfig;
import org.glassfish.tyrus.client.ClientManager.ReconnectHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.websocket.CloseReason;

public class WebsocketReconnectHandler extends ReconnectHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketReconnectHandler.class);

    @Override
    public boolean onDisconnect(CloseReason closeReason) {
        LOGGER.debug("The connection has been closed (Code: {}, Reason: {}).", closeReason.getCloseCode(),
                closeReason.getReasonPhrase());

        if (BeowulfJConfig.getInstance().getIdleTimeout() <= 0) {
            LOGGER.info(
                    "The connection has been closed, but BeowulfJ is configured to never close the conenction. Initiating reconnect.");
            return true;
        }

        return false;
    }

    @Override
    public boolean onConnectFailure(Exception exception) {
        LOGGER.info("The connection has been closed due to a failure.");
        LOGGER.debug("Reason: ", exception);

        // Do not reconnect in case of failure. The CommunicationHandler will
        // take care of changing the node and establishing a new connection.
        return false;
    }

    @Override
    public long getDelay() {
        return 0;
    }
}
