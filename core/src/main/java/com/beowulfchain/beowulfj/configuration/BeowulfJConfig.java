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
package com.beowulfchain.beowulfj.configuration;

import com.beowulfchain.beowulfj.BeowulfJ;
import com.beowulfchain.beowulfj.chain.NetworkProperties;
import com.beowulfchain.beowulfj.chain.network.Testnet;
import com.beowulfchain.beowulfj.enums.AddressPrefixType;
import com.beowulfchain.beowulfj.enums.PrivateKeyType;
import com.beowulfchain.beowulfj.enums.SynchronizationType;
import com.beowulfchain.beowulfj.enums.ValidationType;
import com.beowulfchain.beowulfj.exceptions.BeowulfTimeoutException;
import com.beowulfchain.beowulfj.protocol.AccountName;
import com.beowulfchain.beowulfj.protocol.AssetSymbol;
import com.beowulfchain.beowulfj.protocol.Symbol;
import com.beowulfchain.beowulfj.protocol.enums.AssetSymbolType;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.ClientEndpointConfig;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * This class stores the configuration that is used for the communication to the
 * defined server.
 * <p>
 * The setters can be used to override the default values.
 */
public class BeowulfJConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeowulfJConfig.class);
    /**
     * The BeowulfJ account.
     */
    private static final AccountName BEOWULFJ_ACCOUNT = new AccountName("");
    /**
     * The BeowulfJ version.
     */
    private static final String BEOWULFJ_VERSION = BeowulfJ.class.getPackage().getImplementationVersion();
    /**
     * The BeowulfJ App-Name
     */
    private static final String BEOWULFJ_NAME = BeowulfJ.class.getPackage().getImplementationTitle();
    /**
     * The endpoint URI used by default.
     */
    private static String DEFAULT_BEOWULF_API_URI = "https://testnet-bw.beowulfchain.com/rpc";
    /**
     * The inner {@link BeowulfJConfig} instance.
     */
    private static BeowulfJConfig beowulfJConfigInstance;
    private ClientEndpointConfig clientEndpointConfig;
    private List<Pair<URI, Boolean>> endpointURIs;
    private int responseTimeout;
    private int idleTimeout;
    private String dateTimePattern;
    private long maximumExpirationDateOffset;
    private String timeZoneId;
    private AccountName apiUsername;
    private char[] apiPassword;
    private AccountName defaultAccount;
    private PrivateKeyStorage privateKeyStorage;
    private Charset encodingCharset;
    private AddressPrefixType addressPrefix;
    private String chainId;
    private short beowulfJWeight;
    private ValidationType validationLevel;
    private SynchronizationType synchronizationLevel;
    private AssetSymbol dollarSymbol;
    private AssetSymbol tokenSymbol;
    private AssetSymbol vestsSymbol;
    private NetworkProperties network;

    /**
     * Default constructor that will set all default values.
     */
    private BeowulfJConfig() {
        this.setClientEndpointConfig(ClientEndpointConfig.Builder.create().build());
        this.setDefaultBeowulfApiUri(DEFAULT_BEOWULF_API_URI);
        this.setResponseTimeout(10000);
        this.setIdleTimeout(60000);
        this.setDateTime("yyyy-MM-dd'T'HH:mm:ss", "GMT");
        this.setApiUsername(new AccountName(System.getProperty("beowulfj.api.username", "")));
        this.setApiPassword(System.getProperty("beowulfj.api.password", "").toCharArray());
        this.setMaximumExpirationDateOffset(3600000L);
        this.setEncodingCharset(StandardCharsets.UTF_8);
        this.setBeowulfJWeight(Short.parseShort("250"));
        this.setValidationLevel(ValidationType.ALL);
        this.setSynchronizationLevel(SynchronizationType.FULL);
        NetworkProperties network = new Testnet();
        this.setNetwork(network);

        // Fill the key store with the provided accountName and private keys.
        this.setDefaultAccount(new AccountName(System.getProperty("beowulfj.default.account", "")));
        this.privateKeyStorage = new PrivateKeyStorage();
        if (!this.defaultAccount.isEmpty()) {
            privateKeyStorage.addAccount(this.defaultAccount);
            for (PrivateKeyType privateKeyType : PrivateKeyType.values()) {
                String wifPrivateKey = System
                        .getProperty("beowulfj.default.account." + privateKeyType.name().toLowerCase() + ".key");
                // Only add keys if they are present.
                if (wifPrivateKey != null && !wifPrivateKey.isEmpty()) {
                    privateKeyStorage.addPrivateKeyToAccount(this.defaultAccount,
                            new ImmutablePair<PrivateKeyType, String>(privateKeyType, wifPrivateKey));
                }
            }
        }
    }

    /**
     * Receive a {@link BeowulfJConfig
     * BeowulfJConfig} instance.
     *
     * @return A BeowulfJConfig instance.
     */
    public static BeowulfJConfig getInstance() {
        if (beowulfJConfigInstance == null) {
            beowulfJConfigInstance = new BeowulfJConfig();
        }

        return beowulfJConfigInstance;
    }

    /**
     * Overrides the current
     * {@link BeowulfJConfig BeowulfJConfig}
     * instance and returns a new one.
     *
     * @return A BeowulfJConfig instance.
     */
    public static BeowulfJConfig getNewInstance() {
        beowulfJConfigInstance = new BeowulfJConfig();
        return beowulfJConfigInstance;
    }

    /**
     * @return The official BeowulfJ account name.
     */
    public static AccountName getBeowulfJAccount() {
        return BEOWULFJ_ACCOUNT;
    }

    /**
     * Get the version of BeowulfJ that is currently used. This parameter is set
     * during the build and can't be changed.
     *
     * @return The BeowulfJ version that is currently used.
     */
    public static String getBeowulfJVersion() {
        return BEOWULFJ_VERSION;
    }

    /**
     * Get the application name of BeowulfJ. This parameter is set during the
     * build and can't be changed.
     *
     * @return The application name of BeowulfJ.
     */
    public static String getBeowulfJAppName() {
        return BEOWULFJ_NAME;
    }

    /**
     * Get the currently configured <code>synchronizationLevel</code>.
     * <p>
     * The <code>synchronizationLevel</code> defines which values should be
     * synchronized between this BeowulfJ instance and the connected node. By
     * default, BeowulfJ will synchronize as much as possible.
     *
     * @return The synchronization level.
     */
    public SynchronizationType getSynchronizationLevel() {
        return synchronizationLevel;
    }

    /**
     * Override the currently configured <code>synchronizationLevel</code>.
     * <p>
     * The <code>synchronizationLevel</code> defines which values should be
     * synchronized between this BeowulfJ instance and the connected node. By
     * default, BeowulfJ will synchronize as much as possible.
     *
     * @param synchronizationLevel The synchronization level to set.
     */
    public void setSynchronizationLevel(SynchronizationType synchronizationLevel) {
        this.synchronizationLevel = synchronizationLevel;
    }

    /**
     * Get current super node
     * @return String
     */
    public String getDefaultBeowulfApiUri() {
        return DEFAULT_BEOWULF_API_URI;
    }

    /**
     * Override the currently configured <code>DEFAULT_BEOWULF_API_URI</code>.
     * <p>
     * The <code>DEFAULT_BEOWULF_API_URI</code> define what super node to interact with
     *
     * @param apiUri The api Uri to set.
     */
    public void setDefaultBeowulfApiUri(String apiUri) {
        DEFAULT_BEOWULF_API_URI = apiUri;
        try {
            this.endpointURIs = new ArrayList<>();
            this.addEndpointURI(new URI(DEFAULT_BEOWULF_API_URI));
        } catch (URISyntaxException e) {
            // This can never happen!
            LOGGER.error("At least one of the configured default URIs has a Syntax error.", e);
        }
    }

    /**
     * Get the currently configured password used to login at a Beowulf Node to
     * access protected APIs.
     *
     * @return The currently configured password for the API access.
     */
    public char[] getApiPassword() {
        return apiPassword;
    }

    /**
     * Set the password which should be used to login to a node. This is not
     * required if the node is not protected.
     *
     * @param apiPassword The password to use.
     */
    public void setApiPassword(char[] apiPassword) {
        this.apiPassword = apiPassword;
    }

    /**
     * Get the currently configured account name used to login at a Beowulf Node
     * to access protected APIs.
     *
     * @return The currently configured account name for the API access.
     */
    public AccountName getApiUsername() {
        return apiUsername;
    }

    /**
     * Set the account name which should be used to login to a node. This is not
     * required if the node is not protected.
     *
     * @param apiUsername The account name to use.
     */
    public void setApiUsername(AccountName apiUsername) {
        this.apiUsername = apiUsername;
    }

    /**
     * Get the currently configured default account name. The default account
     * name is used for operations, if no other account has been provided.
     *
     * @return The configured default account.
     */
    public AccountName getDefaultAccount() {
        return defaultAccount;
    }

    /**
     * Set the default account used for simplified operations.
     *
     * @param defaultAccount The account to set.
     */
    public void setDefaultAccount(AccountName defaultAccount) {
        this.defaultAccount = defaultAccount;
    }

    /**
     * Get the currently configured chain id used to sign transactions. For the
     * production chain the id is a 56bit long 0 sequence which is configured by
     * default.
     *
     * @return The currently configured Chain ID.
     */
    public String getChainId() {
        return chainId;
    }

    /**
     * Set the chain id used to sign transactions. For the production chain the
     * id is a 56bit long 0 sequence which is configured by default.
     *
     * @param chainId The chain id to set.
     */
    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    /**
     * Get the configured ClientEndpointConfig instance. Please be aware that
     * the <code>clientEndpointConfig</code> is only used for WebSocket
     * endpoints.
     *
     * @return The configured ClientEndpointConfig instance.
     */
    public ClientEndpointConfig getClientEndpointConfig() {
        return clientEndpointConfig;
    }

    /**
     * Override the default ClientEndpointConfig instance.
     *
     * @param clientEndpointConfig The configuration of the client end point.
     */
    public void setClientEndpointConfig(ClientEndpointConfig clientEndpointConfig) {
        this.clientEndpointConfig = clientEndpointConfig;
    }

    /**
     * Get the currently configured date time pattern. This date pattern is used
     * to serialize and deserialize JSON/Java objects.
     *
     * @return The used date time pattern used for
     * serialization/deserialization.
     */
    public String getDateTimePattern() {
        return dateTimePattern;
    }

    /**
     * Get the currently configured Charset that will be used to encode Strings.
     *
     * @return The configured Charset.
     */
    public Charset getEncodingCharset() {
        return encodingCharset;
    }

    /**
     * Define the Charset that should be used to encode Strings.
     *
     * @param encodingCharset A Charset instance like StandardCharsets.UTF_8.
     */
    public void setEncodingCharset(Charset encodingCharset) {
        this.encodingCharset = encodingCharset;
    }

    /**
     * Get the currently configured maximum offset of the expiration date.
     *
     * @return The maximum offset of the expiration date.
     */
    public long getMaximumExpirationDateOffset() {
        return maximumExpirationDateOffset;
    }

    /**
     * A Beowulf Node will only accept transactions whose expiration date is not
     * to far in the future.
     *
     * <p>
     * Example:<br>
     * Time now: 2017-04-20 20:33 Latest allowed expiration date: 2017-04-20
     * 21:24
     * </p>
     * <p>
     * The difference between $NOW and the $MAXIMAL_ALLOWED_TIME can be
     * configured here.
     *
     * @param maximumExpirationDateOffset The offset in milliseconds.
     */
    public void setMaximumExpirationDateOffset(long maximumExpirationDateOffset) {
        this.maximumExpirationDateOffset = maximumExpirationDateOffset;
    }

    /**
     * Get the private key storage to manage the private keys for one or
     * multiple accounts.
     * <p>
     * The private keys have been defined by the account creator (e.g.
     * beowulf.com) and are required to write data on the blockchain.
     *
     * <ul>
     * <li>A posting key is required to vote, post or comment on content.</li>
     * <li>An active key is required to interact with the market, to change keys
     * and to vote for supernodes.</li>
     * <li>An owner key is required to change the keys.</li>
     * <li>A memo key is required to use private messages.</li>
     * </ul>
     *
     * @return The privateKeyStorage.
     */
    public PrivateKeyStorage getPrivateKeyStorage() {
        return privateKeyStorage;
    }

    /**
     * Get the currently configured address prefix. This prefix is used to parse
     * keys in their WIF format.
     *
     * @return The address prefix.
     */
    public AddressPrefixType getAddressPrefix() {
        return addressPrefix;
    }

    /**
     * Set the address prefix. This prefix is used to parse keys in their WIF
     * format.
     *
     * @param addressPrefix The address prefix to set.
     */
    public void setAddressPrefix(AddressPrefixType addressPrefix) {
        this.addressPrefix = addressPrefix;
    }

    /**
     * Get the configured, maximum time that BeowulfJ will wait for an answer of
     * the endpoint before throwing a {@link BeowulfTimeoutException} exception.
     *
     * @return Time in milliseconds
     */
    public int getResponseTimeout() {
        return responseTimeout;
    }

    /**
     * Override the default, maximum time that BeowulfJ will wait for an answer of
     * the Beowulf Node. If set to <code>0</code> the timeout mechanism will be
     * disabled.
     *
     * @param responseTimeout Time in milliseconds.
     * @throws IllegalArgumentException If the value of timeout is negative.
     */
    public void setResponseTimeout(int responseTimeout) {
        if (responseTimeout < 0) {
            throw new IllegalArgumentException("The timeout has to be greater than 0. (0 will disable the timeout).");
        }

        this.responseTimeout = responseTimeout;
    }

    /**
     * Get the configured, maximum time that BeowulfJ will keep an unused
     * connection open. A value that is 0 or negative indicates the sessions
     * will never timeout due to inactivity.
     *
     * @return The time in milliseconds a connection should be left intact even
     * when no activities are performed.
     */
    public int getIdleTimeout() {
        return idleTimeout;
    }

    /**
     * Override the default, maximum time that BeowulfJ will keep an unused
     * connection open.A value that is 0 or negative indicates the sessions will
     * never timeout due to inactivity.
     *
     * @param idleTimeout The time in milliseconds a connection should be left intact
     *                    even when no activities are performed.
     */
    public void setIdleTimeout(int idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    /**
     * Get the currently configured time zone id.
     *
     * @return The time zone id.
     */
    public String getTimeZoneId() {
        return timeZoneId;
    }

    /**
     * @return Get all configured endpoint URIs.
     */
    public List<Pair<URI, Boolean>> getEndpointURIs() {
        return endpointURIs;
    }

    /**
     * Override the currently configured <code>endpointURIs</code>.
     *
     * @param endpointURIs A list of endpoints to connect to.
     */
    public void setEndpointURIs(List<Pair<URI, Boolean>> endpointURIs) {
        this.endpointURIs = endpointURIs;
    }

    /**
     * Get one of the configured endpoint URIs by providing a
     * <code>selector</code>.
     *
     * @param selector A number used to calculate the next stored endpoint URI from
     *                 the list of configured endpoint URIs.
     * @return One specific endpoint URI.
     */
    public Pair<URI, Boolean> getNextEndpointURI(int selector) {
        return endpointURIs.get(selector % endpointURIs.size());
    }

    /**
     * Get the currently configured beneficiary weight.
     *
     * @return The beneficiary weight.
     */
    public short getBeowulfJWeight() {
        return beowulfJWeight;
    }

    /**
     * Set the currently configured beneficiary weight.
     *
     * @param beowulfJWeight The beneficiary weight for BeowulfJ.
     */
    public void setBeowulfJWeight(short beowulfJWeight) {
        this.beowulfJWeight = beowulfJWeight;
    }

    /**
     * Override the default date pattern. This date pattern is used to serialize
     * and deserialize JSON/Java objects.
     *
     * @param dateTimePattern The date time pattern used for serialization/deserialization.
     * @param timeZoneId      The time zone id used for serialization/deserialization (e.g.
     *                        "UTC").
     */
    public void setDateTime(String dateTimePattern, String timeZoneId) {
        // Create a SimpleDateFormat instance to verify the pattern is valid.
        new SimpleDateFormat(dateTimePattern);
        this.dateTimePattern = dateTimePattern;
        // Try to verify the timeZoneId.
        if (!"GMT".equals(timeZoneId) && "GMT".equals(TimeZone.getTimeZone(timeZoneId).getID())) {
            LOGGER.warn("The timezoneId {} could not be understood - UTC will now be used as a default.", timeZoneId);
            this.timeZoneId = "UTC";
        } else {
            this.timeZoneId = timeZoneId;
        }
    }

    /**
     * This method has the same functionality than
     * {@link #addEndpointURI(URI, boolean) setEndpointURI(URI, boolean)}, but
     * this method will enable the SSL verification by default.
     *
     * @param endpointURI The URI of the node you want to connect to.
     * @throws URISyntaxException If the <code>endpointURI</code> is null.
     */
    public void addEndpointURI(URI endpointURI) throws URISyntaxException {
        addEndpointURI(endpointURI, false);
    }

    /**
     * Configure the connection to the Beowulf Node by providing the endpoint URI
     * and the SSL verification settings.
     *
     * @param endpointURI             The URI of the node you want to connect to.
     * @param sslVerificationDisabled Define if BeowulfJ should verify the SSL certificate of the
     *                                endpoint. This option will be ignored if the given
     *                                <code>endpointURI</code> is using a non SSL protocol.
     * @throws URISyntaxException If the <code>endpointURI</code> is null.
     */
    public void addEndpointURI(URI endpointURI, boolean sslVerificationDisabled) throws URISyntaxException {
        if (endpointURI == null) {
            throw new URISyntaxException("endpointURI", "The endpointURI can't be null.");
        }

        this.endpointURIs.add(new ImmutablePair<URI, Boolean>(endpointURI, sslVerificationDisabled));
    }

    /**
     * Get the currently configured validation level.
     *
     * @return The currently configured validation level.
     */
    public ValidationType getValidationLevel() {
        return validationLevel;
    }

    /**
     * Override the default validation level that BeowulfJ will use to validate if
     * an Object contains valid information before broadcasting it to the Beowulf
     * Node. By default BeowulfJ will validate as much as possible.
     *
     * @param validationLevel The validation level to set.
     */
    public void setValidationLevel(ValidationType validationLevel) {
        this.validationLevel = validationLevel;
    }

    /**
     * Get the currently configured {@link AssetSymbol} for dollars (e.g.
     * W). The configured symbol type is used to validate M fields of
     * operations.
     *
     * @return The currently configured {@link AssetSymbol} for dollars.
     */
    public AssetSymbol getDollarSymbol() {
        return dollarSymbol;
    }

    /**
     * Override the default <code>dollarSymbol</code>. The configured symbol
     * type is used to validate M fields of operations.
     *
     * @param dollarSymbol The {@link AssetSymbol} for dollars to set.
     */
    public void setDollarSymbol(AssetSymbol dollarSymbol) {
        this.dollarSymbol = dollarSymbol;
    }

    /**
     * Get the currently configured {@link AssetSymbolType} for tokens (e.g.
     * BWF). The configured symbol type is used to validate M fields of
     * operations.
     *
     * @return The currently configured {@link AssetSymbolType} for tokens.
     */
    public AssetSymbol getTokenSymbol() {
        return tokenSymbol;
    }

    /**
     * Override the default <code>tokenSymbol</code>. The configured symbol type
     * is used to validate token fields of operations.
     *
     * @param tokenSymbol The {@link AssetSymbolType} for tokens to set.
     */
    public void setTokenSymbol(AssetSymbol tokenSymbol) {
        this.tokenSymbol = tokenSymbol;
    }

    /**
     * Get the currently configured {@link AssetSymbolType} for M. The
     * configured symbol type is used to validate M fields of operations.
     *
     * @return The currently configured {@link AssetSymbolType} for M.
     */
    public AssetSymbol getVestsSymbol() {
        return vestsSymbol;
    }

    /**
     * Override the default <code>vestsSymbol</code>. The configured symbol type
     * is used to validate M fields of operations.
     *
     * @param vestsSymbol The {@link AssetSymbolType} for M to set.
     */
    public void setVestsSymbol(AssetSymbol vestsSymbol) {
        this.vestsSymbol = vestsSymbol;
    }

    public NetworkProperties getNetwork() {
        return network;
    }

    public void setNetwork(NetworkProperties network) {
        this.network = network;
        this.setAddressPrefix(AddressPrefixType.valueOf(network.getPrefix()));
        // using testnet id by default
        this.setChainId(network.getChain_id());
        this.setDollarSymbol(new AssetSymbol(network.getWd_symbol()));
        this.setTokenSymbol(new AssetSymbol(network.getBeowulf_symbol()));
        this.setVestsSymbol(new AssetSymbol(network.getVests_symbol()));
    }

    public static class BeowulfConfig {
        private boolean IS_TEST_NET;
        private float SMT_TOKEN_CREATION_FEE;
        Symbol WD_SYMBOL;
        private float BEOWULF_100_PERCENT;
        private float BEOWULF_1_PERCENT;
        private String BEOWULF_ADDRESS_PREFIX;
        private String BEOWULF_BLOCKCHAIN_HARDFORK_VERSION;
        private String BEOWULF_BLOCKCHAIN_VERSION;
        private float BEOWULF_BLOCK_INTERVAL;
        private float BEOWULF_BLOCKS_PER_DAY;
        private float BEOWULF_BLOCKS_PER_YEAR;
        private String BEOWULF_CHAIN_ID;
        private String BEOWULF_GENESIS_TIME;
        private float BEOWULF_HARDFORK_REQUIRED_SUPERNODES;
        private float BEOWULF_INFLATION_NARROWING_PERIOD;
        private float BEOWULF_INFLATION_RATE_START_PERCENT;
        private float BEOWULF_INFLATION_RATE_STOP_PERCENT;
        private String BEOWULF_INIT_MINER_NAME;
        private String BEOWULF_INIT_PUBLIC_KEY_STR;
        private float BEOWULF_INIT_SUPPLY;
        private float WD_INIT_SUPPLY;
        private float BEOWULF_IRREVERSIBLE_THRESHOLD;
        private float BEOWULF_MAX_ACCOUNT_NAME_LENGTH;
        private float BEOWULF_MAX_ACCOUNT_SUPERNODE_VOTES;
        private float BEOWULF_MAX_AUTHORITY_MEMBERSHIP;
        private float BEOWULF_SOFT_MAX_BLOCK_SIZE;
        private float BEOWULF_MAX_MEMO_SIZE;
        private float BEOWULF_MAX_SUPERNODES;
        private float BEOWULF_MAX_PERMANENT_SUPERNODES_HF0;
        private float BEOWULF_MAX_RUNNER_SUPERNODES_HF0;
        private float BEOWULF_MAX_SHARE_SUPPLY;
        private float BEOWULF_MAX_SIG_CHECK_DEPTH;
        private float BEOWULF_MAX_SIG_CHECK_ACCOUNTS;
        private float BEOWULF_MAX_TIME_UNTIL_EXPIRATION;
        private float BEOWULF_MAX_TRANSACTION_SIZE;
        private float BEOWULF_MAX_UNDO_HISTORY;
        private float BEOWULF_MAX_VOTED_SUPERNODES_HF0;
        private float BEOWULF_MIN_SUPERNODE_FUND;
        private float BEOWULF_MIN_TRANSACTION_FEE;
        private float BEOWULF_MIN_ACCOUNT_CREATION_FEE;
        private float BEOWULF_MIN_ACCOUNT_NAME_LENGTH;
        private float BEOWULF_MIN_BLOCK_SIZE;
        private String BEOWULF_NULL_ACCOUNT;
        private float BEOWULF_NUM_INIT_MINERS;
        private float BEOWULF_OWNER_AUTH_HISTORY_TRACKING_START_BLOCK_NUM;
        private float BEOWULF_OWNER_UPDATE_LIMIT;
        private float BEOWULF_VESTING_WITHDRAW_INTERVALS;
        private float BEOWULF_VESTING_WITHDRAW_INTERVAL_SECONDS;
        private Symbol BEOWULF_SYMBOL;
        private Symbol VESTS_SYMBOL;
        private String BEOWULF_VIRTUAL_SCHEDULE_LAP_LENGTH2;
        private float BEOWULF_1_BEOWULF;
        private float BEOWULF_1_VESTS;
        private float BEOWULF_MAX_TOKEN_PER_ACCOUNT;
        private float BEOWULF_MIN_PERMANENT_SUPERNODE_FUND;
        private float BEOWULF_MAX_TOKEN_NAME_LENGTH;
        private float BEOWULF_MIN_TOKEN_NAME_LENGTH;
        private String BEOWULF_SYMBOL_BEOWULF;
        private String BEOWULF_SYMBOL_WD;
        private String BEOWULF_SYMBOL_VESTS;
        private float BEOWULF_BLOCK_REWARD_GAP;
        private float BEOWULF_ITEM_QUEUE_SIZE;
        private float BEOWULF_FLUSH_INTERVAL;


        // Getter Methods

        public boolean getIS_TEST_NET() {
            return IS_TEST_NET;
        }

        public float getSMT_TOKEN_CREATION_FEE() {
            return SMT_TOKEN_CREATION_FEE;
        }

        public Symbol getWD_SYMBOL() {
            return WD_SYMBOL;
        }

        public float getBEOWULF_100_PERCENT() {
            return BEOWULF_100_PERCENT;
        }

        public float getBEOWULF_1_PERCENT() {
            return BEOWULF_1_PERCENT;
        }

        public String getBEOWULF_ADDRESS_PREFIX() {
            return BEOWULF_ADDRESS_PREFIX;
        }

        public String getBEOWULF_BLOCKCHAIN_HARDFORK_VERSION() {
            return BEOWULF_BLOCKCHAIN_HARDFORK_VERSION;
        }

        public String getBEOWULF_BLOCKCHAIN_VERSION() {
            return BEOWULF_BLOCKCHAIN_VERSION;
        }

        public float getBEOWULF_BLOCK_INTERVAL() {
            return BEOWULF_BLOCK_INTERVAL;
        }

        public float getBEOWULF_BLOCKS_PER_DAY() {
            return BEOWULF_BLOCKS_PER_DAY;
        }

        public float getBEOWULF_BLOCKS_PER_YEAR() {
            return BEOWULF_BLOCKS_PER_YEAR;
        }

        public String getBEOWULF_CHAIN_ID() {
            return BEOWULF_CHAIN_ID;
        }

        public String getBEOWULF_GENESIS_TIME() {
            return BEOWULF_GENESIS_TIME;
        }

        public float getBEOWULF_HARDFORK_REQUIRED_SUPERNODES() {
            return BEOWULF_HARDFORK_REQUIRED_SUPERNODES;
        }

        public float getBEOWULF_INFLATION_NARROWING_PERIOD() {
            return BEOWULF_INFLATION_NARROWING_PERIOD;
        }

        public float getBEOWULF_INFLATION_RATE_START_PERCENT() {
            return BEOWULF_INFLATION_RATE_START_PERCENT;
        }

        public float getBEOWULF_INFLATION_RATE_STOP_PERCENT() {
            return BEOWULF_INFLATION_RATE_STOP_PERCENT;
        }

        public String getBEOWULF_INIT_MINER_NAME() {
            return BEOWULF_INIT_MINER_NAME;
        }

        public String getBEOWULF_INIT_PUBLIC_KEY_STR() {
            return BEOWULF_INIT_PUBLIC_KEY_STR;
        }

        public float getBEOWULF_INIT_SUPPLY() {
            return BEOWULF_INIT_SUPPLY;
        }

        public float getWD_INIT_SUPPLY() {
            return WD_INIT_SUPPLY;
        }

        public float getBEOWULF_IRREVERSIBLE_THRESHOLD() {
            return BEOWULF_IRREVERSIBLE_THRESHOLD;
        }

        public float getBEOWULF_MAX_ACCOUNT_NAME_LENGTH() {
            return BEOWULF_MAX_ACCOUNT_NAME_LENGTH;
        }

        public float getBEOWULF_MAX_ACCOUNT_SUPERNODE_VOTES() {
            return BEOWULF_MAX_ACCOUNT_SUPERNODE_VOTES;
        }

        public float getBEOWULF_MAX_AUTHORITY_MEMBERSHIP() {
            return BEOWULF_MAX_AUTHORITY_MEMBERSHIP;
        }

        public float getBEOWULF_SOFT_MAX_BLOCK_SIZE() {
            return BEOWULF_SOFT_MAX_BLOCK_SIZE;
        }

        public float getBEOWULF_MAX_MEMO_SIZE() {
            return BEOWULF_MAX_MEMO_SIZE;
        }

        public float getBEOWULF_MAX_SUPERNODES() {
            return BEOWULF_MAX_SUPERNODES;
        }

        public float getBEOWULF_MAX_PERMANENT_SUPERNODES_HF0() {
            return BEOWULF_MAX_PERMANENT_SUPERNODES_HF0;
        }

        public float getBEOWULF_MAX_RUNNER_SUPERNODES_HF0() {
            return BEOWULF_MAX_RUNNER_SUPERNODES_HF0;
        }

        public float getBEOWULF_MAX_SHARE_SUPPLY() {
            return BEOWULF_MAX_SHARE_SUPPLY;
        }

        public float getBEOWULF_MAX_SIG_CHECK_DEPTH() {
            return BEOWULF_MAX_SIG_CHECK_DEPTH;
        }

        public float getBEOWULF_MAX_SIG_CHECK_ACCOUNTS() {
            return BEOWULF_MAX_SIG_CHECK_ACCOUNTS;
        }

        public float getBEOWULF_MAX_TIME_UNTIL_EXPIRATION() {
            return BEOWULF_MAX_TIME_UNTIL_EXPIRATION;
        }

        public float getBEOWULF_MAX_TRANSACTION_SIZE() {
            return BEOWULF_MAX_TRANSACTION_SIZE;
        }

        public float getBEOWULF_MAX_UNDO_HISTORY() {
            return BEOWULF_MAX_UNDO_HISTORY;
        }

        public float getBEOWULF_MAX_VOTED_SUPERNODES_HF0() {
            return BEOWULF_MAX_VOTED_SUPERNODES_HF0;
        }

        public float getBEOWULF_MIN_SUPERNODE_FUND() {
            return BEOWULF_MIN_SUPERNODE_FUND;
        }

        public float getBEOWULF_MIN_TRANSACTION_FEE() {
            return BEOWULF_MIN_TRANSACTION_FEE;
        }

        public float getBEOWULF_MIN_ACCOUNT_CREATION_FEE() {
            return BEOWULF_MIN_ACCOUNT_CREATION_FEE;
        }

        public float getBEOWULF_MIN_ACCOUNT_NAME_LENGTH() {
            return BEOWULF_MIN_ACCOUNT_NAME_LENGTH;
        }

        public float getBEOWULF_MIN_BLOCK_SIZE() {
            return BEOWULF_MIN_BLOCK_SIZE;
        }

        public String getBEOWULF_NULL_ACCOUNT() {
            return BEOWULF_NULL_ACCOUNT;
        }

        public float getBEOWULF_NUM_INIT_MINERS() {
            return BEOWULF_NUM_INIT_MINERS;
        }

        public float getBEOWULF_OWNER_AUTH_HISTORY_TRACKING_START_BLOCK_NUM() {
            return BEOWULF_OWNER_AUTH_HISTORY_TRACKING_START_BLOCK_NUM;
        }

        public float getBEOWULF_OWNER_UPDATE_LIMIT() {
            return BEOWULF_OWNER_UPDATE_LIMIT;
        }

        public float getBEOWULF_VESTING_WITHDRAW_INTERVALS() {
            return BEOWULF_VESTING_WITHDRAW_INTERVALS;
        }

        public float getBEOWULF_VESTING_WITHDRAW_INTERVAL_SECONDS() {
            return BEOWULF_VESTING_WITHDRAW_INTERVAL_SECONDS;
        }

        public Symbol getBEOWULF_SYMBOL() {
            return BEOWULF_SYMBOL;
        }

        public Symbol getVESTS_SYMBOL() {
            return VESTS_SYMBOL;
        }

        public String getBEOWULF_VIRTUAL_SCHEDULE_LAP_LENGTH2() {
            return BEOWULF_VIRTUAL_SCHEDULE_LAP_LENGTH2;
        }

        public float getBEOWULF_1_BEOWULF() {
            return BEOWULF_1_BEOWULF;
        }

        public float getBEOWULF_1_VESTS() {
            return BEOWULF_1_VESTS;
        }

        public float getBEOWULF_MAX_TOKEN_PER_ACCOUNT() {
            return BEOWULF_MAX_TOKEN_PER_ACCOUNT;
        }

        public float getBEOWULF_MIN_PERMANENT_SUPERNODE_FUND() {
            return BEOWULF_MIN_PERMANENT_SUPERNODE_FUND;
        }

        public float getBEOWULF_MAX_TOKEN_NAME_LENGTH() {
            return BEOWULF_MAX_TOKEN_NAME_LENGTH;
        }

        public float getBEOWULF_MIN_TOKEN_NAME_LENGTH() {
            return BEOWULF_MIN_TOKEN_NAME_LENGTH;
        }

        public String getBEOWULF_SYMBOL_BEOWULF() {
            return BEOWULF_SYMBOL_BEOWULF;
        }

        public String getBEOWULF_SYMBOL_WD() {
            return BEOWULF_SYMBOL_WD;
        }

        public String getBEOWULF_SYMBOL_VESTS() {
            return BEOWULF_SYMBOL_VESTS;
        }

        public float getBEOWULF_BLOCK_REWARD_GAP() {
            return BEOWULF_BLOCK_REWARD_GAP;
        }

        public float getBEOWULF_ITEM_QUEUE_SIZE() {
            return BEOWULF_ITEM_QUEUE_SIZE;
        }

        public float getBEOWULF_FLUSH_INTERVAL() {
            return BEOWULF_FLUSH_INTERVAL;
        }

        // Setter Methods

        public void setIS_TEST_NET(boolean IS_TEST_NET) {
            this.IS_TEST_NET = IS_TEST_NET;
        }

        public void setSMT_TOKEN_CREATION_FEE(float SMT_TOKEN_CREATION_FEE) {
            this.SMT_TOKEN_CREATION_FEE = SMT_TOKEN_CREATION_FEE;
        }

        public void setWD_SYMBOL(Symbol WD_SYMBOL) {
            this.WD_SYMBOL = this.WD_SYMBOL;
        }

        public void setBEOWULF_100_PERCENT(float BEOWULF_100_PERCENT) {
            this.BEOWULF_100_PERCENT = BEOWULF_100_PERCENT;
        }

        public void setBEOWULF_1_PERCENT(float BEOWULF_1_PERCENT) {
            this.BEOWULF_1_PERCENT = BEOWULF_1_PERCENT;
        }

        public void setBEOWULF_ADDRESS_PREFIX(String BEOWULF_ADDRESS_PREFIX) {
            this.BEOWULF_ADDRESS_PREFIX = BEOWULF_ADDRESS_PREFIX;
        }

        public void setBEOWULF_BLOCKCHAIN_HARDFORK_VERSION(String BEOWULF_BLOCKCHAIN_HARDFORK_VERSION) {
            this.BEOWULF_BLOCKCHAIN_HARDFORK_VERSION = BEOWULF_BLOCKCHAIN_HARDFORK_VERSION;
        }

        public void setBEOWULF_BLOCKCHAIN_VERSION(String BEOWULF_BLOCKCHAIN_VERSION) {
            this.BEOWULF_BLOCKCHAIN_VERSION = BEOWULF_BLOCKCHAIN_VERSION;
        }

        public void setBEOWULF_BLOCK_INTERVAL(float BEOWULF_BLOCK_INTERVAL) {
            this.BEOWULF_BLOCK_INTERVAL = BEOWULF_BLOCK_INTERVAL;
        }

        public void setBEOWULF_BLOCKS_PER_DAY(float BEOWULF_BLOCKS_PER_DAY) {
            this.BEOWULF_BLOCKS_PER_DAY = BEOWULF_BLOCKS_PER_DAY;
        }

        public void setBEOWULF_BLOCKS_PER_YEAR(float BEOWULF_BLOCKS_PER_YEAR) {
            this.BEOWULF_BLOCKS_PER_YEAR = BEOWULF_BLOCKS_PER_YEAR;
        }

        public void setBEOWULF_CHAIN_ID(String BEOWULF_CHAIN_ID) {
            this.BEOWULF_CHAIN_ID = BEOWULF_CHAIN_ID;
        }

        public void setBEOWULF_GENESIS_TIME(String BEOWULF_GENESIS_TIME) {
            this.BEOWULF_GENESIS_TIME = BEOWULF_GENESIS_TIME;
        }

        public void setBEOWULF_HARDFORK_REQUIRED_SUPERNODES(float BEOWULF_HARDFORK_REQUIRED_SUPERNODES) {
            this.BEOWULF_HARDFORK_REQUIRED_SUPERNODES = BEOWULF_HARDFORK_REQUIRED_SUPERNODES;
        }

        public void setBEOWULF_INFLATION_NARROWING_PERIOD(float BEOWULF_INFLATION_NARROWING_PERIOD) {
            this.BEOWULF_INFLATION_NARROWING_PERIOD = BEOWULF_INFLATION_NARROWING_PERIOD;
        }

        public void setBEOWULF_INFLATION_RATE_START_PERCENT(float BEOWULF_INFLATION_RATE_START_PERCENT) {
            this.BEOWULF_INFLATION_RATE_START_PERCENT = BEOWULF_INFLATION_RATE_START_PERCENT;
        }

        public void setBEOWULF_INFLATION_RATE_STOP_PERCENT(float BEOWULF_INFLATION_RATE_STOP_PERCENT) {
            this.BEOWULF_INFLATION_RATE_STOP_PERCENT = BEOWULF_INFLATION_RATE_STOP_PERCENT;
        }

        public void setBEOWULF_INIT_MINER_NAME(String BEOWULF_INIT_MINER_NAME) {
            this.BEOWULF_INIT_MINER_NAME = BEOWULF_INIT_MINER_NAME;
        }

        public void setBEOWULF_INIT_PUBLIC_KEY_STR(String BEOWULF_INIT_PUBLIC_KEY_STR) {
            this.BEOWULF_INIT_PUBLIC_KEY_STR = BEOWULF_INIT_PUBLIC_KEY_STR;
        }

        public void setBEOWULF_INIT_SUPPLY(float BEOWULF_INIT_SUPPLY) {
            this.BEOWULF_INIT_SUPPLY = BEOWULF_INIT_SUPPLY;
        }

        public void setWD_INIT_SUPPLY(float WD_INIT_SUPPLY) {
            this.WD_INIT_SUPPLY = WD_INIT_SUPPLY;
        }

        public void setBEOWULF_IRREVERSIBLE_THRESHOLD(float BEOWULF_IRREVERSIBLE_THRESHOLD) {
            this.BEOWULF_IRREVERSIBLE_THRESHOLD = BEOWULF_IRREVERSIBLE_THRESHOLD;
        }

        public void setBEOWULF_MAX_ACCOUNT_NAME_LENGTH(float BEOWULF_MAX_ACCOUNT_NAME_LENGTH) {
            this.BEOWULF_MAX_ACCOUNT_NAME_LENGTH = BEOWULF_MAX_ACCOUNT_NAME_LENGTH;
        }

        public void setBEOWULF_MAX_ACCOUNT_SUPERNODE_VOTES(float BEOWULF_MAX_ACCOUNT_SUPERNODE_VOTES) {
            this.BEOWULF_MAX_ACCOUNT_SUPERNODE_VOTES = BEOWULF_MAX_ACCOUNT_SUPERNODE_VOTES;
        }

        public void setBEOWULF_MAX_AUTHORITY_MEMBERSHIP(float BEOWULF_MAX_AUTHORITY_MEMBERSHIP) {
            this.BEOWULF_MAX_AUTHORITY_MEMBERSHIP = BEOWULF_MAX_AUTHORITY_MEMBERSHIP;
        }

        public void setBEOWULF_SOFT_MAX_BLOCK_SIZE(float BEOWULF_SOFT_MAX_BLOCK_SIZE) {
            this.BEOWULF_SOFT_MAX_BLOCK_SIZE = BEOWULF_SOFT_MAX_BLOCK_SIZE;
        }

        public void setBEOWULF_MAX_MEMO_SIZE(float BEOWULF_MAX_MEMO_SIZE) {
            this.BEOWULF_MAX_MEMO_SIZE = BEOWULF_MAX_MEMO_SIZE;
        }

        public void setBEOWULF_MAX_SUPERNODES(float BEOWULF_MAX_SUPERNODES) {
            this.BEOWULF_MAX_SUPERNODES = BEOWULF_MAX_SUPERNODES;
        }

        public void setBEOWULF_MAX_PERMANENT_SUPERNODES_HF0(float BEOWULF_MAX_PERMANENT_SUPERNODES_HF0) {
            this.BEOWULF_MAX_PERMANENT_SUPERNODES_HF0 = BEOWULF_MAX_PERMANENT_SUPERNODES_HF0;
        }

        public void setBEOWULF_MAX_RUNNER_SUPERNODES_HF0(float BEOWULF_MAX_RUNNER_SUPERNODES_HF0) {
            this.BEOWULF_MAX_RUNNER_SUPERNODES_HF0 = BEOWULF_MAX_RUNNER_SUPERNODES_HF0;
        }

        public void setBEOWULF_MAX_SHARE_SUPPLY(float BEOWULF_MAX_SHARE_SUPPLY) {
            this.BEOWULF_MAX_SHARE_SUPPLY = BEOWULF_MAX_SHARE_SUPPLY;
        }

        public void setBEOWULF_MAX_SIG_CHECK_DEPTH(float BEOWULF_MAX_SIG_CHECK_DEPTH) {
            this.BEOWULF_MAX_SIG_CHECK_DEPTH = BEOWULF_MAX_SIG_CHECK_DEPTH;
        }

        public void setBEOWULF_MAX_SIG_CHECK_ACCOUNTS(float BEOWULF_MAX_SIG_CHECK_ACCOUNTS) {
            this.BEOWULF_MAX_SIG_CHECK_ACCOUNTS = BEOWULF_MAX_SIG_CHECK_ACCOUNTS;
        }

        public void setBEOWULF_MAX_TIME_UNTIL_EXPIRATION(float BEOWULF_MAX_TIME_UNTIL_EXPIRATION) {
            this.BEOWULF_MAX_TIME_UNTIL_EXPIRATION = BEOWULF_MAX_TIME_UNTIL_EXPIRATION;
        }

        public void setBEOWULF_MAX_TRANSACTION_SIZE(float BEOWULF_MAX_TRANSACTION_SIZE) {
            this.BEOWULF_MAX_TRANSACTION_SIZE = BEOWULF_MAX_TRANSACTION_SIZE;
        }

        public void setBEOWULF_MAX_UNDO_HISTORY(float BEOWULF_MAX_UNDO_HISTORY) {
            this.BEOWULF_MAX_UNDO_HISTORY = BEOWULF_MAX_UNDO_HISTORY;
        }

        public void setBEOWULF_MAX_VOTED_SUPERNODES_HF0(float BEOWULF_MAX_VOTED_SUPERNODES_HF0) {
            this.BEOWULF_MAX_VOTED_SUPERNODES_HF0 = BEOWULF_MAX_VOTED_SUPERNODES_HF0;
        }

        public void setBEOWULF_MIN_SUPERNODE_FUND(float BEOWULF_MIN_SUPERNODE_FUND) {
            this.BEOWULF_MIN_SUPERNODE_FUND = BEOWULF_MIN_SUPERNODE_FUND;
        }

        public void setBEOWULF_MIN_TRANSACTION_FEE(float BEOWULF_MIN_TRANSACTION_FEE) {
            this.BEOWULF_MIN_TRANSACTION_FEE = BEOWULF_MIN_TRANSACTION_FEE;
        }

        public void setBEOWULF_MIN_ACCOUNT_CREATION_FEE(float BEOWULF_MIN_ACCOUNT_CREATION_FEE) {
            this.BEOWULF_MIN_ACCOUNT_CREATION_FEE = BEOWULF_MIN_ACCOUNT_CREATION_FEE;
        }

        public void setBEOWULF_MIN_ACCOUNT_NAME_LENGTH(float BEOWULF_MIN_ACCOUNT_NAME_LENGTH) {
            this.BEOWULF_MIN_ACCOUNT_NAME_LENGTH = BEOWULF_MIN_ACCOUNT_NAME_LENGTH;
        }

        public void setBEOWULF_MIN_BLOCK_SIZE(float BEOWULF_MIN_BLOCK_SIZE) {
            this.BEOWULF_MIN_BLOCK_SIZE = BEOWULF_MIN_BLOCK_SIZE;
        }

        public void setBEOWULF_NULL_ACCOUNT(String BEOWULF_NULL_ACCOUNT) {
            this.BEOWULF_NULL_ACCOUNT = BEOWULF_NULL_ACCOUNT;
        }

        public void setBEOWULF_NUM_INIT_MINERS(float BEOWULF_NUM_INIT_MINERS) {
            this.BEOWULF_NUM_INIT_MINERS = BEOWULF_NUM_INIT_MINERS;
        }

        public void setBEOWULF_OWNER_AUTH_HISTORY_TRACKING_START_BLOCK_NUM(float BEOWULF_OWNER_AUTH_HISTORY_TRACKING_START_BLOCK_NUM) {
            this.BEOWULF_OWNER_AUTH_HISTORY_TRACKING_START_BLOCK_NUM = BEOWULF_OWNER_AUTH_HISTORY_TRACKING_START_BLOCK_NUM;
        }

        public void setBEOWULF_OWNER_UPDATE_LIMIT(float BEOWULF_OWNER_UPDATE_LIMIT) {
            this.BEOWULF_OWNER_UPDATE_LIMIT = BEOWULF_OWNER_UPDATE_LIMIT;
        }

        public void setBEOWULF_VESTING_WITHDRAW_INTERVALS(float BEOWULF_VESTING_WITHDRAW_INTERVALS) {
            this.BEOWULF_VESTING_WITHDRAW_INTERVALS = BEOWULF_VESTING_WITHDRAW_INTERVALS;
        }

        public void setBEOWULF_VESTING_WITHDRAW_INTERVAL_SECONDS(float BEOWULF_VESTING_WITHDRAW_INTERVAL_SECONDS) {
            this.BEOWULF_VESTING_WITHDRAW_INTERVAL_SECONDS = BEOWULF_VESTING_WITHDRAW_INTERVAL_SECONDS;
        }

        public void setBEOWULF_SYMBOL(Symbol BEOWULF_SYMBOL) {
            this.BEOWULF_SYMBOL = BEOWULF_SYMBOL;
        }

        public void setVESTS_SYMBOL(Symbol VESTS_SYMBOL) {
            this.VESTS_SYMBOL = VESTS_SYMBOL;
        }

        public void setBEOWULF_VIRTUAL_SCHEDULE_LAP_LENGTH2(String BEOWULF_VIRTUAL_SCHEDULE_LAP_LENGTH2) {
            this.BEOWULF_VIRTUAL_SCHEDULE_LAP_LENGTH2 = BEOWULF_VIRTUAL_SCHEDULE_LAP_LENGTH2;
        }

        public void setBEOWULF_1_BEOWULF(float BEOWULF_1_BEOWULF) {
            this.BEOWULF_1_BEOWULF = BEOWULF_1_BEOWULF;
        }

        public void setBEOWULF_1_VESTS(float BEOWULF_1_VESTS) {
            this.BEOWULF_1_VESTS = BEOWULF_1_VESTS;
        }

        public void setBEOWULF_MAX_TOKEN_PER_ACCOUNT(float BEOWULF_MAX_TOKEN_PER_ACCOUNT) {
            this.BEOWULF_MAX_TOKEN_PER_ACCOUNT = BEOWULF_MAX_TOKEN_PER_ACCOUNT;
        }

        public void setBEOWULF_MIN_PERMANENT_SUPERNODE_FUND(float BEOWULF_MIN_PERMANENT_SUPERNODE_FUND) {
            this.BEOWULF_MIN_PERMANENT_SUPERNODE_FUND = BEOWULF_MIN_PERMANENT_SUPERNODE_FUND;
        }

        public void setBEOWULF_MAX_TOKEN_NAME_LENGTH(float BEOWULF_MAX_TOKEN_NAME_LENGTH) {
            this.BEOWULF_MAX_TOKEN_NAME_LENGTH = BEOWULF_MAX_TOKEN_NAME_LENGTH;
        }

        public void setBEOWULF_MIN_TOKEN_NAME_LENGTH(float BEOWULF_MIN_TOKEN_NAME_LENGTH) {
            this.BEOWULF_MIN_TOKEN_NAME_LENGTH = BEOWULF_MIN_TOKEN_NAME_LENGTH;
        }

        public void setBEOWULF_SYMBOL_BEOWULF(String BEOWULF_SYMBOL_BEOWULF) {
            this.BEOWULF_SYMBOL_BEOWULF = BEOWULF_SYMBOL_BEOWULF;
        }

        public void setBEOWULF_SYMBOL_WD(String BEOWULF_SYMBOL_WD) {
            this.BEOWULF_SYMBOL_WD = BEOWULF_SYMBOL_WD;
        }

        public void setBEOWULF_SYMBOL_VESTS(String BEOWULF_SYMBOL_VESTS) {
            this.BEOWULF_SYMBOL_VESTS = BEOWULF_SYMBOL_VESTS;
        }

        public void setBEOWULF_BLOCK_REWARD_GAP(float BEOWULF_BLOCK_REWARD_GAP) {
            this.BEOWULF_BLOCK_REWARD_GAP = BEOWULF_BLOCK_REWARD_GAP;
        }

        public void setBEOWULF_ITEM_QUEUE_SIZE(float BEOWULF_ITEM_QUEUE_SIZE) {
            this.BEOWULF_ITEM_QUEUE_SIZE = BEOWULF_ITEM_QUEUE_SIZE;
        }

        public void setBEOWULF_FLUSH_INTERVAL(float BEOWULF_FLUSH_INTERVAL) {
            this.BEOWULF_FLUSH_INTERVAL = BEOWULF_FLUSH_INTERVAL;
        }
    }
}
