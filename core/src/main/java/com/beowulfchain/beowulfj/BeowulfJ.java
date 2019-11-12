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

import com.beowulfchain.beowulfj.base.models.Block;
import com.beowulfchain.beowulfj.base.models.FutureExtensions;
import com.beowulfchain.beowulfj.base.models.ScheduledHardfork;
import com.beowulfchain.beowulfj.chain.CompletedTransaction;
import com.beowulfchain.beowulfj.chain.SignedTransaction;
import com.beowulfchain.beowulfj.communication.CommunicationHandler;
import com.beowulfchain.beowulfj.configuration.BeowulfJConfig;
import com.beowulfchain.beowulfj.enums.PrivateKeyType;
import com.beowulfchain.beowulfj.exceptions.BeowulfCommunicationException;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.exceptions.BeowulfResponseException;
import com.beowulfchain.beowulfj.plugins.apis.condenser.CondenserApi;
import com.beowulfchain.beowulfj.plugins.apis.condenser.models.AccountHistoryReturn;
import com.beowulfchain.beowulfj.plugins.apis.condenser.models.ExtendedAccount;
import com.beowulfchain.beowulfj.plugins.apis.condenser.models.FindSmtTokenByName;
import com.beowulfchain.beowulfj.plugins.apis.database.DatabaseApi;
import com.beowulfchain.beowulfj.plugins.apis.database.models.Config;
import com.beowulfchain.beowulfj.plugins.apis.database.models.DynamicGlobalProperty;
import com.beowulfchain.beowulfj.plugins.apis.database.models.Supernode;
import com.beowulfchain.beowulfj.plugins.apis.database.models.SupernodeSchedule;
import com.beowulfchain.beowulfj.plugins.apis.network.broadcast.models.BroadcastTransactionSynchronousReturn;
import com.beowulfchain.beowulfj.protocol.*;
import com.beowulfchain.beowulfj.protocol.enums.AssetSymbolType;
import com.beowulfchain.beowulfj.protocol.operations.AccountCreateOperation;
import com.beowulfchain.beowulfj.protocol.operations.Operation;
import com.beowulfchain.beowulfj.protocol.operations.SmtCreateOperation;
import com.beowulfchain.beowulfj.protocol.operations.TransferOperation;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import eu.bittrade.crypto.core.ECKey;
import eu.bittrade.crypto.core.Sha256Hash;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joou.UInteger;
import org.joou.ULong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidParameterException;
import java.util.List;


/**
 * This class is a wrapper for the Beowulf web socket API and provides all
 * features known from the Beowulf CLI Wallet.
 */
