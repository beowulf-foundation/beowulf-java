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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Test the brain key dictionary import.
 */
public class BrainkeyDictionaryManagerTest {
    private static final int NUMBER_OF_WORDS = 49744;

    /**
     * Verify that the brain key dictionary has the correct amount of words.
     */
    @Test
    public void testBrainkeyDictionaryManager() {
        System.out.println(BrainkeyDictionaryManager.getInstance().getBrainKeyDictionary().length);
        assertThat(BrainkeyDictionaryManager.getInstance().getBrainKeyDictionary().length, equalTo(NUMBER_OF_WORDS));
    }
}
