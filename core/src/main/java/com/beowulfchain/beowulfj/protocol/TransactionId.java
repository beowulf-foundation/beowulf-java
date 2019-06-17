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

import com.beowulfchain.beowulfj.base.models.Ripemd160;
import java.io.Serializable;

/**
 * This class is the java implementation of the Beowulf "transaction_id_type"
 * object.
 */
public class TransactionId extends Ripemd160 implements Serializable {
    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = -8046859919278042955L;

    /**
     * Create a new wrapper for the given ripemd160 hash.
     *
     * @param hashValue The hash to wrap.
     */
    public TransactionId(String hashValue) {
        super(hashValue);
    }
}
