# Official Java BEOWULF Library

`beowulf-java` is the official Beowulf library for Java.  

# Full Documentation
- Please have a look at the [Wiki](https://github.com/beowulf-foundation/beowulf-java/wiki) for full documentation, examples, operational details and other information.
- Or have a look at the JavaDoc.

# Communication
- Beside that you can also create an [Issue](https://github.com/beowulf-foundation/beowulf-java/issues) here at GitHub.

# Beowulf Java Api Wrapper

*A Java implementation of the Beowulf RPC Calls. Under the [GNU General Public Licence v3.0](https://raw.githubusercontent.com/beowulf-foundation/beowulf-java/master/LICENSE)*.

The api documentation can be found in the official beowulf developers portal:
https://beowulfchain.com/developer-guide/java  

# Main Functions Supported
1. CHAIN
- get_block
- get_transaction
- get_balance
- get operation
2. TRANSACTION
- broadcast_transaction
- create transaction transfer
- create account
- create token

#### Requirements
* Java 8
* Maven 3.5


## Maven
File: *pom.xml*
```Xml
<dependency>
    <groupId>com.beowulfchain</groupId>
    <artifactId>beowulfj-core</artifactId>
    <version>0.0.1</version>
</dependency>
```

# How to build & install
The project requires Maven and Java to be installed on your machine. It can be build with the default maven command:

>mvn clean install

The resulting JAR can be found in the target directory as usual. Please notice that some integration tests require different private keys. If you do not want to execute tests at all add *"-DskipTests=true"* to the mvn call which skips the test execution during the build.


#### Configuration
Create a new client instance of BeowulfJ
```java
// Define config:
NetworkProperties network = new Testnet();
BeowulfJConfig myConfig = BeowulfJConfig.getInstance();
myConfig.setResponseTimeout(100000);
// default account will be used if transaction doesn't define
myConfig.setDefaultAccount(new AccountName("default-account"));
myConfig.setNetwork(network);
// init beowulf node
myConfig.setDefaultBeowulfApiUri("https://testnet-bw.beowulfchain.com/rpc");


// Add and manage private keys follow base58 format:
AccountName user1 = new AccountName("user1");
ImmutablePair<PrivateKeyType, String> defaultAccountPrivkey = new ImmutablePair<>(PrivateKeyType.OWNER, "5Hv****");
ImmutablePair<PrivateKeyType, String> user1PrivKey = new ImmutablePair<>(PrivateKeyType.OWNER, "5Hv****");
myConfig.getPrivateKeyStorage().addPrivateKeyToAccount(myConfig.getDefaultAccount(), defaultAccountPrivkey);
myConfig.getPrivateKeyStorage().addPrivateKeyToAccount(user1, user1PrivKey);

// Create a new apiWrapper with your config object.
BeowulfJ beowulfJ = BeowulfJ.getInstance();
```

#### Example Usage
##### Creating a wallet
```java
/*
 * Create new account and get private key
 */
AccountName creator = new AccountName("creator");
AccountName newAccount = new AccountName("newaccount");

// create new keypair
ECKey ecKey = new ECKey().decompress();
// get private key
String newprivkey = BeowulfJUtils.getPrivkeyBase58FromEckey(ecKey);
System.out.println(newprivkey);
// add new account to memory storage
ImmutablePair<PrivateKeyType, String> newAccountKey = new ImmutablePair<>(PrivateKeyType.OWNER, newprivkey);
myConfig.getPrivateKeyStorage().addAccount(newAccount, Collections.singletonList(newAccountKey));

// get public key
PublicKey publicKey = new PublicKey(ecKey);
System.out.println(publicKey.getAddressFromPublicKey());

// create authority for new account by public key
// more authority if needed
Authority owner = new Authority();
Map<PublicKey, Integer> keyAuths = new HashMap<>();
keyAuths.put(publicKey, 1);
owner.setKeyAuths(keyAuths);

String jsonMetadata = "{}";

AccountCreateOperation accountCreateOperation = beowulfJ.createAccount(creator, network.getAccountCreationFee(), newAccount, owner, "{}");
TransactionId transactionId1 = beowulfJ.signAndBroadcast(Collections.singletonList(accountCreateOperation));
System.out.println("Transaction id: " + transactionId1);
```

##### Signing and pushing a transaction

```java
/*
 * Transfer 1.0 W from sender to receiver
 */
AccountName from = new AccountName("sender");
AccountName to = new AccountName("receiver");
Asset amount = Asset.createSmtAsset(new BigDecimal("1.00000"), "W");
TransferOperation transferOperation = beowulfJ.transfer(from, to, amount, network.getTransactionFee(), "Transfer 1.0 W from sender to receiver");
TransactionId transactionId = beowulfJ.signAndBroadcast(Collections.singletonList(transferOperation));
System.out.println("Transaction id: " + transactionId);
```

##### Getting a block
```java
/*
 * Get block from block number
 */
Block block = beowulfJ.getBlock(165099L);
```

##### Getting transaction details
```java
TransactionId transactionId = block.getTransactionIds().get(0);
CompletedTransaction transaction = beowulfJ.getTransactionDetail(transactionId.toString());
```

##### Getting operation in transaction
```java
List<Operation> operations = transaction.getOperations();
Operation operation = operations.get(0);
if (operation instanceof TransferOperation){
    TransferOperation transferOperation = (TransferOperation) operation;
    System.out.println(transferOperation.toString());
}
```

##### Getting balance of specific asset in an account
```java
AccountName accountToGetBalance = new AccountName("sender");
AssetInfo assetInfo = new AssetInfo("BWF", UInteger.valueOf(5));
Asset asset = beowulfJ.getBalance(accountToGetBalance, assetInfo);
System.out.println(asset.toString());
```

#### Notes
* All methods are synchronous and blocking.
* All methods will throw a catchable BeowulfException.

# Bugs and Feedback
For bugs or feature requests please create a [GitHub Issue](https://github.com/beowulf-foundation/beowulf-java/issues).  

# Example
The [sample module](https://github.com/beowulf-foundation/beowulf-java/tree/master/sample) of the BeowulfJ project provides showcases for the most common acitivies and operations users want to perform. 

Beside that you can find a lot of snippets and examples in the different [Wiki sections](https://github.com/beowulf-foundation/beowulf-java/wiki).  
