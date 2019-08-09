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

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Test the Ripedm160Test object.
 */
public class Ripedm160Test {
    private final int EXPECTED_NUMBER = 14534105;

    /**
     * Test the
     * {@link Ripemd160#getNumberFromHash()}
     * method.
     *
     * @throws Exception If something went wrong.
     */
    @Test
    public void testVestsAssetToByteArray() throws Exception {
        Ripemd160 hash = new Ripemd160("00ddc5d91d1bd0050853ce67c665c390cb232bbf") {
            private static final long serialVersionUID = 3981401550230595705L;
        };

        assertThat(hash.getNumberFromHash(), equalTo(EXPECTED_NUMBER));
    }
}
