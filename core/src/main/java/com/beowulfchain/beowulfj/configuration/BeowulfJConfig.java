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
    private AssetSymbolType dollarSymbol;
    private AssetSymbolType tokenSymbol;
    private AssetSymbolType vestsSymbol;
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
        this.setBeowulfJWeight(Short.valueOf("250"));
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
        return endpointURIs.get(((int) (selector % endpointURIs.size())));
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
     * Get the currently configured {@link AssetSymbolType} for dollars (e.g.
     * W). The configured symbol type is used to validate M fields of
     * operations.
     *
     * @return The currently configured {@link AssetSymbolType} for dollars.
     */
    public AssetSymbolType getDollarSymbol() {
        return dollarSymbol;
    }

    /**
     * Override the default <code>dollarSymbol</code>. The configured symbol
     * type is used to validate M fields of operations.
     *
     * @param dollarSymbol The {@link AssetSymbolType} for dollars to set.
     */
    public void setDollarSymbol(AssetSymbolType dollarSymbol) {
        this.dollarSymbol = dollarSymbol;
    }

    /**
     * Get the currently configured {@link AssetSymbolType} for tokens (e.g.
     * BWF). The configured symbol type is used to validate M fields of
     * operations.
     *
     * @return The currently configured {@link AssetSymbolType} for tokens.
     */
    public AssetSymbolType getTokenSymbol() {
        return tokenSymbol;
    }

    /**
     * Override the default <code>tokenSymbol</code>. The configured symbol type
     * is used to validate token fields of operations.
     *
     * @param tokenSymbol The {@link AssetSymbolType} for tokens to set.
     */
    public void setTokenSymbol(AssetSymbolType tokenSymbol) {
        this.tokenSymbol = tokenSymbol;
    }

    /**
     * Get the currently configured {@link AssetSymbolType} for M. The
     * configured symbol type is used to validate M fields of operations.
     *
     * @return The currently configured {@link AssetSymbolType} for M.
     */
    public AssetSymbolType getVestsSymbol() {
        return vestsSymbol;
    }

    /**
     * Override the default <code>vestsSymbol</code>. The configured symbol type
     * is used to validate M fields of operations.
     *
     * @param vestsSymbol The {@link AssetSymbolType} for M to set.
     */
    public void setVestsSymbol(AssetSymbolType vestsSymbol) {
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
        this.setDollarSymbol(AssetSymbolType.getValue(network.getWd_symbol()));
        this.setTokenSymbol(AssetSymbolType.getValue(network.getBeowulf_symbol()));
        this.setVestsSymbol(AssetSymbolType.getValue(network.getVests_symbol()));
    }
}
