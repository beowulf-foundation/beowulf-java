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
package com.beowulfchain.beowulfj.plugins.apis.network.broadcast.api;

import com.beowulfchain.beowulfj.chain.SignedTransaction;
import com.beowulfchain.beowulfj.communication.CommunicationHandler;
import com.beowulfchain.beowulfj.communication.jrpc.JsonRPCRequest;
import com.beowulfchain.beowulfj.configuration.BeowulfJConfig;
import com.beowulfchain.beowulfj.enums.BeowulfApiType;
import com.beowulfchain.beowulfj.enums.RequestMethod;
import com.beowulfchain.beowulfj.exceptions.BeowulfCommunicationException;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.exceptions.BeowulfResponseException;
import com.beowulfchain.beowulfj.plugins.apis.network.broadcast.models.BroadcastTransactionSynchronousReturn;
import com.beowulfchain.beowulfj.protocol.SignedBlock;

/**
 * This class implements the network broadcast api which is required to send
 * transactions.
 */
@Deprecated
public class NetworkBroadcastApi {
    /**
     * Add a private constructor to hide the implicit public one.
     */
    private NetworkBroadcastApi() {
    }

    /**
     * Broadcast a transaction on the Beowulf blockchain. This method will
     * validate the transaction and return immediately. Please notice that this
     * does not mean that the operation has been accepted and has been
     * processed. If you want to make sure that this is the case use the
     * {@link #broadcastTransactionSynchronous(CommunicationHandler, SignedTransaction)}
     * method.
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param transaction          The {@link SignedTransaction} object to broadcast.
     * @throws BeowulfCommunicationException      <ul>
     *                                            <li>If the server was not able to answer the request in the
     *                                            given time (see
     *                                            {@link BeowulfJConfig#setResponseTimeout(int)
     *                                            setResponseTimeout}).</li>
     *                                            <li>If there is a connection problem.</li>
     *                                            </ul>
     * @throws BeowulfResponseException           <ul>
     *                                            <li>If the BeowulfJ is unable to transform the JSON response
     *                                            into a Java object.</li>
     *                                            <li>If the Server returned an error object.</li>
     *                                            </ul>
     * @throws BeowulfInvalidTransactionException In case the provided transaction is not valid.
     */
    public static void broadcastTransaction(CommunicationHandler communicationHandler, SignedTransaction transaction)
            throws BeowulfCommunicationException, BeowulfResponseException, BeowulfInvalidTransactionException {
        if (transaction.getSignatures() == null || transaction.getSignatures().isEmpty()) {
            transaction.sign();
        }

        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.NETWORK_BROADCAST_API,
                RequestMethod.BROADCAST_TRANSACTION, transaction);

        communicationHandler.performRequest(requestObject, Object.class);
    }

    /**
     * Broadcast a transaction on the Beowulf blockchain. This method will
     * validate the transaction and return after it has been accepted and
     * applied.
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param transaction          The {@link SignedTransaction} object to broadcast.
     * @return A {@link BroadcastTransactionSynchronousReturn} object providing
     * information about the block in which the transaction has been
     * applied.
     * @throws BeowulfCommunicationException      <ul>
     *                                            <li>If the server was not able to answer the request in the
     *                                            given time (see
     *                                            {@link BeowulfJConfig#setResponseTimeout(int)
     *                                            setResponseTimeout}).</li>
     *                                            <li>If there is a connection problem.</li>
     *                                            </ul>
     * @throws BeowulfResponseException           <ul>
     *                                            <li>If the BeowulfJ is unable to transform the JSON response
     *                                            into a Java object.</li>
     *                                            <li>If the Server returned an error object.</li>
     *                                            </ul>
     * @throws BeowulfInvalidTransactionException In case the provided transaction is not valid.
     */
    public static BroadcastTransactionSynchronousReturn broadcastTransactionSynchronous(
            CommunicationHandler communicationHandler, SignedTransaction transaction)
            throws BeowulfCommunicationException, BeowulfResponseException, BeowulfInvalidTransactionException {
        if (transaction.getSignatures() == null || transaction.getSignatures().isEmpty()) {
            transaction.sign();
        }

        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.NETWORK_BROADCAST_API,
                RequestMethod.BROADCAST_TRANSACTION_SYNCHRONOUS, transaction);

        return communicationHandler.performRequest(requestObject, BroadcastTransactionSynchronousReturn.class).get(0);
    }

    /**
     * Broadcast a whole block.
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param signedBlock          The {@link SignedBlock} object to broadcast.
     * @throws BeowulfCommunicationException <ul>
     *                                       <li>If the server was not able to answer the request in the
     *                                       given time (see
     *                                       {@link BeowulfJConfig#setResponseTimeout(int)
     *                                       setResponseTimeout}).</li>
     *                                       <li>If there is a connection problem.</li>
     *                                       </ul>
     * @throws BeowulfResponseException      <ul>
     *                                       <li>If the BeowulfJ is unable to transform the JSON response
     *                                       into a Java object.</li>
     *                                       <li>If the Server returned an error object.</li>
     *                                       </ul>
     */
    public static void broadcastBlock(CommunicationHandler communicationHandler, SignedBlock signedBlock)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.NETWORK_BROADCAST_API,
                RequestMethod.BROADCAST_BLOCK, signedBlock);

        communicationHandler.performRequest(requestObject, Object.class);
    }
}
