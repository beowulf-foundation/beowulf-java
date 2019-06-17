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
package com.beowulfchain.beowulfj.plugins.apis.database;

import com.beowulfchain.beowulfj.plugins.apis.database.models.GetPotentialSignaturesArgs;
import com.beowulfchain.beowulfj.plugins.apis.database.models.GetPotentialSignaturesReturn;
import com.beowulfchain.beowulfj.plugins.apis.database.models.SupernodeSchedule;
import com.beowulfchain.beowulfj.plugins.apis.database.models.VerifyAuthorityArgs;
import com.beowulfchain.beowulfj.plugins.apis.database.models.VerifyAuthorityReturn;
import com.beowulfchain.beowulfj.plugins.apis.database.models.GetTransactionHexReturn;
import com.beowulfchain.beowulfj.plugins.apis.database.models.DynamicGlobalProperty;
import com.beowulfchain.beowulfj.plugins.apis.database.models.GetRequiredSignaturesReturn;
import com.beowulfchain.beowulfj.plugins.apis.database.models.GetTransactionHexArgs;
import com.beowulfchain.beowulfj.plugins.apis.database.models.Config;
import com.beowulfchain.beowulfj.plugins.apis.database.models.VerifyAccountAuthorityReturn;
import com.beowulfchain.beowulfj.plugins.apis.database.models.VerifyAccountAuthorityArgs;
import com.beowulfchain.beowulfj.plugins.apis.database.models.GetRequiredSignaturesArgs;
import com.beowulfchain.beowulfj.communication.CommunicationHandler;
import com.beowulfchain.beowulfj.communication.jrpc.JsonRPCRequest;
import com.beowulfchain.beowulfj.configuration.BeowulfJConfig;
import com.beowulfchain.beowulfj.enums.BeowulfApiType;
import com.beowulfchain.beowulfj.enums.RequestMethod;
import com.beowulfchain.beowulfj.exceptions.BeowulfCommunicationException;
import com.beowulfchain.beowulfj.exceptions.BeowulfResponseException;
import com.beowulfchain.beowulfj.protocol.AccountName;
import java.util.List;


/**
 * This class implements the "database_api".
 */
public class DatabaseApi {
    /**
     * Add a private constructor to hide the implicit public one.
     */
    private DatabaseApi() {
    }

    /**
     * Get the configuration.
     *
     * @param communicationHandler
     * @return The beowulf configuration.
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
    public static Config getConfig(CommunicationHandler communicationHandler)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.DATABASE_API, RequestMethod.GET_CONFIG, null);

        return communicationHandler.performRequest(requestObject, Config.class).get(0);
    }

    /**
     * Get the global properties.
     *
     * @param communicationHandler
     * @return The dynamic global properties.
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
    public static DynamicGlobalProperty getDynamicGlobalProperties(CommunicationHandler communicationHandler)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.DATABASE_API,
                RequestMethod.GET_DYNAMIC_GLOBAL_PROPERTIES, null);

        return communicationHandler.performRequest(requestObject, DynamicGlobalProperty.class).get(0);
    }

    /**
     * Get the supernode schedule.
     *
     * @param communicationHandler
     * @return The supernode schedule.
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
    public static SupernodeSchedule getSupernodeSchedule(CommunicationHandler communicationHandler)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.DATABASE_API, RequestMethod.GET_SUPERNODE_SCHEDULE,
                null);

        return communicationHandler.performRequest(requestObject, SupernodeSchedule.class).get(0);
    }

    /**
     * Get the list of the current active supernodes.
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @return The list of the current active supernodes.
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
    public static List<AccountName> getActiveSupernodes(CommunicationHandler communicationHandler)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.DATABASE_API, RequestMethod.GET_ACTIVE_SUPERNODES,
                null);

        return communicationHandler.performRequest(requestObject, AccountName.class);
    }

    /**
     * @param communicationHandler
     * @param getTransactionHexArgs
     * @return GetTransactionHexReturn
     * @throws BeowulfCommunicationException
     * @throws BeowulfResponseException
     */
    public static GetTransactionHexReturn getTransactionHex(CommunicationHandler communicationHandler,
                                                            GetTransactionHexArgs getTransactionHexArgs) throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.DATABASE_API, RequestMethod.GET_TRANSACTION_HEX,
                getTransactionHexArgs);

        return communicationHandler.performRequest(requestObject, GetTransactionHexReturn.class).get(0);
    }

    /**
     * @param communicationHandler
     * @param getRequiredSignaturesArgs
     * @return GetRequiredSignaturesReturn
     * @throws BeowulfCommunicationException
     * @throws BeowulfResponseException
     */
    public static GetRequiredSignaturesReturn getRequiredSignatures(CommunicationHandler communicationHandler,
                                                                    GetRequiredSignaturesArgs getRequiredSignaturesArgs)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.DATABASE_API,
                RequestMethod.GET_REQUIRED_SIGNATURES, getRequiredSignaturesArgs);

        return communicationHandler.performRequest(requestObject, GetRequiredSignaturesReturn.class).get(0);
    }

    /**
     * @param communicationHandler
     * @param getPotentialSignaturesArgs
     * @return GetPotentialSignaturesReturn
     * @throws BeowulfCommunicationException
     * @throws BeowulfResponseException
     */
    public static GetPotentialSignaturesReturn getPotentialSignatures(CommunicationHandler communicationHandler,
                                                                      GetPotentialSignaturesArgs getPotentialSignaturesArgs)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.DATABASE_API,
                RequestMethod.GET_POTENTIAL_SIGNATURES, getPotentialSignaturesArgs);

        return communicationHandler.performRequest(requestObject, GetPotentialSignaturesReturn.class).get(0);
    }

    /**
     * @param communicationHandler
     * @param verifyAuthorityArgs
     * @return VerifyAuthorityReturn
     * @throws BeowulfCommunicationException
     * @throws BeowulfResponseException
     */
    public static VerifyAuthorityReturn verifyAuthority(CommunicationHandler communicationHandler,
                                                        VerifyAuthorityArgs verifyAuthorityArgs) throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.DATABASE_API, RequestMethod.VERIFY_AUTHORITY,
                verifyAuthorityArgs);

        return communicationHandler.performRequest(requestObject, VerifyAuthorityReturn.class).get(0);
    }

    /**
     * @param communicationHandler
     * @param verifyAccountAuthorityArgs
     * @return VerifyAccountAuthorityReturn
     * @throws BeowulfCommunicationException
     * @throws BeowulfResponseException
     */
    public static VerifyAccountAuthorityReturn verifyAccountAuthority(CommunicationHandler communicationHandler,
                                                                      VerifyAccountAuthorityArgs verifyAccountAuthorityArgs)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.DATABASE_API,
                RequestMethod.VERIFY_ACCOUNT_AUTHORITY, null);

        return communicationHandler.performRequest(requestObject, VerifyAccountAuthorityReturn.class).get(0);
    }
}
