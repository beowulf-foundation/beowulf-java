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

import com.beowulfchain.beowulfj.protocol.enums.AssetSymbolType;
import eu.bittrade.crypto.core.CryptoUtils;
import org.junit.Test;

import java.security.InvalidParameterException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

/**
 * Test the Price object.
 */
public class PriceTest {
    private final String EXPECTED_BYTE_REPRESENTATION = "730000000000000005000000570000000000000000640000000000000005000000425746000000000000";

    /**
     * Test if the {@link Price#toByteArray}
     * method of a price object returns the expected byte array.
     *
     * @throws Exception If something went wrong.
     */
    @Test
    public void testPriceToByteArray() throws Exception {
        Asset base = new Asset(115, AssetSymbolType.W);
        Asset quote = new Asset(100, AssetSymbolType.BWF);

        Price price = new Price(base, quote);

        assertThat("Expect that the price object has the given byte representation.",
                CryptoUtils.HEX.encode(price.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    /**
     * Test the {@link Price#equals} method
     * of a price object.
     */
    @Test
    public void testPriceEqualsMethod() {
        Asset base = new Asset(115, AssetSymbolType.W);
        Asset quote = new Asset(100, AssetSymbolType.BWF);

        Price price = new Price(base, quote);

        Asset anotherBase = new Asset(115, AssetSymbolType.W);
        Asset anotherQuote = new Asset(100, AssetSymbolType.BWF);

        Price anotherPrice = new Price(anotherBase, anotherQuote);

        Asset defferentBase = new Asset(115, AssetSymbolType.W);
        Asset differentQuote = new Asset(1230, AssetSymbolType.BWF);

        Price differentPrice = new Price(defferentBase, differentQuote);

        assertThat(price.equals(anotherPrice), equalTo(true));
        assertThat(anotherPrice.equals(differentPrice), equalTo(false));
    }

    /**
     * Test the validation of the
     * {@link Price} object by providing
     * invalid assets.
     */
    @Test(expected = InvalidParameterException.class)
    public void testPriceValidationNegativeBaseAsset() {
        Asset base = new Asset(-1, AssetSymbolType.W);
        base.setAmount(-1);
        base.setSymbol(AssetSymbolType.W);
        Asset quote = new Asset(100, AssetSymbolType.BWF);

        new Price(base, quote);
    }

    /**
     * Test the validation of the
     * {@link Price} object by providing
     * invalid assets.
     */
    @Test(expected = InvalidParameterException.class)
    public void testPriceValidationNegativeQuoteAsset() {
        Asset base = new Asset(115, AssetSymbolType.W);
        Asset quote = new Asset(-1, AssetSymbolType.BWF);

        new Price(base, quote);
    }

    /**
     * Test the validation of the
     * {@link Price} object by providing
     * invalid assets.
     */
    @Test(expected = InvalidParameterException.class)
    public void testPriceValidationNegativeSameSymbols() {
        Asset base = new Asset(115, AssetSymbolType.BWF);
        Asset quote = new Asset(100, AssetSymbolType.BWF);

        new Price(base, quote);
    }

    /**
     * Test the validation of the
     * {@link Price}} object by providing
     * valid assets.
     */
    @Test
    public void testPriceValidation() {
        Asset base = new Asset(115, AssetSymbolType.W);
        Asset quote = new Asset(100, AssetSymbolType.BWF);

        new Price(base, quote);
    }

    /**
     * Test the {@link Price#multiply(Asset)} method.
     */
    @Test
    public void testMultiply() {
        Asset base = new Asset(50L, AssetSymbolType.W);
        Asset quote = new Asset(100L, AssetSymbolType.BWF);

        Price exchangeRate = new Price(base, quote);

        Asset amountToSell = new Asset(2L, AssetSymbolType.W);

        assertEquals(4L, (long) exchangeRate.multiply(amountToSell).getAmount());
        assertEquals(exchangeRate.multiply(amountToSell).getName(), AssetSymbolType.BWF.name());
    }
}
