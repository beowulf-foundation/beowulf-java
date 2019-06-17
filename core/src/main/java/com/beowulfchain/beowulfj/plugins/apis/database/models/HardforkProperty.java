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
package com.beowulfchain.beowulfj.plugins.apis.database.models;

import com.beowulfchain.beowulfj.fc.TimePointSec;
import com.beowulfchain.beowulfj.protocol.HardforkVersion;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;
import java.util.List;

/**
 * This class represents a Beowulf "api_hardfork_property_object" object.
 */
public class HardforkProperty {
    // Original type is hardfork_property_id_type
    @JsonProperty("id")
    private long id;
    @JsonProperty("processed_hardforks")
    private List<TimePointSec> processedHardforks;
    @JsonProperty("last_hardfork")
    private UInteger lastHardfork;
    @JsonProperty("current_hardfork_version")
    private HardforkVersion currentHardforkVersion;
    @JsonProperty("next_hardfork")
    private HardforkVersion nextHardfork;
    @JsonProperty("next_hardfork_time")
    private TimePointSec nextHardforkTime;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected HardforkProperty() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the processedHardforks
     */
    public List<TimePointSec> getProcessedHardforks() {
        return processedHardforks;
    }

    /**
     * @return the lastHardfork
     */
    public UInteger getLastHardfork() {
        return lastHardfork;
    }

    /**
     * @return the currentHardforkVersion
     */
    public HardforkVersion getCurrentHardforkVersion() {
        return currentHardforkVersion;
    }

    /**
     * @return the nextHardfork
     */
    public HardforkVersion getNextHardfork() {
        return nextHardfork;
    }

    /**
     * @return the nextHardforkTime
     */
    public TimePointSec getNextHardforkTime() {
        return nextHardforkTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
