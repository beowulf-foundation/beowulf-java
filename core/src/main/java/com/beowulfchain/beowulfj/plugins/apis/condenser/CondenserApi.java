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
package com.beowulfchain.beowulfj.plugins.apis.condenser;

import com.beowulfchain.beowulfj.base.models.Account;
import com.beowulfchain.beowulfj.base.models.Block;
import com.beowulfchain.beowulfj.base.models.ScheduledHardfork;
import com.beowulfchain.beowulfj.chain.CompletedTransaction;
import com.beowulfchain.beowulfj.chain.SignedTransaction;
import com.beowulfchain.beowulfj.communication.CommunicationHandler;
import com.beowulfchain.beowulfj.communication.jrpc.JsonRPCRequest;
import com.beowulfchain.beowulfj.configuration.BeowulfJConfig;
import com.beowulfchain.beowulfj.enums.BeowulfApiType;
import com.beowulfchain.beowulfj.enums.RequestMethod;
import com.beowulfchain.beowulfj.exceptions.BeowulfCommunicationException;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.exceptions.BeowulfResponseException;
import com.beowulfchain.beowulfj.plugins.apis.condenser.models.AccountHistoryReturn;
import com.beowulfchain.beowulfj.plugins.apis.condenser.models.ExtendedAccount;
import com.beowulfchain.beowulfj.plugins.apis.condenser.models.ExtendedDynamicGlobalProperties;
import com.beowulfchain.beowulfj.plugins.apis.condenser.models.FindSmtTokenByName;
import com.beowulfchain.beowulfj.plugins.apis.database.DatabaseApi;
import com.beowulfchain.beowulfj.plugins.apis.database.models.Config;
import com.beowulfchain.beowulfj.plugins.apis.database.models.Supernode;
import com.beowulfchain.beowulfj.plugins.apis.database.models.SupernodeSchedule;
import com.beowulfchain.beowulfj.plugins.apis.network.broadcast.models.BroadcastTransactionSynchronousReturn;
import com.beowulfchain.beowulfj.protocol.*;
import org.joou.UInteger;
import org.joou.ULong;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class implements the "condenser_api".
 */
public class CondenserApi {
    /**
     * Add a private constructor to hide the implicit public one.
     */
    private CondenserApi() {
    }

