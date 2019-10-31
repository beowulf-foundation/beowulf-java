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
package com.beowulfchain.beowulfj.chain;

import com.beowulfchain.beowulfj.base.models.BlockId;
import com.beowulfchain.beowulfj.base.models.FutureExtensions;
import com.beowulfchain.beowulfj.base.models.Transaction;
import com.beowulfchain.beowulfj.configuration.BeowulfJConfig;
import com.beowulfchain.beowulfj.configuration.PrivateKeyStorage;
import com.beowulfchain.beowulfj.enums.PrivateKeyType;
import com.beowulfchain.beowulfj.enums.ValidationType;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.fc.TimePointSec;
import com.beowulfchain.beowulfj.interfaces.ByteTransformable;
import com.beowulfchain.beowulfj.interfaces.SignatureObject;
import com.beowulfchain.beowulfj.protocol.AccountName;
import com.beowulfchain.beowulfj.protocol.Authority;
import com.beowulfchain.beowulfj.protocol.TransactionId;
import com.beowulfchain.beowulfj.protocol.operations.Operation;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.bittrade.crypto.core.CryptoUtils;
import eu.bittrade.crypto.core.ECKey;
import eu.bittrade.crypto.core.Sha256Hash;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;
import org.joou.UShort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * this class present signed transaction, waiting for pushing to network
 */
public class SignedTransaction extends Transaction implements ByteTransformable, Serializable {
    private static final long serialVersionUID = 4821422578657270330L;
    private static final Logger LOGGER = LoggerFactory.getLogger(SignedTransaction.class);

    protected transient List<String> signatures;

    /**
     * This constructor is only used to create the POJO from a JSON response.
     */
    @JsonCreator
    SignedTransaction(@JsonProperty("ref_block_num") UShort refBlockNum,
                              @JsonProperty("ref_block_prefix") UInteger refBlockPrefix,
                              @JsonProperty("expiration") TimePointSec expirationDate,
                              @JsonProperty("operations") List<Operation> operations,
                              @JsonProperty("extensions") List<FutureExtensions> extensions,
                              @JsonProperty("signatures") List<String> signatures,
                              @JsonProperty("created_time") Long createdTime) {
        super(refBlockNum, refBlockPrefix, expirationDate, operations, extensions, createdTime);
        this.signatures = signatures;
    }

    /**
     * Create a new signed transaction object.
     *
     * @param refBlockNum    The reference block number (see
     *                       {@link #setRefBlockNum(UShort)}).
     * @param refBlockPrefix The reference block index (see
     *                       {@link #setRefBlockPrefix(UInteger)}).
     * @param expirationDate Define until when the transaction has to be processed (see
     *                       {@link #setExpirationDate(TimePointSec)}).
     * @param operations     A list of operations to process within this Transaction (see
     *                       {@link #setOperations(List)}).
     * @param extensions     Extensions are currently not supported and will be ignored
     *                       (see {@link #setExtensions(List)}).
     * @param createdTime    The timestamp create transaction.
     */
    public SignedTransaction(UShort refBlockNum, UInteger refBlockPrefix, TimePointSec expirationDate,
                             List<Operation> operations, List<FutureExtensions> extensions, Long createdTime) {
        super(refBlockNum, refBlockPrefix, expirationDate, operations, extensions, createdTime);
        this.signatures = new ArrayList<>();
    }

    /**
     * Like
     * {@link #SignedTransaction(BlockId, List, List)},
     * but allows you to provide a
     * {@link BlockId} object as the
     * reference block and will also set the <code>expirationDate</code> to the
     * latest possible time.
     *
     * @param blockId    The block reference (see {@link #setRefBlockNum(UShort)} and
     *                   {@link #setRefBlockPrefix(UInteger)}).
     * @param operations A list of operations to process within this Transaction (see
     *                   {@link #setOperations(List)}).
     * @param extensions Extensions are currently not supported and will be ignored
     *                   (see {@link #setExtensions(List)}).
     */
    public SignedTransaction(BlockId blockId, List<Operation> operations, List<FutureExtensions> extensions) {
        super(blockId, operations, extensions);
        this.signatures = new ArrayList<>();
    }

