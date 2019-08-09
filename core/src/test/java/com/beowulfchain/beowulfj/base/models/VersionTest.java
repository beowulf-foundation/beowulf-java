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

public class VersionTest {
    /**
     * Test if a Version object results in the expected String representation
     * under the use of the
     * {@link Version#Version(byte, byte, short)}
     * constructor.
     */
    @Test
    public void versionToStringTest() {
        Version version = new Version((byte) 0, (byte) 19, (short) 2);

        assertThat(version.toString(), equalTo("0.19.2"));
    }

    /**
     * Test if a Version object results in the expected String representation
     * under the use of the
     * {@link Version#Version(String)}
     * constructor.
     */
    @Test
    public void versionFromStringToStringTest() {
        Version version = new Version("0.19.1");

        assertThat(version.toString(), equalTo("0.19.1"));
    }
}
