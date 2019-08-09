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
package com.beowulfchain.beowulfj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joou.UInteger;
import org.joou.UShort;

/**
 * This class defines static properties used in all tests.
 */
public abstract class BaseTest {
    protected static final Logger LOGGER = LogManager.getLogger(BaseTest.class);

    protected static final UShort REF_BLOCK_NUM = UShort.valueOf((short) 34294);
    protected static final UInteger REF_BLOCK_PREFIX = UInteger.valueOf(3707022213L);
    protected static final String EXPIRATION_DATE = "2016-04-06T08:29:27UTC";
}
