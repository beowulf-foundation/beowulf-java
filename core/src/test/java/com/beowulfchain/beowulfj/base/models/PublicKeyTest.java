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
package com.beowulfchain.beowulfj.base.models;

import com.beowulfchain.beowulfj.BaseUT;
import com.beowulfchain.beowulfj.protocol.PublicKey;
import eu.bittrade.crypto.core.CryptoUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Test the PublicKey object.
 */
public class PublicKeyTest extends BaseUT {
    private static final String ADDRESS = "BEO6ybN7AC3kiFwmZssZ4AK9JskN68BZQCHG7LoUssqppUH5scQjY";
    private static final String ANOTHER_ADDRESS = "BEO8YAMLtNcnqGNd3fx28NP3WoyuqNtzxXpwXTkZjbfe9scBmSyGT";
    private static final String EXPECTED_BYTE_REPRESENTATION = "0312fb0a6de07b5fcfe3cc282b1886f73cb2b279753516f3a7b12b4795aeb74ca9";

    private static PublicKey publicKey;

    /**
     * Prepare the environment for this specific test.
     *
     * @throws Exception If something went wrong.
     */
    @BeforeClass
    public static void prepareTestClass() throws Exception {
        publicKey = new PublicKey(ADDRESS);
    }

    /**
     * Test the {@link PublicKey#toByteArray()}
     * method by creating a new PublicKey from an Address.
     *
     * @throws Exception If something went wrong.
     */
    @Test
    public void testPublicKeyFromAddress() throws Exception {
        assertThat(CryptoUtils.HEX.encode(publicKey.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    /**
     * Test the
     * {@link PublicKey#getAddressFromPublicKey}
     * method.
     *
     * @throws Exception If something went wrong.
     */
    @Test
    public void testAddressFromPublicKey() throws Exception {
        assertThat(ADDRESS, equalTo(publicKey.getAddressFromPublicKey()));
    }

    /**
     * Test the
     * {@link PublicKey#equals(Object)} method.
     */
    @Test
    public void testPublicKeyEqualsMethod() {
        PublicKey publicKey = new PublicKey(ADDRESS);
        PublicKey samePublicKey = new PublicKey(ADDRESS);
        PublicKey differentPublicKey = new PublicKey(ANOTHER_ADDRESS);

        assertThat(publicKey.equals(samePublicKey), equalTo(true));
        assertThat(samePublicKey.equals(differentPublicKey), equalTo(false));
    }
}
