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

import org.junit.Test;

import java.security.InvalidParameterException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

/**
 * Test some specific methods of the {@link BeowulfJUtils} class.
 */
public class BeowulfJUtilsTest {
    /**
     * Test if the {@link BeowulfJUtils#verifyJsonString(String)} method is
     * working correctly.
     */
    @Test
    public void testVerifyJsonString() {
        assertTrue(BeowulfJUtils.verifyJsonString("{\"menu\": {\"id\": \"file\", \"value\": \"File\", \"popup\": "
                + "{ \"menuitem\": [ {\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"}, "
                + "{\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},"
                + "{\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}]}}}"));
        assertFalse(BeowulfJUtils.verifyJsonString("{\"menu\": {\"id\": \"file\", \"value\": \"File\", \"popup\": "
                + "{ \"menuitem\": [ {\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"}, "
                + "{\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},"
                + "{\"value\": [\"Close\",] \"onclick\": \"CloseDoc()\"}]}}"));
    }

    /**
     * Test if the {@link BeowulfJUtils#setIfNotNull(Object, String, Object)}
     * method is working correctly.
     */
    @Test
    public void testSetIfNotNull() {
        Object objectToSet = new Object();
        Object nullObject = null;
        Object defaultValue = new Object();
        String exampleMessage = "TestMessage";

        assertThat(BeowulfJUtils.setIfNotNull(objectToSet, exampleMessage), equalTo(objectToSet));

        try {
            BeowulfJUtils.setIfNotNull(nullObject, exampleMessage);
            fail();
        } catch (InvalidParameterException e) {
            assertThat(e.getMessage(), equalTo(exampleMessage));
        }

        assertThat(BeowulfJUtils.setIfNotNull(objectToSet, defaultValue), equalTo(objectToSet));
        assertThat(BeowulfJUtils.setIfNotNull(nullObject, defaultValue), equalTo(defaultValue));

        try {
            BeowulfJUtils.setIfNotNull(nullObject, nullObject);
            fail();
        } catch (InvalidParameterException e) {
            assertThat(e.getMessage(), equalTo("Both, the objectToSet and the default value are null."));
        }
    }
}
