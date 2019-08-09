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
import eu.bittrade.crypto.core.CryptoUtils;
import org.junit.Test;

import java.security.InvalidParameterException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
/**
 * Test the AccountName object.
 */
public class AccountNameTest {
    private final String EXPECTED_BYTE_REPRESENTATION = "0762656f77756c66";

    /**
     * Test the
     * {@link AccountName#toByteArray()
     * toByteArray()} method.
     *
     * @throws Exception In case of a problem.
     */
    @Test
    public void testAccountNameToByteArray() throws Exception {
        AccountName myAccount = new AccountName("beowulf");

        assertThat("Expect that the accountName object has the given byte representation.",
                CryptoUtils.HEX.encode(myAccount.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    /**
     * Test the validation of the
     * {@link AccountName#setName(String name)
     * setName(String name)} method by providing an account name which is too
     * long.
     */
    @Test(expected = InvalidParameterException.class)
    public void testAccountNameValidationTooLong() {
        new AccountName("thisaccountnameistoolong");
    }

    /**
     * Test the validation of the
     * {@link AccountName#setName(String name)
     * setName(String name)} method by providing an account name which is too
     * short.
     */
    @Test(expected = InvalidParameterException.class)
    public void testAccountNameValidationTooShort() {
        new AccountName("sh");
    }

    /**
     * Test the validation of the
     * {@link AccountName#setName(String name)
     * setName(String name)} method by providing an account name with an invalid
     * start character.
     */
    @Test(expected = InvalidParameterException.class)
    public void testAccountNameValidationWrongStartChar() {
        new AccountName("-beowulf");
    }

    /**
     * Test the validation of the
     * {@link AccountName#setName(String name)
     * setName(String name)} method by providing an account name with an invalid
     * end character.
     */
    @Test(expected = InvalidParameterException.class)
    public void testAccountNameValidationWrongEndChar() {
        new AccountName("beowulf");
    }

    /**
     * Test the validation of the
     * {@link AccountName#setName(String name)
     * setName(String name)} method by providing valid account names.
     */
    @Test
    public void testAccountNameValidation() {
        new AccountName("ta-trongcau");
        new AccountName("cau");
        new AccountName("trongcau-beowulf");
    }
}