    /**
     * Get the signatures for this transaction.
     *
     * @return An array of currently appended signatures.
     */
    @JsonProperty("signatures")
    public List<String> getSignatures() {
        return this.signatures;
    }

    /**
     * Verify that the signature is canonical.
     * <p>
     * Original implementation can be found <a href=
     * "https://github.com/kenCode-de/graphenej/blob/master/graphenej/src/main/java/de/bitsharesmunich/graphenej/Transaction.java"
     * >here</a>.
     *
     * @param signature A single signature in its byte representation.
     * @return True if the signature is canonical or false if not.
     */
    @Deprecated
    private boolean isCanonical(byte[] signature) {
        return ((signature[0] & 0x80) != 0) || (signature[0] == 0) || ((signature[1] & 0x80) != 0)
                || ((signature[32] & 0x80) != 0) || (signature[32] == 0) || ((signature[33] & 0x80) != 0);
    }

    /**
     * Like {@link #sign(String) sign(String)}, but uses the
     * {@link BeowulfJConfig#getChainId() default chain id}.
     *
     * @throws BeowulfInvalidTransactionException If the transaction can not be signed.
     */
    public void sign() throws BeowulfInvalidTransactionException {
        sign(BeowulfJConfig.getInstance().getChainId());
    }

    /**
     * Use this method if you want to specify a different chainId than the
     * {@link BeowulfJConfig#getChainId() default one}. Otherwise use the
     * {@link #sign() sign()} method.
     *
     * @param chainId The chain id that should be used during signing.
     * @throws BeowulfInvalidTransactionException If the transaction can not be signed.
     */
    public void sign(String chainId) throws BeowulfInvalidTransactionException {
        if (!BeowulfJConfig.getInstance().getValidationLevel().equals(ValidationType.SKIP_VALIDATION)) {
            this.validate();
        }

        for (ECKey requiredPrivateKey : getRequiredSignatureKeys()) {
            Sha256Hash messageAsHash;
            try {
                messageAsHash = Sha256Hash.of(this.toByteArray(chainId));
            } catch (BeowulfInvalidTransactionException e) {
                throw new BeowulfInvalidTransactionException(
                        "The required encoding is not supported by your platform.", e);
            }
            String signature = requiredPrivateKey.signMessage(messageAsHash);
            byte[] signatureAsByteArray = Base64.decode(signature);
            this.signatures.add(CryptoUtils.HEX.encode(signatureAsByteArray));
        }
    }

    /**
     * @return The list of private keys required to sign this transaction.
     * @throws BeowulfInvalidTransactionException If the required private key is not present in the
     *                                            {@link PrivateKeyStorage}.
     */
    @JsonIgnore
    protected List<ECKey> getRequiredSignatureKeys() throws BeowulfInvalidTransactionException {
        List<ECKey> requiredSignatures = new ArrayList<>();
        Map<SignatureObject, PrivateKeyType> requiredAuthorities = getRequiredAuthorities();

        for (Entry<SignatureObject, PrivateKeyType> requiredAuthority : requiredAuthorities.entrySet()) {
            if (requiredAuthority.getKey() instanceof AccountName) {
                requiredSignatures = getRequiredSignatureKeyForAccount(requiredSignatures,
                        (AccountName) requiredAuthority.getKey(), requiredAuthority.getValue());
            } else if (requiredAuthority.getKey() instanceof Authority) {
                // TODO: Support authorities.
            } else {
                LOGGER.warn("Unknown SigningObject type {}", requiredAuthority.getKey());
            }
        }

        return requiredSignatures;
    }

