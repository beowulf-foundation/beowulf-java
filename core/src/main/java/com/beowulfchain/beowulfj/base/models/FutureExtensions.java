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

import com.beowulfchain.beowulfj.interfaces.ByteTransformable;
import com.beowulfchain.beowulfj.protocol.extensions.JsonExtension;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = JsonExtension.class, name = "extension_json_type"),
})
public abstract class FutureExtensions implements ByteTransformable, Serializable {
}
