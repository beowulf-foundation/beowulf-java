# Official Java BEOWULF Library

`beowulf-java` is the official Beowulf library for Java.  

## Main Functions Supported
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

## Requirements
* Java 8
* Maven 3.5

### Maven
File: *pom.xml*
```Xml
<dependency>
    <groupId>com.beowulfchain</groupId>
    <artifactId>beowulfj-core</artifactId>
    <version>0.0.4</version>
</dependency>
```

## Installation
The project requires Maven and Java to be installed on your machine. It can be build with the default maven command:

>mvn clean install

The resulting JAR can be found in the target directory as usual. Please notice that some integration tests require different private keys. If you do not want to execute tests at all add *"-DskipTests=true"* to the mvn call which skips the test execution during the build.


## Configuration
Create a new client instance of BeowulfJ
```java
// Define config:
NetworkProperties network = new Mainnet();
BeowulfJConfig myConfig = BeowulfJConfig.getInstance();
myConfig.setResponseTimeout(100000);
// default account will be used if transaction doesn't define
myConfig.setDefaultAccount(new AccountName("default-account"));
myConfig.setNetwork(network);
// init beowulf node
myConfig.setDefaultBeowulfApiUri("https://bw.beowulfchain.com/rpc");


// Add and manage private keys follow base58 format:
AccountName user1 = new AccountName("user1");
ImmutablePair<PrivateKeyType, String> defaultAccountPrivkey = new ImmutablePair<>(PrivateKeyType.OWNER, "5Hv****");
ImmutablePair<PrivateKeyType, String> user1PrivKey = new ImmutablePair<>(PrivateKeyType.OWNER, "5Hv****");
myConfig.getPrivateKeyStorage().addPrivateKeyToAccount(myConfig.getDefaultAccount(), defaultAccountPrivkey);
myConfig.getPrivateKeyStorage().addPrivateKeyToAccount(user1, user1PrivKey);

// Create a new apiWrapper with your config object.
BeowulfJ beowulfJ = BeowulfJ.getInstance();
```

## Example Usage

##### Get block
```java
/*
 * Get block from block number
 */
Block block = beowulfJ.getBlock(165099L);
```

##### Get transaction
```java
TransactionId transactionId = block.getTransactionIds().get(0);
CompletedTransaction transaction = beowulfJ.getTransactionDetail(transactionId.toString());
```

##### Transfer native coin
###### Transfer BWF
```java
/*
 * Transfer 1.0 W from sender to receiver
 */
AccountName from = new AccountName("sender");
AccountName to = new AccountName("receiver");
Asset amount = Asset.createSmtAsset(new BigDecimal("1.00000"), "BWF");
TransferOperation transferOperation = beowulfJ.transfer(from, to, amount, network.getTransactionFee(), "Transfer 1.0 BWF from sender to receiver");
TransactionId transactionId = beowulfJ.signAndBroadcast(Collections.singletonList(transferOperation));
System.out.println("Transaction id: " + transactionId);
```

###### Transfer W
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

##### Create wallet
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
