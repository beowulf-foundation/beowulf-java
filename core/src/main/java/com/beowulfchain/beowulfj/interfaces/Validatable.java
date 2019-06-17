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
package com.beowulfchain.beowulfj.interfaces;

import com.beowulfchain.beowulfj.enums.ValidationType;

import java.security.InvalidParameterException;

/**
 * This interface is used to make sure an object implements the validate method.
 */
public interface Validatable {
    /**
     * Use this method to verify that this object is valid.
     *
     * @param validationType An indicator telling the method what should be validated.
     * @throws InvalidParameterException If a field does not fulfill the requirements.
     */
    public void validate(ValidationType validationType);
}