    /**
     * Like
     * {@link DatabaseApi#getDynamicGlobalProperties(CommunicationHandler)}, but
     * returns an {@link ExtendedDynamicGlobalProperties} object providing
     * additional information.
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @return ExtendedDynamicGlobalProperties
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
    public static ExtendedDynamicGlobalProperties getDynamicGlobalProperties(CommunicationHandler communicationHandler)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API,
                RequestMethod.GET_DYNAMIC_GLOBAL_PROPERTIES, Collections.emptyList());

        return communicationHandler.performRequest(requestObject, ExtendedDynamicGlobalProperties.class).get(0);
    }

    /**
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param accounts             List accounts name.
     * @return List ExtendedAccount
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
    public static List<ExtendedAccount> getExtAccounts(CommunicationHandler communicationHandler, List<AccountName> accounts)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API, RequestMethod.GET_ACCOUNTS,
                Collections.singletonList(accounts)
        );

        return communicationHandler.performRequest(requestObject, ExtendedAccount.class);
    }

    /**
     * Get the hardfork version the node you are connected to is using.
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @return The hardfork version that the connected node is running on.
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
    public static String getHardforkVersion(CommunicationHandler communicationHandler)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API, RequestMethod.GET_HARDFORK_VERSION,
                Collections.emptyList());

        return communicationHandler.performRequest(requestObject, String.class).get(0);
    }

    /**
     * (get_block_header)
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param blockNum             The block number.
     * @return BlockHeader
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
    public static BlockHeader getBlockHeader(CommunicationHandler communicationHandler, long blockNum)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API, RequestMethod.GET_BLOCK_HEADER,
                Collections.singletonList(blockNum));

        return communicationHandler.performRequest(requestObject, BlockHeader.class).get(0);
    }

    /**
     * (get_block)
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param blockNum             The block number.
     * @return Block
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
    public static Block getBlock(CommunicationHandler communicationHandler, long blockNum)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API, RequestMethod.GET_BLOCK,
                Collections.singletonList(blockNum));

        return communicationHandler.performRequest(requestObject, Block.class).get(0);
    }

    /**
     * (get_active_supernodes)
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @return List AccountName The block number.
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
    public static List<AccountName> getActiveSuperNodes(CommunicationHandler communicationHandler)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API, RequestMethod.GET_ACTIVE_SUPERNODES,
                Collections.emptyList());

        return communicationHandler.performRequest(requestObject, AccountName.class);
    }

    /**
     * (get_config)
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @return Config
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
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API, RequestMethod.GET_CONFIG,
                Collections.emptyList());

        return communicationHandler.performRequest(requestObject, Config.class).get(0);
    }

    /**
     * (get_accounts)
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param accounts             List account name.
     * @return List Account
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
    public static List<Account> getAccounts(CommunicationHandler communicationHandler, List<AccountName> accounts)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API, RequestMethod.GET_ACCOUNTS,
                Collections.singletonList(accounts)
        );

        return communicationHandler.performRequest(requestObject, Account.class);
    }


    /**
     * Get all operations performed by the specified <code>accountName</code>.
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param account              get account history param include [name, start, limit]
     * @param start start position
     * @param limit limitation fo history
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
    @Deprecated
    public static List<AccountHistoryReturn> getAccountHistory(CommunicationHandler communicationHandler, String account, long start, long limit) throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API,
                RequestMethod.GET_ACCOUNT_HISTORY, Arrays.asList(account, start, limit));
        return communicationHandler.performRequest(requestObject, AccountHistoryReturn.class);
    }

    /**
     * (get_transaction_hex)
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param transaction          get serialized of Signed Transaction.
     * @return String
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
    public static String getTransactionHex(CommunicationHandler communicationHandler, SignedTransaction transaction)
            throws BeowulfCommunicationException, BeowulfResponseException {

        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API,
                RequestMethod.GET_TRANSACTION_HEX, Collections.singletonList(transaction));

        return communicationHandler.performRequest(requestObject, String.class).get(0);
    }

    /**
     * (find_smt_tokens_by_name)
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param names                List name token.
     * @return List FindSmtTokenByName
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
    public static List<FindSmtTokenByName> findSmtTokenByName(CommunicationHandler communicationHandler, List<String> names)
            throws BeowulfCommunicationException, BeowulfResponseException {

        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API,
                RequestMethod.FIND_SMT_TOKENS_BY_NAME, Collections.singletonList(names));

        return communicationHandler.performRequest(requestObject, FindSmtTokenByName.class);
    }

    /**
     * (get_next_scheduled_hardfork)
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @return ScheduledHardfork
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
    public static ScheduledHardfork getNextScheduleHardfork(CommunicationHandler communicationHandler)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API,
                RequestMethod.GET_NEXT_SCHEDULED_HARDFORK, Collections.emptyList());
        return communicationHandler.performRequest(requestObject, ScheduledHardfork.class).get(0);
    }

    /**
     * (get_key_references)
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param publicKeys           List public keys.
     * @return List String
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
    public static List<String> getKeyReferences(CommunicationHandler communicationHandler, List<PublicKey> publicKeys)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API,
                RequestMethod.GET_KEY_REFERENCES, Collections.singleton(publicKeys));
        return communicationHandler.performRequest(requestObject, List.class).get(0);
    }

    /**
     * (get_account_count
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @return ULong
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
    public static ULong getAccountCount(CommunicationHandler communicationHandler)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API,
                RequestMethod.GET_ACCOUNT_COUNT, Collections.emptyList());
        return communicationHandler.performRequest(requestObject, ULong.class).get(0);
    }

    /**
     * (get_supernode_by_account)
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param supernodeAccount     The acoount name.
     * @return Supernode
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
    public static Supernode getSupernodeByAccount(CommunicationHandler communicationHandler, AccountName supernodeAccount)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API,
                RequestMethod.GET_SUPERNODE_BY_ACCOUNT, Collections.singleton(supernodeAccount));
        return communicationHandler.performRequest(requestObject, Supernode.class).get(0);
    }

    /**
     * (get_supernodes_by_vote)
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param supernodeAccount     The acoount name.
     * @param limit                The number limit.
     * @param vestingShare         The number coin.
     * @return List Supernode
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
    public static List<Supernode> getSupernodesByVote(CommunicationHandler communicationHandler, AccountName supernodeAccount, int limit, int vestingShare)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API,
                RequestMethod.GET_SUPERNODES_BY_VOTE, Arrays.asList(supernodeAccount, limit, vestingShare));
        return communicationHandler.performRequest(requestObject, Supernode.class);
    }

    /**
     * (get_supernode_schedule)
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @return SupernodeSchedule
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
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API,
                RequestMethod.GET_SUPERNODE_SCHEDULE, Collections.emptyList());
        return communicationHandler.performRequest(requestObject, SupernodeSchedule.class).get(0);
    }

    /**
     * (get_supernode_count)
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @return int
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
    public static int getSupernodeCount(CommunicationHandler communicationHandler)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API,
                RequestMethod.GET_SUPERNODE_COUNT, Collections.emptyList());
        return communicationHandler.performRequest(requestObject, UInteger.class).get(0).intValue();
    }

    /**
     * (lookupSupernodeAccounts)
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param pattern              The pattern name to search.
     * @param limit                The number limit.
     * @return List String
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
    public static List<String> lookupSupernodeAccounts(CommunicationHandler communicationHandler, String pattern, int limit)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API,
                RequestMethod.LOOKUP_SUPERNODE_ACCOUNTS, Arrays.asList(pattern, limit));
        return communicationHandler.performRequest(requestObject, String.class);
    }

    /**
     * (lookupSupernodeAccounts)
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param pattern              The pattern name to search.
     * @param limit                The number limit.
     * @return List String
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
    public static List<String> lookupAccounts(CommunicationHandler communicationHandler, String pattern, int limit)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API,
                RequestMethod.LOOKUP_ACCOUNTS, Arrays.asList(pattern, limit));
        return communicationHandler.performRequest(requestObject, String.class);
    }

    /**
     * (get_transaction)
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param trx_id               The transaction id.
     * @return CompletedTransaction
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
    public static CompletedTransaction getTransactionDetail(CommunicationHandler communicationHandler, String trx_id)
            throws BeowulfCommunicationException, BeowulfResponseException {

        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API,
                RequestMethod.GET_TRANSACTION, Collections.singletonList(trx_id));

        return communicationHandler.performRequest(requestObject, CompletedTransaction.class).get(0);
    }

    /**
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param accountName          The account name.
     * @param publicKeys           List public keys.
     * @return Boolean
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
    public static Boolean verifyAccountAuthority(CommunicationHandler communicationHandler, AccountName accountName, List<PublicKey> publicKeys)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API,
                RequestMethod.VERIFY_ACCOUNT_AUTHORITY, Arrays.asList(accountName, publicKeys));

        return communicationHandler.performRequest(requestObject, Boolean.class).get(0);
    }

    /**
     * Broadcast a whole block.
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param accountName          account get balance
     * @param assetInfo            asset type
     * @return Asset balance of accountName
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
    public static Asset getBalance(CommunicationHandler communicationHandler, String accountName, AssetInfo assetInfo)
            throws BeowulfCommunicationException, BeowulfResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API,
                RequestMethod.GET_BALANCE, Arrays.asList(accountName, assetInfo));
        return communicationHandler.performRequest(requestObject, Asset.class).get(0);
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
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API,
                RequestMethod.BROADCAST_BLOCK, Collections.singleton(signedBlock));

        communicationHandler.performRequest(requestObject, Object.class);
    }

    /**
     * Broadcast a transaction on the Beowulf blockchain. This method will
     * validate the transaction and return immediately. Please notice that this
     * does not mean that the operation has been accepted and has been
     * processed. If you want to make sure that this is the case use the
     * {@link #broadcastTransaction(CommunicationHandler, SignedTransaction)}
     * method.
     *
     * @param communicationHandler A
     *                             {@link CommunicationHandler
     *                             CommunicationHandler} instance that should be used to send the
     *                             request.
     * @param transaction          The {@link SignedTransaction} object to broadcast.
     *
     * @return transactionId the local generate transaction id
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
    public static TransactionId broadcastTransaction(CommunicationHandler communicationHandler, SignedTransaction transaction)
            throws BeowulfCommunicationException, BeowulfResponseException, BeowulfInvalidTransactionException {
        if (transaction.getSignatures() == null || transaction.getSignatures().isEmpty()) {
            transaction.sign();
        }
        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API,
                RequestMethod.BROADCAST_TRANSACTION, Collections.singletonList(transaction));

        communicationHandler.performRequest(requestObject, Object.class);
        return transaction.generateTransactionId();
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
            System.out.println(transaction.generateTransactionId());
        }

        JsonRPCRequest requestObject = new JsonRPCRequest(BeowulfApiType.CONDENSER_API,
                RequestMethod.BROADCAST_TRANSACTION_SYNCHRONOUS, Collections.singletonList(transaction));

        return communicationHandler.performRequest(requestObject, BroadcastTransactionSynchronousReturn.class).get(0);
    }


    /*
     * (get_version) (get_state)
     * (get_hardfork_version)
     * (get_account_references) (lookup_account_names)`
     * (lookup_accounts) (get_account_count) (get_owner_history)
     * (get_required_signatures) (get_potential_signatures) (verify_authority)
     * (get_active_votes) (get_account_votes)
     * (broadcast_block)
     */
}


