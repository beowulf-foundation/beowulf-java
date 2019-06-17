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
package com.beowulfchain.beowulfj.plugins.apis.account.by.key;

import com.beowulfchain.beowulfj.communication.CommunicationHandler;
import com.beowulfchain.beowulfj.communication.jrpc.JsonRPCRequest;
import com.beowulfchain.beowulfj.configuration.BeowulfJConfig;
import com.beowulfchain.beowulfj.enums.BeowulfApiType;
import com.beowulfchain.beowulfj.enums.RequestMethod;
import com.beowulfchain.beowulfj.exceptions.BeowulfCommunicationException;
import com.beowulfchain.beowulfj.exceptions.BeowulfResponseException;
import com.beowulfchain.beowulfj.plugins.apis.account.by.key.models.GetKeyReferencesArgs;
import com.beowulfchain.beowulfj.plugins.apis.account.by.key.models.GetKeyReferencesReturn;

/**
 * This class implements the account by key api.
 */
@Deprecated
public class AccountByKeyApi {
    /**
     * Add a private constructor to hide the implicit public one.
     */
    private AccountByKeyApi() {
    }

    /**
     * Search for users under the use of their public key(s).
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param publicKeys           An array containing one or more public keys.
     * @return A list of arrays containing the matching account names.
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
    public static GetKeyReferencesReturn getKeyReferences(CommunicationHandler communicationHandler,
                                                          GetKeyReferencesArgs publicKeys) throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.ACCOUNT_BY_KEY_API,
                RequestMethod.GET_KEY_REFERENCES, publicKeys);

        return communicationHandler.performRequest(requestObject, GetKeyReferencesReturn.class).get(0);
    }
}
