package my.sample.project;

import com.beowulfchain.beowulfj.BeowulfJ;
import com.beowulfchain.beowulfj.base.models.Block;
import com.beowulfchain.beowulfj.chain.CompletedTransaction;
import com.beowulfchain.beowulfj.chain.NetworkProperties;
import com.beowulfchain.beowulfj.chain.network.Testnet;
import com.beowulfchain.beowulfj.configuration.BeowulfJConfig;
import com.beowulfchain.beowulfj.enums.PrivateKeyType;
import com.beowulfchain.beowulfj.exceptions.BeowulfCommunicationException;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.exceptions.BeowulfResponseException;
import com.beowulfchain.beowulfj.plugins.apis.condenser.models.AccountHistoryReturn;
import com.beowulfchain.beowulfj.protocol.*;
import com.beowulfchain.beowulfj.protocol.operations.AccountCreateOperation;
import com.beowulfchain.beowulfj.protocol.operations.Operation;
import com.beowulfchain.beowulfj.protocol.operations.SmtCreateOperation;
import com.beowulfchain.beowulfj.protocol.operations.TransferOperation;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import eu.bittrade.crypto.core.ECKey;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joou.UInteger;
import org.joou.ULong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides shows some common BeowulfJ commands.
 */
public class BeowulfJUsageExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeowulfJUsageExample.class);

    /**
     * Called at startup.
     *
     * @param args The arguments to set.
     * @throws java.lang.Exception Main Exception
     */
    public static void main(String args[]) throws Exception {
        try {
            // #########################################################################
            // ## Configure BeowulfJ to fit your needs ###################################
            // #########################################################################

            // Change the default settings if needed:
            NetworkProperties network = new Testnet();
            BeowulfJConfig myConfig = BeowulfJConfig.getInstance();
            myConfig.setResponseTimeout(100000);
            myConfig.setDefaultAccount(new AccountName("sender"));
            myConfig.setNetwork(network);
            myConfig.setDefaultBeowulfApiUri("https://testnet-bw.beowulfchain.com/rpc");

            // Add and manage private keys: replace your private key here.
            ImmutablePair<PrivateKeyType, String> defaultAccountPrivkey = new ImmutablePair<>(PrivateKeyType.OWNER, "5Hxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            myConfig.getPrivateKeyStorage().addPrivateKeyToAccount(myConfig.getDefaultAccount(), defaultAccountPrivkey);

            // Create a new apiWrapper with your config object.
            BeowulfJ beowulfJ = BeowulfJ.getInstance();

            // #########################################################################
            // ## EXECUTE SIMPLYFIED OPERATIONS ########################################
            // #########################################################################

            /*
             * Get data from blockchain
             */

            /*
             * Get block by block number
             */
            Block block = beowulfJ.getBlock(165099L);
            System.out.println(block.toString());

            /*
             * Get transaction detail from transactionid
             */
            TransactionId trx_id = block.getTransactionIds().get(0);
            CompletedTransaction transaction = beowulfJ.getTransactionDetail(trx_id.toString());

            /*
             * Get operation detail from transaction
             */
            List<Operation> operations = transaction.getOperations();
            Operation operation = operations.get(0);
            if (operation instanceof TransferOperation) {
                TransferOperation transferOperation = (TransferOperation) operation;
                System.out.println(transferOperation.toString());
            }

            /*
             * Get account balance
             */
            AccountName accountToGetBalance = new AccountName("sender");
            AssetInfo assetInfo = new AssetInfo("BWF", UInteger.valueOf(5));
            Asset asset = beowulfJ.getBalance(accountToGetBalance, assetInfo);
            System.out.println(asset.toString());

            /*
             * transfer 1.0 W from sender to receiver
             */
            AccountName from = new AccountName("sender");
            AccountName to = new AccountName("receiver");
            Asset amount = Asset.createSmtAsset(new BigDecimal("1.00000"), "W");
            TransferOperation transferOperation = beowulfJ.transfer(from, to, amount, network.getTransactionFee(), "Transfer 1.0 W from sender to receiver");
            TransactionId transactionId = beowulfJ.signAndBroadcast(Collections.singletonList(transferOperation));
            System.out.println("Transaction id: " + transactionId);

            /*
             * Create new account and get private key
             */
            AccountName creator = new AccountName("sender");
            AccountName newAccount = new AccountName("newaccount");

            // create new keypair
            ECKey ecKey = new ECKey().decompress();
            // get private key
            String newprivkey = BeowulfJUtils.fromEckeyToWif(ecKey);
            System.out.println(newprivkey);
            // add new account to memory storage
            ImmutablePair<PrivateKeyType, String> newAccountKey = new ImmutablePair<>(PrivateKeyType.OWNER, newprivkey);
            myConfig.getPrivateKeyStorage().addAccount(newAccount, Collections.singletonList(newAccountKey));

            // get public key
            PublicKey publicKey = new PublicKey(ecKey);
            System.out.println(publicKey.getAddressFromPublicKey());

            // create authority for new account by public key
            Authority owner = new Authority();
            Map<PublicKey, Integer> keyAuths = new HashMap<>();
            keyAuths.put(publicKey, 1);
            owner.setKeyAuths(keyAuths);

            AccountCreateOperation accountCreateOperation = beowulfJ.createAccount(creator, network.getAccountCreationFee(), newAccount, owner, "{}");
            TransactionId transactionId1 = beowulfJ.signAndBroadcast(Collections.singletonList(accountCreateOperation));
            System.out.println("Transaction id: " + transactionId1);

            /*
             * Create smt by code. Only Beowulf account can create new token, pls contact us for more information
             */
            AssetInfo newAsset = new AssetInfo("BTC", UInteger.valueOf(5));
            SmtCreateOperation smtCreateOperation = beowulfJ.smtCreate(creator, creator, newAsset, network.getSmtCreationFee(), 1000000L);
            TransactionId transactionId2 = beowulfJ.signAndBroadcast(Collections.singletonList(smtCreateOperation));
            System.out.println("Transaction id: " + transactionId2);

            // #########################################################################
            // ## EXECUTE READ OPERATIONS AGAINS THE NDOE ##############################
            // #########################################################################
            // Let's have a look at the account history of alice:
            List<AccountHistoryReturn> accountHistories = beowulfJ.getAccountHistory(new AccountName("sender"), ULong.valueOf(5),
                    UInteger.valueOf(5));
            if (accountHistories.get(0).getAppliedOperation().getOp() instanceof AccountCreateOperation) {
                AccountCreateOperation accountCreateOperation1 = (AccountCreateOperation) (accountHistories.get(0).getAppliedOperation().getOp());
                LOGGER.info("The account {} has been created by {}.", "sender", accountCreateOperation1.getCreator());
            }
        } catch (BeowulfResponseException e) {
            LOGGER.error("An error occured.", e);
            LOGGER.error("The error code is {}", e.getCode());
        } catch (BeowulfCommunicationException e) {
            LOGGER.error("A communication error occured!", e);
        } catch (BeowulfInvalidTransactionException e) {
            LOGGER.error("There was a problem to sign a transaction.", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
