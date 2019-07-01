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
package com.beowulfchain.beowulfj.util;

import com.beowulfchain.beowulfj.protocol.AccountName;
import com.beowulfchain.beowulfj.protocol.KeyPair;
import com.beowulfchain.beowulfj.protocol.WalletObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.spongycastle.pqc.math.linearalgebra.ByteUtils;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

public class WalletUtils {
    public static byte[] sha256(String msg) {

        try {
            MessageDigest sha512Digest = null;
            sha512Digest = MessageDigest.getInstance("SHA-512");
            byte[] hashedPassword = sha512Digest.digest(msg.getBytes(StandardCharsets.UTF_8));
            return hashedPassword;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param account The account name.
     * @param keypair The key pair public key and private key.
     * @param password The password.
     * @return WalletObject
     */
    public static WalletObject encryptWallet(AccountName account, KeyPair keypair, String password) {
        String salt = UUID.randomUUID().toString().substring(16);
        String msg = password + salt;
        byte[] hashedPassword = sha256(msg);
        byte[] iv = Arrays.copyOfRange(hashedPassword, 32, 48);
        byte[] newPassword = Arrays.copyOfRange(hashedPassword, 0, 32);

        JsonNodeFactory factory = new JsonNodeFactory(true);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonNode = new ObjectNode(factory);
        jsonNode.put("checksum", ByteUtils.toHexString(hashedPassword));
        jsonNode.putPOJO("keys", mapper.convertValue(keypair, JsonNode.class));


        String strPlainKeys = jsonNode.toString();
        byte[] encryptedKeys = encrypt(newPassword, iv, strPlainKeys.getBytes(StandardCharsets.UTF_8));

        WalletObject walletObject = new WalletObject();
        walletObject.setCipher_keys(ByteUtils.toHexString(encryptedKeys));
        walletObject.setCipher_type("aes-256-cbc");
        walletObject.setName(account);
        walletObject.setSalt(salt);

        return walletObject;
    }

    public static JsonNode decryptWallet(WalletObject encryptedWallet, String password) throws IOException {
        String encryptedKeys = encryptedWallet.getCipher_keys();
        byte[] cipherkeys = ByteUtils.fromHexString(encryptedKeys);

        String salt = encryptedWallet.getSalt();
        String msg = password + salt;
        byte[] hashedPassword = sha256(msg);
        byte[] iv = Arrays.copyOfRange(hashedPassword, 32, 48);
        byte[] newPassword = Arrays.copyOfRange(hashedPassword, 0, 32);
        byte[] strPlainKeys = decrypt(newPassword, iv, cipherkeys);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode plainKeys = mapper.readTree(strPlainKeys);
        return plainKeys;
    }

    public static byte[] encrypt(byte[] key, byte[] initVector, byte[] value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value);

            return encrypted;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(byte[] key, byte[] initVector, byte[] encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(encrypted);

            return original;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


