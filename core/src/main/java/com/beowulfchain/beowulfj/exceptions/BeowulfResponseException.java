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
package com.beowulfchain.beowulfj.exceptions;

import com.fasterxml.jackson.databind.JsonNode;

public class BeowulfResponseException extends Exception {
    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = 147694337695115012L;
    /**
     * The error code.
     */
    private final Integer code;
    /**
     * The error data.
     */
    private final JsonNode data;

    /**
     * Create a new {@link BeowulfResponseException} instance.
     *
     * @param message The error message to set.
     */
    public BeowulfResponseException(String message) {
        super(message);

        this.code = null;
        this.data = null;
    }

    /**
     * Create a new {@link BeowulfResponseException} instance.
     *
     * @param message The error message to set.
     * @param cause   The cause of this response exception.
     */
    public BeowulfResponseException(String message, Throwable cause) {
        super(message, cause);

        this.code = null;
        this.data = null;
    }

    /**
     * Create a new {@link BeowulfResponseException} instance.
     *
     * @param code    The error code to set.
     * @param message The error message to set.
     * @param data    The additional data to set.
     */
    public BeowulfResponseException(Integer code, String message, JsonNode data) {
        super(message);

        this.code = code;
        this.data = data;
    }

    /**
     * Create a new {@link BeowulfResponseException} instance.
     *
     * @param code    The error code to set.
     * @param message The error message to set.
     * @param data    The additional data to set.
     * @param cause   The cause of this response exception.
     */
    public BeowulfResponseException(Integer code, String message, JsonNode data, Throwable cause) {
        super(message, cause);

        this.code = code;
        this.data = data;
    }

    /**
     * Get the error code.
     *
     * @return The code if its available, <code>null</code> otherwise.
     */
    public Integer getCode() {
        return code;
    }

    /**
     * Get the data attached to the response error.
     *
     * @return The data.
     */
    public JsonNode getData() {
        return data;
    }
}
