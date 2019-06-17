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

import com.beowulfchain.beowulfj.base.models.Version;
import com.beowulfchain.beowulfj.interfaces.ByteTransformable;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * This class is the java implementation of the Beowulf "hardfork_version" object.
 */
public class HardforkVersion extends Version implements ByteTransformable {
    /**
     * This class represents a hardfork version.
     *
     * @param majorVersion    The major version to set.
     * @param hardforkVersion The hardfork version to set.
     */
    public HardforkVersion(byte majorVersion, byte hardforkVersion) {
        super(majorVersion, hardforkVersion, (short) 0);
    }

    /**
     * Like {@link #HardforkVersion(byte, byte)}, but expects a Version object.
     *
     * @param hardforkVersion The hardfork version to set.
     */
    public HardforkVersion(Version hardforkVersion) {
        super(hardforkVersion.toString());
    }

    /**
     * Like {@link #HardforkVersion(byte, byte)}, but expects the hardfork
     * version in its String representation.
     *
     * @param hardforkVersion The hardfork version to set.
     */
    @JsonCreator
    public HardforkVersion(String hardforkVersion) {
        super(hardforkVersion);
    }
}
