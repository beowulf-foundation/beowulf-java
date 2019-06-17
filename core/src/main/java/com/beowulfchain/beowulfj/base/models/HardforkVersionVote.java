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

import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.fc.TimePointSec;
import com.beowulfchain.beowulfj.protocol.HardforkVersion;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.io.IOException;

@JsonDeserialize
@JsonSerialize
public class HardforkVersionVote extends BlockHeaderExtensions {
    @JsonProperty("hf_version")
    protected HardforkVersion hfVersion;
    @JsonProperty("hf_time")
    protected TimePointSec hfTime;

    /**
     * Create a new hardfork version vote object.
     *
     * @param hfVersion The hardfork version to set.
     * @param hfTime    The hardfork time to set.
     */
    @JsonCreator
    public HardforkVersionVote(@JsonProperty("hf_version") HardforkVersion hfVersion,
                               @JsonProperty("hf_time") TimePointSec hfTime) {
        this.setHfTime(hfTime);
        this.setHfVersion(hfVersion);
    }

    /**
     * @return the hfVersion
     */
    public HardforkVersion getHfVersion() {
        return hfVersion;
    }

    /**
     * @param hfVersion the hfVersion to set
     */
    public void setHfVersion(HardforkVersion hfVersion) {
        this.hfVersion = hfVersion;
    }

    /**
     * @return the hfTime
     */
    public TimePointSec getHfTime() {
        return hfTime;
    }

    /**
     * @param hfTime the hfTime to set
     */
    public void setHfTime(TimePointSec hfTime) {
        this.hfTime = hfTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedHardforkVersionVoteExtension = new ByteArrayOutputStream()) {
            serializedHardforkVersionVoteExtension.write(this.getHfVersion().toByteArray());
            serializedHardforkVersionVoteExtension.write(this.getHfTime().toByteArray());

            return serializedHardforkVersionVoteExtension.toByteArray();
        } catch (IOException e) {
            throw new BeowulfInvalidTransactionException(
                    "A problem occured while transforming the operation into a byte array.", e);
        }
    }
}
