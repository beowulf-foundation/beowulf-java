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
package com.beowulfchain.beowulfj.plugins.apis.block;

import com.beowulfchain.beowulfj.communication.CommunicationHandler;
import com.beowulfchain.beowulfj.communication.jrpc.JsonRPCRequest;
import com.beowulfchain.beowulfj.configuration.BeowulfJConfig;
import com.beowulfchain.beowulfj.enums.BeowulfApiType;
import com.beowulfchain.beowulfj.enums.RequestMethod;
import com.beowulfchain.beowulfj.exceptions.BeowulfCommunicationException;
import com.beowulfchain.beowulfj.exceptions.BeowulfResponseException;
import com.beowulfchain.beowulfj.plugins.apis.block.models.GetBlockArgs;
import com.beowulfchain.beowulfj.plugins.apis.block.models.GetBlockHeaderArgs;
import com.beowulfchain.beowulfj.plugins.apis.block.models.GetBlockHeaderReturn;
import com.beowulfchain.beowulfj.plugins.apis.block.models.GetBlockReturn;

/**
 * This class implements the "block_api".
 */
@Deprecated
public class BlockApi {
    /**
     * Add a private constructor to hide the implicit public one.
     */
    private BlockApi() {
    }

    /**
     * Like {@link #getBlock(CommunicationHandler, GetBlockArgs)}, but will only
     * return the header of the requested block instead of the full, signed one.
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param getBlockHeaderArgs   TODO
     * @return The referenced full, signed block, or <code>null</code> if no
     * matching block was found.
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
    public static GetBlockHeaderReturn getBlockHeader(CommunicationHandler communicationHandler,
                                                      GetBlockHeaderArgs getBlockHeaderArgs) throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.BLOCK_API, RequestMethod.GET_BLOCK_HEADER,
                getBlockHeaderArgs);

        return communicationHandler.performRequest(requestObject, GetBlockHeaderReturn.class).get(0);
    }

    /**
     * Get a full, signed block by providing its <code>blockNumber</code>. The
     * returned object contains all information related to the block (e.g.
     * processed transactions, the supernode and the creation time).
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param getBlockArgs         Height of the block to be returned.
     * @return The referenced full, signed block, or <code>null</code> if no
     * matching block was found.
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
    public static GetBlockReturn getBlock(CommunicationHandler communicationHandler, GetBlockArgs getBlockArgs)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.BLOCK_API, RequestMethod.GET_BLOCK,
                getBlockArgs);

        return communicationHandler.performRequest(requestObject, GetBlockReturn.class).get(0);
    }
}
