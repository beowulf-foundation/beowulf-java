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
package com.beowulfchain.beowulfj.fc;

import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.interfaces.ByteTransformable;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * This class represents a FC "time_point_sec" Object. It basically wraps a date
 * in its long representation and offers a bunch of utility methods to transform
 * the timestamp into other representations.
 */
public class TimePointSec implements ByteTransformable {
    @JsonIgnore
    private long dateTime;

    /**
     * Default constructor used to deserialize a json String into a date.
     * <p>
     * The date has to be specified as String and needs a special format:
     * yyyy-MM-dd'T'HH:mm:ss
     *
     * <p>
     * Example: "2016-08-08T12:24:17"
     * </p>
     *
     * @param dateTime The date in its String representation.
     */
    public TimePointSec(@JsonProperty String dateTime) {
        try {
            this.setDateTime(dateTime);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Not able to transform '" + dateTime + "' into a date object.");
        }
    }

    /**
     * Create a new TimePointSec object by providing the date as a timestamp.
     *
     * @param dateTime The date as a timestamp.
     */
    public TimePointSec(long dateTime) {
        this.setDateTime(dateTime);
    }

    /**
     * This method returns the date as its String representation. For this, a
     * specific date format ("yyyy-MM-dd'T'HH:mm:ss") is used as it is required
     * by the Beowulf api.
     *
     * @return The date as String.
     */
    @JsonValue
    public String getDateTime() {
        return BeowulfJUtils.transformDateToString(this.getDateTimeAsDate());
    }

    /**
     * Set the date. The date has to be specified as String and needs a special
     * format: yyyy-MM-dd'T'HH:mm:ss
     *
     * <p>
     * Example: "2016-08-08T12:24:17"
     * </p>
     *
     * @param dateTime The date as its String representation.
     * @throws ParseException If the given String does not match the pattern.
     */
    public void setDateTime(String dateTime) throws ParseException {
        this.setDateTime(BeowulfJUtils.transformStringToTimestamp(dateTime));
    }

    /**
     * Set the date by providing a timestamp.
     *
     * @param dateTime The date as a timestamp.
     */
    @JsonIgnore
    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Get the configured date as a {@link java.util.Date Date} object.
     *
     * @return The date.
     */
    @JsonIgnore
    public Date getDateTimeAsDate() {
        return new Date(this.dateTime);
    }

    /**
     * This method returns the data as its int representation.
     *
     * @return The date.
     */
    @JsonIgnore
    public int getDateTimeAsInt() {
        return (int) (this.dateTime / 1000);
    }

    /**
     * This method returns the data as its timestamp representation.
     *
     * @return The date.
     */
    @JsonIgnore
    public long getDateTimeAsTimestamp() {
        return this.dateTime;
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedDateTime = new ByteArrayOutputStream()) {
            serializedDateTime.write(BeowulfJUtils.transformIntToByteArray(this.getDateTimeAsInt()));

            return serializedDateTime.toByteArray();
        } catch (IOException e) {
            throw new BeowulfInvalidTransactionException(
                    "A problem occured while transforming the model into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object otherTimePointSec) {
        if (this == otherTimePointSec)
            return true;
        if (otherTimePointSec == null || !(otherTimePointSec instanceof TimePointSec))
            return false;
        TimePointSec other = (TimePointSec) otherTimePointSec;
        return (this.getDateTime().equals(other.getDateTime()));
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + (this.getDateTime() == null ? 0 : this.getDateTime().hashCode());
        return hashCode;
    }
}