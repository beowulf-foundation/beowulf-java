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

import java.math.BigDecimal;
import java.security.InvalidParameterException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;

/**
 * Test the Asset object.
 */
public class AssetTest {
    private final String EXPECTED_VESTS_ASSET_BYTE_REPRESENTATION = "ce04000000000000050000004d0000000000000000";

    private final String EXPECTED_BEOWULF_ASSET_BYTE_REPRESENTATION = "78e001000000000005000000425746000000000000";
    private final String EXPECTED_WD_ASSET_BYTE_REPRESENTATION = "7b0000000000000005000000570000000000000000";
    private final String EXPECTED_BWF_ASSET_BYTE_REPRESENTATION = "0cde00000000000005000000425746000000000000";

    /**
     * Test the {@link Asset#toByteArray()}
     * method for the M asset type.
     *
     * @throws Exception If something went wrong.
     */
    @Test
    public void testVestsAssetToByteArray() throws Exception {
        Asset vestsAsset = new Asset(1230, AssetSymbolType.M);

        assertThat(vestsAsset.getPrecision().intValue(), equalTo(5));
        assertThat("Expect that the asset object has the given byte representation.",
                CryptoUtils.HEX.encode(vestsAsset.toByteArray()), equalTo(EXPECTED_VESTS_ASSET_BYTE_REPRESENTATION));
    }

    /**
     * Test the {@link Asset#toByteArray()}
     * method for the BWF asset type.
     *
     * @throws Exception If something went wrong.
     */
    @Test
    public void testBeowulfAssetToByteArray() throws Exception {
        Asset beowulfAsset = new Asset(123000, AssetSymbolType.BWF);

        assertThat(beowulfAsset.getPrecision().intValue(), equalTo(5));
        assertThat("Expect that the asset object has the given byte representation.",
                CryptoUtils.HEX.encode(beowulfAsset.toByteArray()), equalTo(EXPECTED_BEOWULF_ASSET_BYTE_REPRESENTATION));
    }

    /**
     * Test the {@link Asset#toByteArray()}
     * method for the W asset type.
     *
     * @throws Exception If something went wrong.
     */
    @Test
    public void testWdAssetToByteArray() throws Exception {
        Asset wdAsset = new Asset(123, AssetSymbolType.W);

        assertThat(wdAsset.getPrecision().intValue(), equalTo(5));
        assertThat("Expect that the asset object has the given byte representation.",
                CryptoUtils.HEX.encode(wdAsset.toByteArray()), equalTo(EXPECTED_WD_ASSET_BYTE_REPRESENTATION));
    }

    /**
     * Test the {@link Asset#toByteArray()}
     * method for the BWF asset type.
     *
     * @throws Exception If something went wrong.
     */
    @Test
    public void testBWFAssetToByteArray() throws Exception {
        Asset wdAsset = new Asset(56844, AssetSymbolType.BWF);

        assertThat(wdAsset.getPrecision().intValue(), equalTo(5));
        assertThat("Expect that the asset object has the given byte representation.",
                CryptoUtils.HEX.encode(wdAsset.toByteArray()), equalTo(EXPECTED_BWF_ASSET_BYTE_REPRESENTATION));
    }

    /**
     * Test the {@link Asset#equals(Object)}
     * method for the M asset type.
     */
    @Test
    public void testAssetEqualsMethod() {
        Asset asset = new Asset(11500, AssetSymbolType.W);

        Asset sameAsset = new Asset(new BigDecimal("0.115"), AssetSymbolType.W);

        Asset differentAsset = new Asset(10000, AssetSymbolType.BWF);

        assertThat(asset.equals(sameAsset), equalTo(true));
        assertThat(sameAsset.equals(differentAsset), equalTo(false));
    }

    /**
     * Test the {@link Asset} method for the
     * M asset type.
     */
    @Test
    public void testAssetBigDecimalConstructor() {
        new Asset(new BigDecimal("0.1150"), AssetSymbolType.W);
        new Asset(new BigDecimal("200.100"), AssetSymbolType.BWF);
        new Asset(new BigDecimal("2500.14511"), AssetSymbolType.BWF);

        try {
            new Asset(new BigDecimal("0.115144"), AssetSymbolType.W);
            fail();
        } catch (InvalidParameterException e) {
            // Expected.
        }
    }
}
