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

import com.beowulfchain.beowulfj.BaseIT;
import com.beowulfchain.beowulfj.BeowulfJ;
import com.beowulfchain.beowulfj.IntegrationTest;
import com.beowulfchain.beowulfj.base.models.Account;
import com.beowulfchain.beowulfj.base.models.Block;
import com.beowulfchain.beowulfj.chain.SignedTransaction;
import com.beowulfchain.beowulfj.communication.CommunicationHandler;
import com.beowulfchain.beowulfj.configuration.BeowulfJConfig;
import com.beowulfchain.beowulfj.enums.PrivateKeyType;
import com.beowulfchain.beowulfj.exceptions.BeowulfCommunicationException;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.exceptions.BeowulfResponseException;
import com.beowulfchain.beowulfj.plugins.apis.condenser.models.AccountHistoryReturn;
import com.beowulfchain.beowulfj.plugins.apis.condenser.models.FindSmtTokenByName;
import com.beowulfchain.beowulfj.plugins.apis.database.models.Config;
import com.beowulfchain.beowulfj.plugins.apis.database.models.DynamicGlobalProperty;
import com.beowulfchain.beowulfj.protocol.AccountName;
import com.beowulfchain.beowulfj.protocol.Asset;
import com.beowulfchain.beowulfj.protocol.BlockHeader;
import com.beowulfchain.beowulfj.protocol.PublicKey;
import com.beowulfchain.beowulfj.protocol.enums.AssetSymbolType;
import com.beowulfchain.beowulfj.protocol.operations.Operation;
import com.beowulfchain.beowulfj.protocol.operations.TransferOperation;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CondenserApiIT extends BaseIT {
    private static CommunicationHandler COMMUNICATION_HANDLER;

    /**
     * Setup the test environment.
     *
     * @throws BeowulfCommunicationException If a communication error occurs.
     */
    @BeforeClass
    public static void init() throws BeowulfCommunicationException {
        setupIntegrationTestEnvironment();

        COMMUNICATION_HANDLER = new CommunicationHandler();
    }

    /**
     * @throws BeowulfCommunicationException If a communication error occurs.
     * @throws BeowulfResponseException      If the response is an error.
     */
    @Category({IntegrationTest.class})
    @Test
    public void testGetHardforkVersion() throws BeowulfCommunicationException, BeowulfResponseException {
        CondenserApi.getHardforkVersion(COMMUNICATION_HANDLER);
    }

    @Category({IntegrationTest.class})
    @Test
    public void testGetBlock() throws BeowulfCommunicationException, BeowulfResponseException {
        Block block = CondenserApi.getBlock(COMMUNICATION_HANDLER, 11331);
        System.out.println(block.toString());
    }

    @Category({IntegrationTest.class})
    @Test
    public void testGetBlockHeader() throws BeowulfCommunicationException, BeowulfResponseException {
        BlockHeader blockHeader = CondenserApi.getBlockHeader(COMMUNICATION_HANDLER, 11331);
        System.out.println(blockHeader.toString());
    }

    @Category({IntegrationTest.class})
    @Test
    public void testGetConfig() throws BeowulfCommunicationException, BeowulfResponseException {
        Config config = CondenserApi.getConfig(COMMUNICATION_HANDLER);
        System.out.println(config.toString());
    }

    @Category({IntegrationTest.class})
    @Test
    @Ignore
    public void testGetAccounts() throws BeowulfCommunicationException, BeowulfResponseException {
        AccountName initminer = new AccountName("trongcau1");
        AccountName alice = new AccountName("alice");
        List<Account> accounts = CondenserApi.getAccounts(COMMUNICATION_HANDLER, Arrays.asList(initminer, alice));
        System.out.println(accounts.get(1));
    }

    @Category({IntegrationTest.class})
    @Test
    public void testGetTransactionHex() throws BeowulfCommunicationException, BeowulfResponseException, BeowulfInvalidTransactionException {
        // Change the default settings if needed:
        BeowulfJConfig myConfig = BeowulfJConfig.getInstance();
        myConfig.setResponseTimeout(100000);
        myConfig.setDefaultAccount(new AccountName("alice"));

        // Add and manage private keys:
        List<ImmutablePair<PrivateKeyType, String>> privateKeys = new ArrayList<>();
        privateKeys.add(new ImmutablePair<>(PrivateKeyType.OWNER, "5Jx7zAAmyUUbWXKHVvY8khB5BaZ875wJys1ajcD2Y5c6BF8EzMZ"));
        // ... add more keys if needed.

        myConfig.getPrivateKeyStorage().addAccount(myConfig.getDefaultAccount(), privateKeys);

        BeowulfJ beowulfJ = BeowulfJ.getInstance();
        AccountName from = new AccountName("alice");
        AccountName to = new AccountName("trongcauta");
        Asset amount = new Asset(new BigDecimal("1.000"), AssetSymbolType.BWF);
        Asset fee = new Asset(new BigDecimal("1.000"), AssetSymbolType.W);
        String memo = "Hello @trongcauta - I've send you one BWF.";
        TransferOperation transferOperation = new TransferOperation(from, to, amount, fee, memo);
        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(transferOperation);
        DynamicGlobalProperty globalProperties = beowulfJ.getDynamicGlobalProperties();
        SignedTransaction signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations,
                null);
        String txHex = CondenserApi.getTransactionHex(COMMUNICATION_HANDLER, signedTransaction);
        System.out.println(txHex);
    }

    @Category({IntegrationTest.class})
    @Test
    public void testFindSmtTokenByName() throws BeowulfCommunicationException, BeowulfResponseException, BeowulfInvalidTransactionException {

        List<FindSmtTokenByName> tokenInfos = BeowulfJ.getInstance().findSmtTokenByName(Collections.singletonList("ABC"));
    }

    /**
     * Test the
     * {@link CondenserApi#getKeyReferences(CommunicationHandler, List)}
     * method.
     *
     * @throws BeowulfCommunicationException If a communication error occurs.
     * @throws BeowulfResponseException      If the response is an error.
     */
    @Category({IntegrationTest.class})
    @Test
    @Ignore
    public void testGetAccountByKey() throws BeowulfCommunicationException, BeowulfResponseException {
        final List<String> accountNames = CondenserApi
                .getKeyReferences(COMMUNICATION_HANDLER, Collections.singletonList(new PublicKey("BEO5Hxd68mEqTKQNUSELm19ZYCtCEKqPdMn3uXCMk5Qx3Y58umxKP")));

        assertThat(accountNames.size(), equalTo(1));
        assertThat(accountNames.get(0), equalTo("alice"));
    }


    @Category({IntegrationTest.class})
    @Test
    @Ignore
    @Deprecated
    public void testGetAccountHistory() throws BeowulfCommunicationException, BeowulfResponseException {
        String account = "alice";
        long start = 5;
        long limit = 5;
        List<AccountHistoryReturn> accountsHistory = CondenserApi.getAccountHistory(COMMUNICATION_HANDLER, account, start, limit);
        assertThat(accountsHistory.get(0).getSequence().longValue(), equalTo(1L));

    }


}
