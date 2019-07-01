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
package com.beowulfchain.beowulfj.plugins.apis.account.history;

import com.beowulfchain.beowulfj.communication.CommunicationHandler;
import com.beowulfchain.beowulfj.communication.jrpc.JsonRPCRequest;
import com.beowulfchain.beowulfj.configuration.BeowulfJConfig;
import com.beowulfchain.beowulfj.enums.BeowulfApiType;
import com.beowulfchain.beowulfj.enums.RequestMethod;
import com.beowulfchain.beowulfj.exceptions.BeowulfCommunicationException;
import com.beowulfchain.beowulfj.exceptions.BeowulfResponseException;
import com.beowulfchain.beowulfj.plugins.apis.account.history.models.GetAccountHistoryArgs;
import com.beowulfchain.beowulfj.plugins.apis.account.history.models.GetAccountHistoryReturn;
import com.beowulfchain.beowulfj.protocol.AnnotatedSignedTransaction;

/**
 * This class implements the "account_history_api".
 */
@Deprecated
public class AccountHistoryApi {
    /**
     * Add a private constructor to hide the implicit public one.
     */
    private AccountHistoryApi() {
    }

    /**
     * Find a transaction by its <code>transactionId</code>.
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param getTransactionArgs        The <code>transactionId</code> to search for.
     * @return A sequence of operations included/generated within a particular
     * block.
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
    public static AnnotatedSignedTransaction getTransaction(CommunicationHandler communicationHandler,
                                                            GetAccountHistoryArgs getTransactionArgs) throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.ACCOUNT_HISTORY_API,
                RequestMethod.GET_TRANSACTION, getTransactionArgs);

        return communicationHandler.performRequest(requestObject, AnnotatedSignedTransaction.class).get(0);
    }

    /**
     * Get all operations performed by the specified <code>accountName</code>.
     *
     * @param communicationHandler  A
     *                              {@link CommunicationHandler
     *                              CommunicationHandler} instance that should be used to send the
     *                              request.
     * @param getAccountHistoryArgs get account history param include [name, start, limit]
     * @return A map containing the activities. The key is the id of the
     * activity.
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
    public static GetAccountHistoryReturn getAccountHistory(CommunicationHandler communicationHandler,
                                                            GetAccountHistoryArgs getAccountHistoryArgs) throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.ACCOUNT_HISTORY_API,
                RequestMethod.GET_ACCOUNT_HISTORY, getAccountHistoryArgs);

        return communicationHandler.performRequest(requestObject, GetAccountHistoryReturn.class).get(0);
    }
}
