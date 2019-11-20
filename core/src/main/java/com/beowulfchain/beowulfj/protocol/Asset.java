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
package com.beowulfchain.beowulfj.protocol;

import com.beowulfchain.beowulfj.BeowulfJ;
import com.beowulfchain.beowulfj.base.models.deserializer.AssetDeserializer;
import com.beowulfchain.beowulfj.base.models.serializer.AssetSerializer;
import com.beowulfchain.beowulfj.configuration.BeowulfJConfig;
import com.beowulfchain.beowulfj.exceptions.BeowulfCommunicationException;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.exceptions.BeowulfResponseException;
import com.beowulfchain.beowulfj.interfaces.ByteTransformable;
import com.beowulfchain.beowulfj.plugins.apis.condenser.models.FindSmtTokenByName;
import com.beowulfchain.beowulfj.protocol.enums.AssetSymbolType;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joou.UInteger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Collections;

@JsonDeserialize(using = AssetDeserializer.class)
@JsonSerialize(using = AssetSerializer.class)
public class Asset implements ByteTransformable {
    // Original type is "share_type" which is a "safe<int64_t>".
    private long amount;
    // Type us uint64_t in the original code.
    private String name;
    private byte precision;

    private Asset() {
    }

    /**
     * Create a new asset object by providing all required fields.
     *
     * @param amount The amount.
     * @param symbol   One type of
     *               {@link AssetSymbolType
     *               AssetSymbolType}.
     */
    public Asset(BigDecimal amount, AssetSymbolType symbol) {
        // native asset by precision equal 5 by default
        this.setPrecision(UInteger.valueOf(5));
        this.setName(symbol.name());
        this.setAmount(amount);
    }

    public Asset(long amount, AssetSymbolType symbol) {
        // native asset by precision equal 5 by default
        this.setPrecision(UInteger.valueOf(5));
        this.setName(symbol.name());
        this.setAmount(amount);
    }

    /**
     * Create a new asset object by providing all required fields.
     *
     * @param amount The amount.
     * @param symbol   One type of
     *               {@link AssetSymbol
     *               AssetSymbol}.
     */
    public Asset(long amount, AssetSymbol symbol, UInteger precision) {
        // native asset by precision equal 5 by default
        this.setPrecision(precision);
        this.setName(symbol.getName());
        this.setAmount(amount);
    }

    public Asset(BigDecimal amount, AssetSymbol symbol, UInteger precision) {
        this.setPrecision(precision);
        this.setName(symbol.getName());
        this.setAmount(amount);
    }


    /**
     * Create a new asset object by providing all required fields.
     *
     * @param amount The amount.
     * @param name   String.
     * @param precision The precision of asset.
     */
    public Asset(BigDecimal amount, String name, UInteger precision) {
        this.setName(name);
        this.setPrecision(precision);
        this.setAmount(amount);
    }

    /**
     * Create a new asset object by providing all required fields.
     *
     * @param amount The amount.
     * @param name   String.
     * @param precision The precision of asset.
     */
    public Asset(long amount, String name, UInteger precision) {
        this.setName(name);
        this.setPrecision(precision);
        this.setAmount(amount);
    }

    /**
     * Create a new asset object by providing all required fields.
     *
     * @param amount The amount.
     * @param name   String.
     * @return Asset instance contain precision from network
     * @throws BeowulfCommunicationException The BeowulfCommunicationException.
     * @throws BeowulfResponseException The BeowulfResponseException.
     */
    public static Asset createAsset(BigDecimal amount, String name) throws BeowulfCommunicationException, BeowulfResponseException {
        Asset asset = new Asset();
        if (AssetSymbolType.getNativeAsset(name) != null) {
            asset.setPrecision(UInteger.valueOf(5));
            asset.setName(name);
            asset.setAmount(amount);
        } else {
            FindSmtTokenByName tokenInfo = BeowulfJ.getInstance().findSmtTokenByName(Collections.singletonList(name)).get(0);
            UInteger decimals = tokenInfo.getLiquid_symbol().getDecimals();
            asset.setPrecision(decimals);
            asset.setName(name);
            asset.setAmount(amount);
        }
        return asset;
    }

