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

import com.beowulfchain.beowulfj.protocol.AccountName;
import com.beowulfchain.beowulfj.protocol.Authority;
import com.beowulfchain.beowulfj.protocol.PublicKey;
import eu.bittrade.crypto.core.CryptoUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class AuthorityTest {
    private final String EXPECTED_BYTE_REPRESENTATION = "0600000000010312fb0a6de07b5fcfe3cc282b1886f73cb2b279753516f3a7b12b4795aeb74ca90100";

    /**
     * Test the {@link Authority#toByteArray()
     * toByteArray()} method.
     *
     * @throws Exception In case of a problem.
     */
    @Test
    public void testBeowulfChainPropertiesToByteArray() throws Exception {
        Authority exampleAuthority = new Authority();
        exampleAuthority.setAccountAuths(new HashMap<AccountName, Integer>());
        Map<PublicKey, Integer> postingKeyAuth = new HashMap<>();
        postingKeyAuth.put(new PublicKey("BEO6ybN7AC3kiFwmZssZ4AK9JskN68BZQCHG7LoUssqppUH5scQjY"), 1);
        exampleAuthority.setKeyAuths(postingKeyAuth);
        exampleAuthority.setWeightThreshold(6);

        assertThat("Expect that the asset object has the given byte representation.",
                CryptoUtils.HEX.encode(exampleAuthority.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));

    }

    /**
     * Test the {@link Authority#equals(Object)
     * equals(Object)} method.
     */
    @Test
    public void testAuthorityEqualsMethod() {
        Authority authorityOne = new Authority();
        Map<PublicKey, Integer> postingKeyAuth = new HashMap<>();
        postingKeyAuth.put(new PublicKey("BEO7Amy3akYfmSY92YrxYxfEGfc1pe3ctJtWjRi1wfo66K2e9veCN"), 1);
        authorityOne.setKeyAuths(postingKeyAuth);
        Map<AccountName, Integer> accountAuths = new HashMap<>();
        accountAuths.put(new AccountName("trongcauta"), 2);
        authorityOne.setAccountAuths(accountAuths);
        authorityOne.setWeightThreshold(1);

        Authority authorityTwo = new Authority();
        authorityTwo.setKeyAuths(postingKeyAuth);
        authorityTwo.setAccountAuths(accountAuths);
        authorityTwo.setWeightThreshold(1);

        assertEquals(authorityOne, authorityTwo);

        authorityTwo.setWeightThreshold(2);

        assertNotEquals(authorityOne, authorityTwo);

    }

    /**
     * Test the {@link Authority#isEmpty()}
     * method.
     */
    @Test
    public void testAuthorityIsEmptyMethod() {
        Authority exampleAuthority = new Authority();
        assertTrue(exampleAuthority.isEmpty());

        exampleAuthority = new Authority();
        exampleAuthority.setWeightThreshold(1);
        assertTrue(exampleAuthority.isEmpty());

        exampleAuthority = new Authority();
        Map<PublicKey, Integer> postingKeyAuth = new HashMap<>();
        postingKeyAuth.put(new PublicKey("BEO7Amy3akYfmSY92YrxYxfEGfc1pe3ctJtWjRi1wfo66K2e9veCN"), 1);
        exampleAuthority.setKeyAuths(postingKeyAuth);
        assertFalse(exampleAuthority.isEmpty());

        exampleAuthority = new Authority();
        Map<AccountName, Integer> accountAuths = new HashMap<>();
        accountAuths.put(new AccountName("trongcauta"), 2);
        exampleAuthority.setAccountAuths(accountAuths);
        assertFalse(exampleAuthority.isEmpty());

        exampleAuthority = new Authority();
        exampleAuthority.setKeyAuths(postingKeyAuth);
        exampleAuthority.setAccountAuths(accountAuths);
        assertFalse(exampleAuthority.isEmpty());
    }

    /**
     * Test the {@link Authority#hashCode()}
     * method.
     */
    @Test
    public void testAuthorityHashCodeMethod() {
        Authority exampleAuthority = new Authority();
        Map<PublicKey, Integer> postingKeyAuth = new HashMap<>();
        postingKeyAuth.put(new PublicKey("BEO7Amy3akYfmSY92YrxYxfEGfc1pe3ctJtWjRi1wfo66K2e9veCN"), 1);
        exampleAuthority.setKeyAuths(postingKeyAuth);
        Map<AccountName, Integer> accountAuths = new HashMap<>();
        accountAuths.put(new AccountName("trongcauta"), 2);
        exampleAuthority.setAccountAuths(accountAuths);
        exampleAuthority.setWeightThreshold(1);
        assertThat(exampleAuthority.hashCode(), equalTo(-52370617));
    }
}