public class BeowulfJ {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeowulfJ.class);
    // Error messages as constants to make SonarQube happy.
    private static final String NO_DEFAULT_ACCOUNT_ERROR_MESSAGE = "You try to use a simplified operation without having a default account configured in BeowulfJConfig. Please configure a default account or use another method.";
    private static BeowulfJ beowulfJ;
    private CommunicationHandler communicationHandler;

    /**
     * Initialize the BeowulfJ.
     *
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
    private BeowulfJ() throws BeowulfCommunicationException {
        this.communicationHandler = new CommunicationHandler();
    }

    public static BeowulfJ getInstance() throws BeowulfCommunicationException {
        if (beowulfJ == null) {
            beowulfJ = new BeowulfJ();
        }
        return beowulfJ;
    }

    public static BeowulfJ getNewInstance() throws BeowulfCommunicationException {
        beowulfJ = new BeowulfJ();
        return beowulfJ;
    }

    public static Asset beowulfToWd(Price price, Asset beowulfAsset) {
        if (beowulfAsset == null || !beowulfAsset.getName().equals(AssetSymbolType.BWF)) {
            throw new InvalidParameterException("The asset needs be of SymbolType BWF.");
        }

        if (price == null) {
            return new Asset(0, AssetSymbolType.W);
        }

        return price.multiply(beowulfAsset);
    }

    public static Asset wdToBeowulf(Price price, Asset wdAsset) {
        if (wdAsset == null || !wdAsset.getName().equals(AssetSymbolType.W)) {
            throw new InvalidParameterException("The asset needs be of SymbolType BWF.");
        }

        if (price == null) {
            return new Asset(0, AssetSymbolType.BWF);
        }

        return price.multiply(wdAsset);
    }

    /**
     * Get the private and public key of a given type for the given
     * <code>account</code>
     *
     * @param account         The account name to generate the passwords for.
     * @param role            The key type that should be generated.
     * @param beowulfPassword The password of the <code>account</code> valid for the Beowulf
     *                        blockchain.
     * @return The requested key pair.
     */
    public static ImmutablePair<PublicKey, String> getPrivateKeyFromPassword(AccountName account, PrivateKeyType role,
                                                                             String beowulfPassword) {
        String seed = account.getName() + role.name().toLowerCase() + beowulfPassword;
        ECKey keyPair = ECKey.fromPrivate(Sha256Hash.hash(seed.getBytes(), 0, seed.length()));

        return new ImmutablePair<>(new PublicKey(keyPair), BeowulfJUtils.privateKeyToWIF(keyPair));
    }

    // #########################################################################
    // ## BLOCK API ############################################################
    // #########################################################################

    /**
     * Search for users under the use of their public key(s).
     *
     * @param publicKeys An array containing one or more public keys.
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
    public List<String> getKeyReferences(List<PublicKey> publicKeys)
            throws BeowulfCommunicationException, BeowulfResponseException {
        return CondenserApi.getKeyReferences(communicationHandler, publicKeys);
    }

    /**
     * Get all operations performed by the specified account.
     *
     * @param accountName The user name of the account.
     * @param start       The starting point.
     * @param limit       The maximum number of entries.
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
    public List<AccountHistoryReturn> getAccountHistory(AccountName accountName, ULong start, UInteger limit)
            throws BeowulfCommunicationException, BeowulfResponseException {
        return CondenserApi
                .getAccountHistory(communicationHandler, accountName.getName(), start.longValue(), limit.longValue());
    }

    /**
     * Get a full, signed block by providing its <code>blockNumber</code>. The
     * returned object contains all information related to the block (e.g.
     * processed transactions, the supernode and the creation time).
     *
     * @param blockNumber Height of the block to be returned.
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
    public Block getBlock(long blockNumber)
            throws BeowulfCommunicationException, BeowulfResponseException {
        return CondenserApi.getBlock(communicationHandler, blockNumber);
    }

    /**
     * Like {@link #getBlock(long)}, but will only return the header of the
     * requested block instead of the full, signed one.
     *
     * @param blockNumber Height of the block to be returned.
     * @return The referenced full, signed block, or <code>null</code> if no
     * matching block was found.
     * @throws BeowulfCommunicationException <ul>
     *                                       <li>If the server was not able to answer the request in the
     *                                       given time (see
     *                                       {@link BeowulfJConfig#setResponseTimeout(int)
     *                                       setResponseTimeout }).</li>
     *                                       <li>If there is a connection problem.</li>
     *                                       </ul>
     * @throws BeowulfResponseException      <ul>
     *                                       <li>If the BeowulfJ is unable to transform the JSON response
     *                                       into a Java object.</li>
     *                                       <li>If the Server returned an error object.</li>
     *                                       </ul>
     */
    public BlockHeader getBlockHeader(long blockNumber)
            throws BeowulfCommunicationException, BeowulfResponseException {
        return CondenserApi.getBlockHeader(communicationHandler, blockNumber);
    }

    // #########################################################################
    // ## DATABASE API #########################################################
    // #########################################################################

    /**
     * Broadcast a transaction on the Beowulf blockchain. This method will
     * validate the transaction and return immediately. Please notice that this
     * does not mean that the operation has been accepted and has been
     * processed. If you want to make sure that this is the case use the
     * {@link #broadcastTransactionSynchronous(SignedTransaction)} method.
     *
     * @param transaction The {@link SignedTransaction} object to broadcast.
     * @return TransactionId
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
    public TransactionId broadcastTransaction(SignedTransaction transaction)
            throws BeowulfCommunicationException, BeowulfResponseException, BeowulfInvalidTransactionException {
        return CondenserApi.broadcastTransaction(communicationHandler, transaction);
    }

    /**
     * Broadcast a transaction on the Beowulf blockchain. This method will
     * validate the transaction and return after it has been accepted and
     * applied.
     *
     * @param transaction The {@link SignedTransaction} object to broadcast.
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
    public BroadcastTransactionSynchronousReturn broadcastTransactionSynchronous(SignedTransaction transaction)
            throws BeowulfCommunicationException, BeowulfResponseException, BeowulfInvalidTransactionException {
        return CondenserApi.broadcastTransactionSynchronous(communicationHandler, transaction);
    }

    /**
     * Broadcast a whole block.
     *
     * @param signedBlock The {@link SignedBlock} object to broadcast.
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
    public void broadcastBlock(SignedBlock signedBlock) throws BeowulfCommunicationException, BeowulfResponseException {
        CondenserApi.broadcastBlock(communicationHandler, signedBlock);
    }

    /**
     * Get the list of the current active supernodes.
     *
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
    public List<AccountName> getActiveSupernodes() throws BeowulfCommunicationException, BeowulfResponseException {
        return CondenserApi.getActiveSuperNodes(communicationHandler);
    }

    /**
     * Get the global properties.
     *
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
    public DynamicGlobalProperty getDynamicGlobalProperties()
            throws BeowulfCommunicationException, BeowulfResponseException {
        return CondenserApi.getDynamicGlobalProperties(communicationHandler);
    }

    public Config getConfig() throws BeowulfCommunicationException, BeowulfResponseException {
        return CondenserApi.getConfig(communicationHandler);
    }

    /**
     * Get the current number of registered Beowulf accounts.
     *
     * @return The number of accounts.
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
    public long getAccountCount() throws BeowulfCommunicationException, BeowulfResponseException {
        return CondenserApi.getAccountCount(communicationHandler).longValue();
    }

    /**
     * @param accountNames A list of accounts you want to request the details for.
     * @return A List of accounts found for the given account names.
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
    public List<ExtendedAccount> getAccounts(List<AccountName> accountNames)
            throws BeowulfCommunicationException, BeowulfResponseException {
        return CondenserApi.getExtAccounts(communicationHandler, accountNames);
    }

    public Asset getBalance(AccountName accountName, AssetInfo assetInfo)
            throws BeowulfCommunicationException, BeowulfResponseException {
        return CondenserApi.getBalance(communicationHandler, accountName.getName(), assetInfo);
    }

    /**
     * TODO: Check what this method is supposed to do. In a fist test it seems
     * to return the time since the current version is active.
     *
     * @return ???
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
    public ScheduledHardfork getNextScheduledHarfork() throws BeowulfCommunicationException, BeowulfResponseException {
        return CondenserApi.getNextScheduleHardfork(communicationHandler);
    }

    /**
     * Use the Beowulf API to receive the HEX representation of a signed
     * transaction.
     *
     * @param signedTransaction The signed Transaction object you want to receive the HEX
     *                          representation for.
     * @return The HEX representation.
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
     * @throws BeowulfInvalidTransactionException The beowulf invalid transaction exception.
     */
    public String getTransactionHex(SignedTransaction signedTransaction)
            throws BeowulfCommunicationException, BeowulfResponseException, BeowulfInvalidTransactionException {
        if (BeowulfJConfig.getInstance().getDefaultAccount().isEmpty()) {
            throw new InvalidParameterException(NO_DEFAULT_ACCOUNT_ERROR_MESSAGE);
        }
        String result = CondenserApi.getTransactionHex(communicationHandler, signedTransaction);
        return result;
    }

    /**
     * Use the Beowulf API to receive the HEX representation of a signed
     * transaction.
     *
     * @param trx_id transaction id for getting data
     * @return completed transaction detail
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
    public CompletedTransaction getTransactionDetail(String trx_id)
            throws BeowulfCommunicationException, BeowulfResponseException {
        if (BeowulfJConfig.getInstance().getDefaultAccount().isEmpty()) {
            throw new InvalidParameterException(NO_DEFAULT_ACCOUNT_ERROR_MESSAGE);
        }
        CompletedTransaction result = CondenserApi.getTransactionDetail(communicationHandler, trx_id);
        return result;
    }

    /**
     * Get the supernode information for a given supernode account name.
     *
     * @param supernodeName The supernode name.
     * @return A list of supernodes.
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
    public Supernode getSupernodeByAccount(AccountName supernodeName)
            throws BeowulfCommunicationException, BeowulfResponseException {
        return CondenserApi.getSupernodeByAccount(communicationHandler, supernodeName);
    }

    /**
     * Get a list of supernodes sorted by the amount of votes. The list begins
     * with the given account name and contains the next supernodes with less
     * votes than given one.
     *
     * @param supernodeName The supernode name to start from.
     * @param limit         The number of results.
     * @param vestingShare  The number coin.
     * @return A list of supernodes.
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
    public List<Supernode> getSupernodesByVote(AccountName supernodeName, int limit, int vestingShare)
            throws BeowulfCommunicationException, BeowulfResponseException {

        return CondenserApi.getSupernodesByVote(communicationHandler, supernodeName, limit, vestingShare);
    }

    /**
     * Get the current number of active supernodes.
     *
     * @return The number of supernodes.
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
    public int getSupernodeCount() throws BeowulfCommunicationException, BeowulfResponseException {
        return CondenserApi.getSupernodeCount(communicationHandler);
    }

    /**
     * Get all supernodes.
     *
     * @return A list of supernodes.
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
    public List<Supernode> getSupernodes() throws BeowulfCommunicationException, BeowulfResponseException {
        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * requestObject.setApiMethod(RequestMethod.GET_SUPERNODES);
         * requestObject.setBeowulfApi(BeowulfApiType.DATABASE_API); String[]
         * parameters = {}; requestObject.setAdditionalParameters(parameters);
         *
         * return communicationHandler.performRequest(requestObject,
         * Supernode.class);
         */
        return null;
    }

    /**
     * Get the supernode schedule.
     *
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
    public SupernodeSchedule getSupernodeSchedule() throws BeowulfCommunicationException, BeowulfResponseException {
        return CondenserApi.getSupernodeSchedule(communicationHandler);
    }

    // #########################################################################
    // ## UTILITY METHODS ######################################################
    // #########################################################################

    /**
     * Search for accounts.
     *
     * @param pattern The lower case pattern you want to search for.
     * @param limit   The maximum number of account names.
     * @return A list of matching account names.
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
    public List<String> lookupAccounts(String pattern, int limit)
            throws BeowulfCommunicationException, BeowulfResponseException {
        return CondenserApi.lookupAccounts(communicationHandler, pattern, limit);
    }

    /**
     * Search for supernode accounts.
     *
     * @param pattern The lower case pattern you want to search for.
     * @param limit   The maximum number of account names.
     * @return A list of matching account names.
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
    public List<String> lookupSupernodeAccounts(String pattern, int limit)
            throws BeowulfCommunicationException, BeowulfResponseException {
        return CondenserApi.lookupSupernodeAccounts(communicationHandler, pattern, limit);
    }

    /**
     * Use the Beowulf API to verify the required authorities for this
     * transaction.
     *
     * @param signedTransaction A {@link SignedTransaction} transaction which has been signed.
     * @return <code>true</code> if the given transaction has been signed
     * correctly, otherwise an Exception will be thrown.
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
    public boolean verifyAuthority(SignedTransaction signedTransaction)
            throws BeowulfCommunicationException, BeowulfResponseException {
        return DatabaseApi.verifyAccountAuthority(communicationHandler, null).isValid();
    }

    // #########################################################################
    // ## SIMPLIFIED OPERATIONS ################################################
    // #########################################################################

    /**
     * Transfer currency from specified account to recipient. Amount is
     * automatically converted from normalized representation to base
     * representation. For example, to transfer 1.00 W to another account,
     * simply use:
     * <code>BeowulfJ.transfer(new AccountName("accounta"), new AccountName("accountb"), AssetSymbolType.W, 1.0, "My memo");</code>
     *
     * <b>Attention</b> This method will write data on the blockchain. As all
     * writing operations, a private key is required to sign the transaction.
     * For a transfer operation the private active key of the
     * {@link BeowulfJConfig#getDefaultAccount() DefaultAccount} needs to be
     * configured in the {@link BeowulfJConfig#getPrivateKeyStorage()
     * PrivateKeyStorage}.
     *
     * @param from   The account from which to transfer currency.
     * @param to     The account to which to transfer currency.
     * @param amount An {@link Asset} object containing the Asset type (see
     *               {@link AssetSymbolType}
     *               and the amount to transfer.
     * @param fee    The fee of transaction.
     * @param memo   Message include with transfer (255 char max)
     * @return The TransferOperation broadcast.
     */
    public TransferOperation transfer(AccountName from, AccountName to, Asset amount, Asset fee, String memo) {
        TransferOperation transferOperation = new TransferOperation(from, to, amount, fee, memo);
        return transferOperation;
    }

    public List<FindSmtTokenByName> findSmtTokenByName(List<String> names)
            throws BeowulfCommunicationException, BeowulfResponseException {
        return CondenserApi.findSmtTokenByName(communicationHandler, names);
    }

    public SmtCreateOperation smtCreate(AccountName controlAccount, AccountName creator,
                                        AssetInfo assetInfo,
                                        Asset smtCreationFee, long maxSupply) {
        UInteger precision = assetInfo.getDecimals();
        SmtCreateOperation smtCreateOperation = new SmtCreateOperation(controlAccount, creator, assetInfo, smtCreationFee, precision, null, maxSupply);
        return smtCreateOperation;
    }

    public AccountCreateOperation createAccount(AccountName creator, Asset fee, AccountName newAccount, Authority owner, String jsonMetadata) {
        AccountCreateOperation accountCreateOperation = new AccountCreateOperation(creator, fee, newAccount, owner, jsonMetadata);
        return accountCreateOperation;
    }

    public BroadcastTransactionSynchronousReturn signAndBroadcastSynchronous(List<Operation> operations)
            throws BeowulfCommunicationException, BeowulfResponseException, BeowulfInvalidTransactionException {
        SignedTransaction signedTransaction = signTransaction(operations, null);
        return this.broadcastTransactionSynchronous(signedTransaction);
    }

    public TransactionId signAndBroadcast(List<Operation> operations)
            throws BeowulfCommunicationException, BeowulfResponseException, BeowulfInvalidTransactionException {
        SignedTransaction signedTransaction = signTransaction(operations, null);
        return this.broadcastTransaction(signedTransaction);
    }

    private SignedTransaction signTransaction(List<Operation> operations,  List<FutureExtensions> extensions) throws BeowulfCommunicationException, BeowulfResponseException {
        DynamicGlobalProperty globalProperties = this.getDynamicGlobalProperties();
        return new SignedTransaction(globalProperties.getHeadBlockId(), operations,
                extensions);
    }

    public TransactionId signAndBroadcastWithExtension(List<Operation> operations, List<FutureExtensions> extensions)
            throws BeowulfCommunicationException, BeowulfResponseException, BeowulfInvalidTransactionException, JsonProcessingException {
        SignedTransaction signedTransaction = signTransaction(operations, extensions);
        return this.broadcastTransaction(signedTransaction);
    }
}