    /**
     * Get the amount stored in this asset object.
     *
     * @return The amount.
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * Set the amount of this asset.
     *
     * @param amount The amount.
     */
    public void setAmount(long amount) {
        this.amount = amount;
    }

    /**
     * Set the amount of this asset.
     *
     * @param amount The amount.
     */
    public void setAmount(BigDecimal amount) {
        if (amount.scale() > this.getPrecision().intValue()) {
            throw new InvalidParameterException("The provided 'amount' has a 'scale' of " + amount.scale()
                    + ", but needs to have a 'scale' of " + this.getPrecision() + " when " + this.getName()
                    + " is used as a AssetSymbol.");
        }

        this.amount = amount.multiply(BigDecimal.valueOf(Math.pow(10, this.getPrecision().intValue()))).longValue();
    }

    public void setAmount(String amount) {
        BigDecimal amountDecimal = new BigDecimal(amount);
        setAmount(amountDecimal);
    }

    /**
     * Get the precision of this asset object.
     *
     * @return The precision.
     */
    public UInteger getPrecision() {
        return UInteger.valueOf((int) this.precision);
    }

    /**
     * Set the precision of this asset.
     *
     * @param precision One type of
     *                  {@link AssetSymbolType
     *                  AssetSymbolType}.
     */
    public void setPrecision(UInteger precision) {
        this.precision = precision.byteValue();
    }

    /**
     * Get the name for this asset object.
     *
     * @return String.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this asset.
     *
     * @param name One type of
     *             {@link AssetSymbolType
     *             AssetSymbolType}.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the name of this asset.
     *
     * @param symbol One type of
     *               {@link AssetSymbolType
     *               AssetSymbolType}.
     */
    public void setSymbol(AssetSymbolType symbol) {
        this.name = symbol.name();
    }

    /**
     * Transform this asset into its {@link BigDecimal} representation.
     *
     * @return The value of this asset in its {@link BigDecimal} representation.
     */
    public BigDecimal toReal() {
        BigDecimal transformedValue = new BigDecimal(this.getAmount());
//        transformedValue.setScale(this.getPrecision());
        return transformedValue.divide(BigDecimal.valueOf(Math.pow(10, this.getPrecision().intValue())));
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedAsset = new ByteArrayOutputStream()) {
            serializedAsset.write(BeowulfJUtils.transformLongToByteArray(this.amount));

            String filledAssetSymbol = this.name.toUpperCase();
            // padding byte => 4 byte
            serializedAsset.write(BeowulfJUtils.transformIntToByteArray(this.precision));
            serializedAsset
                    .write(this.name.toUpperCase().getBytes(BeowulfJConfig.getInstance().getEncodingCharset()));

            for (int i = filledAssetSymbol.length(); i < 9; i++) {
                serializedAsset.write(0x00);
            }

            return serializedAsset.toByteArray();
        } catch (IOException e) {
            throw new BeowulfInvalidTransactionException(
                    "A problem occured while transforming an asset into a byte array.", e);
        }
    }

    @Override
    public String toString() {
//        return ToStringBuilder.reflectionToString(this);
        return BigDecimal.valueOf(this.getAmount()).divide(BigDecimal.valueOf(100000L)).toPlainString() + " " + this.getName();
    }

    @Override
    public boolean equals(Object otherAsset) {
        if (this == otherAsset)
            return true;
        if (otherAsset == null || !(otherAsset instanceof Asset))
            return false;
        Asset other = (Asset) otherAsset;
        return (this.getAmount().equals(other.getAmount()) && this.getName().equals(other.getName())
                && this.getPrecision().equals(other.getPrecision()));
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + (this.getAmount() == null ? 0 : this.getAmount().hashCode());
        hashCode = 31 * hashCode + (this.getName() == null ? 0 : this.getName().hashCode());
        hashCode = 31 * hashCode + (this.getPrecision() == null ? 0 : this.getPrecision().hashCode());
        return hashCode;
    }
}
