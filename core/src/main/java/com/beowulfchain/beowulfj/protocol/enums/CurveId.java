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
package com.beowulfchain.beowulfj.protocol.enums;

import com.beowulfchain.beowulfj.base.models.deserializer.CurveIdDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * An enumeration for all existing curve types.
 */
@JsonDeserialize(using = CurveIdDeserializer.class)
public enum CurveId {
    /**
     *
     */
    QUADRATIC,
    /**
     *
     */
    QUADRATIC_CURATION,
    /**
     *
     */
    LINEAR,
    /**
     *
     */
    SQUARE_ROOT
}
