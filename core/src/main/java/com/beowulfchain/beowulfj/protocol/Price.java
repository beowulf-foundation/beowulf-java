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

import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.interfaces.ByteTransformable;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;

public class Price implements ByteTransformable {
    @JsonProperty("base")
    private Asset base;
    @JsonProperty("quote")
    private Asset quote;

    /**
     * Create a new price object by providing a base and a
     * quote asset.
     * 
     * Represents quotation of the relative value of asset against another
     * asset. Similar to 'currency pair' used to determine value of currencies.
     * 
     * For example:
     * 
     * 1 EUR / 1.25 USD where:
     * 
     * 1 EUR is an asset specified as a base
     * 1.25 USD us an asset specified as a quote
     * 
     * can determine value of EUR against USD.
     * 
     *
     * @param base  Represents a value of the Price object to be
     *              expressed relatively to quote asset.
     * @param quote Represents an relative asset.
     * @throws InvalidParameterException If the base, the quote or both
     *                                   objects have not been provided, contain the same symbol or
     *                                   have an amount less than 1.
     */
    @JsonCreator
    public Price(@JsonProperty("base") Asset base, @JsonProperty("quote") Asset quote) {
        this.base = base;
        this.quote = quote;

        validate();
    }

    /**
     * @return The base asset.
     */
    public Asset getBase() {
        return base;
    }

    /**
     * @param base The base asset to set.
     * @throws InvalidParameterException If the <code>base</code>, the <code>quote</code> or both
     *                                   objects have not been provided, contain the same symbol or
     *                                   have an amount less than 1.
     */
    public void setBase(Asset base) {
        this.base = base;

        validate();
    }

    /**
     * @return The quote asset.
     */
    public Asset getQuote() {
        return quote;
    }

    /**
     * @param quote The quote asset to set.
     * @throws InvalidParameterException If the <code>base</code>, the <code>quote</code> or both
     *                                   objects have not been provided, contain the same symbol or
     *                                   have an amount less than 1.
     */
    public void setQuote(Asset quote) {
        this.quote = quote;

        validate();
    }

    /**
     * Multiply this price instance with an <code>Asset</code> instance.
     *
     * @param asset The asset to multiply.
     * @return The <code>asset</code> multiplied with this price.
     */
    public Asset multiply(Asset asset) {
        if (asset == null) {
            throw new InvalidParameterException("The asset can't be null");
        } else if (asset.getName().equals(this.getBase().getName())) {
            if (this.getBase().getAmount() == 0) {
                throw new InvalidParameterException("Can't multiply as the price base is 0.");
            }

            return new Asset((long) ((asset.getAmount() * this.getQuote().getAmount()) / this.getBase().getAmount()),
                    this.getQuote().getName(), this.getQuote().getPrecision());
        } else if (asset.getName().equals(this.getQuote().getName())) {
            if (this.getQuote().getAmount() == 0) {
                throw new InvalidParameterException("Can't multiply as the price quote is 0.");
            }

            return new Asset((long) ((asset.getAmount() * this.getBase().getAmount()) / this.getQuote().getAmount()),
                    this.getBase().getName(), this.getQuote().getPrecision());
        } else {
            throw new InvalidParameterException(
                    "The provided asset does not fulfill the requirements to perform the multiply operation.");
        }
    }

    /**
     * Validate this <code>Price</code> object.
     *
     * @throws InvalidParameterException If the <code>base</code>, the <code>quote</code> or both
     *                                   objects have not been provided, contain the same symbol or
     *                                   have an amount less than 1.
     */
    private void validate() {
        if (BeowulfJUtils.setIfNotNull(this.base, "The base asset needs to be provided.").getName() == BeowulfJUtils
                .setIfNotNull(this.quote, "The quote asset needs to be provided.").getName()) {
            throw new InvalidParameterException("The base asset needs to have a different type than the quote asset.");
        } else if (this.base.getAmount() <= 0) {
            throw new InvalidParameterException("The base asset amount needs to be greater than 0.");
        } else if (this.quote.getAmount() <= 0) {
            throw new InvalidParameterException("The quote asset amount needs to be greater than 0.");
        }
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedPriceObject = new ByteArrayOutputStream()) {
            serializedPriceObject.write(this.getBase().toByteArray());
            serializedPriceObject.write(this.getQuote().toByteArray());

            return serializedPriceObject.toByteArray();
        } catch (IOException e) {
            throw new BeowulfInvalidTransactionException(
                    "A problem occured while transforming the object into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject)
            return true;
        if (otherObject == null || !(otherObject instanceof Price))
            return false;
        Price otherPrice = (Price) otherObject;
        return (this.getBase().equals(otherPrice.getBase()) && this.getQuote().equals(otherPrice.getQuote()));
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + (this.getBase() == null ? 0 : this.getBase().hashCode());
        hashCode = 31 * hashCode + (this.getQuote() == null ? 0 : this.getQuote().hashCode());
        return hashCode;
    }
}