    /**
     * Fetch the requested private key for the given <code>accountName</code>
     * from the {@link PrivateKeyStorage}
     * and merge it into the <code>requiredSignatures</code> list.
     *
     * @param requiredSignatures A list of already fetched keys. This list is used to make sure
     *                           that a key is not added twice.
     * @param accountName        The account name to fetch the key for.
     * @param privateKeyType     The key type to fetch.
     * @return The <code>requiredSignatures</code> including the
     * <code>privateKeyType</code> for <code>accountName</code>.
     * @throws BeowulfInvalidTransactionException If the required private key is not present in the
     *                                            {@link PrivateKeyStorage}.
     */
    private List<ECKey> getRequiredSignatureKeyForAccount(List<ECKey> requiredSignatures, AccountName accountName,
                                                          PrivateKeyType privateKeyType) throws BeowulfInvalidTransactionException {
        ECKey privateKey;

        try {
            privateKey = BeowulfJConfig.getInstance().getPrivateKeyStorage().getKeyForAccount(privateKeyType,
                    accountName);
        } catch (InvalidParameterException ipe) {
            throw new BeowulfInvalidTransactionException(
                    "Could not find private " + privateKeyType + " key for the user " + accountName.getName() + ".");
        }

        if (!requiredSignatures.contains(privateKey)) {
            requiredSignatures.add(privateKey);
        }

        return requiredSignatures;
    }

    /**
     * This method creates a byte array based on a transaction object under the
     * use of a guide written by <a href="https://Beowulfit.com/Beowulf/@xeroc/">
     * Xeroc</a>. This method should only be used internally.
     * <p>
     * If a chainId is provided it will be added in front of the byte array.
     *
     * @return The serialized transaction object.
     * @throws BeowulfInvalidTransactionException If the transaction can not be signed.
     */
    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        return toByteArray(BeowulfJConfig.getInstance().getChainId());
    }

    /**
     * Like {@link #toByteArray() toByteArray()}, but allows to define a Beowulf
     * chain id.
     *
     * @param chainId The HEX representation of the chain Id you want to use for
     *                this transaction.
     * @return The serialized transaction object.
     * @throws BeowulfInvalidTransactionException If the transaction can not be signed.
     */
    protected byte[] toByteArray(String chainId) throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedTransaction = new ByteArrayOutputStream()) {
            if (chainId != null && !chainId.isEmpty()) {
                serializedTransaction.write(CryptoUtils.HEX.decode(chainId));
            }
            serializedTransaction.write(serialize());
            return serializedTransaction.toByteArray();
        } catch (IOException e) {
            throw new BeowulfInvalidTransactionException(
                    "A problem occured while transforming the transaction into a byte array.", e);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public TransactionId generateTransactionId() throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedTransaction = new ByteArrayOutputStream()) {
            serializedTransaction.write(serialize());
            Sha256Hash messageAsHash = Sha256Hash.of(serializedTransaction.toByteArray());
            return new TransactionId(messageAsHash.toString().substring(0, 40));
        } catch (IOException e) {
            throw new BeowulfInvalidTransactionException(
                    "A problem occured while transforming the transaction into a byte array.", e);
        }
    }

    private byte[] serialize() throws IOException, BeowulfInvalidTransactionException {
        ByteArrayOutputStream serializedTransaction = new ByteArrayOutputStream();
        serializedTransaction.write(BeowulfJUtils.transformShortToByteArray(this.getRefBlockNum().shortValue()));
        serializedTransaction.write(BeowulfJUtils.transformIntToByteArray(this.getRefBlockPrefix().intValue()));
        serializedTransaction.write(this.getExpirationDate().toByteArray());

        serializedTransaction.write(BeowulfJUtils.transformLongToVarIntByteArray(this.getOperations().size()));
        for (Operation operation : this.getOperations()) {
            operation.validate(BeowulfJConfig.getInstance().getValidationLevel());
            serializedTransaction.write(operation.toByteArray());
        }

        serializedTransaction.write(BeowulfJUtils.transformIntToVarIntByteArray(this.getExtensions().size()));
        for (FutureExtensions futureExtensions : this.getExtensions()) {
            serializedTransaction.write(futureExtensions.toByteArray());
        }
        serializedTransaction.write(BeowulfJUtils.transformLongToByteArray(this.getCreatedTime()));
        return serializedTransaction.toByteArray();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
