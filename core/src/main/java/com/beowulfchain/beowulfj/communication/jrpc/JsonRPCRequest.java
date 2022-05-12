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
package com.beowulfchain.beowulfj.communication.jrpc;

import com.beowulfchain.beowulfj.communication.CommunicationHandler;
import com.beowulfchain.beowulfj.enums.BeowulfApiType;
import com.beowulfchain.beowulfj.enums.RequestMethod;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import javax.annotation.Nullable;
import java.util.Random;

@JsonPropertyOrder({"jsonrpc", "method", "params", "id"})
public class JsonRPCRequest {
    /**
     * The JSON RPC version.
     */
    private static final String JSONRPC = "2.0";
    /**
     * A shared <code>Random</code> instance.
     */
    private static Random randomGenerator = new Random();
    /**
     * The ID of this request.
     */
    private final long id = randomGenerator.nextLong() % 1000000000000L;
    private String method;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object params;

    /**
     * Instantiate a new RequestObject.
     *
     * @param beowulfApiType The {@link BeowulfApiType} the <code>requestMethod</code>
     *                       belongs to or <code>null</code> if the default namespace
     *                       should be used.
     * @param requestMethod  The {@link RequestMethod} to request.
     * @param params         An object which contains all parameters required by the
     *                       <code>requestMethod</code> or <code>null</code> if no
     *                       parameters are required.
     */
    public JsonRPCRequest(@Nullable BeowulfApiType beowulfApiType, RequestMethod requestMethod, @Nullable Object params) {
        String namespaceAndMethod = "";
        if (beowulfApiType != null) {
            namespaceAndMethod = beowulfApiType.name().toLowerCase() + ".";
        }
        this.method = namespaceAndMethod + requestMethod.name().toLowerCase();
        this.params = params;
    }

    public JsonRPCRequest(@Nullable String apiType, String method, @Nullable Object params) {
        String namespaceAndMethod = "";
        if (apiType != null) {
            namespaceAndMethod = apiType + ".";
        }
        this.method = namespaceAndMethod + method;
        this.params = params;
    }

    /**
     * Get the json-rpc version.
     *
     * @return The used json-rpc version.
     */
    public String getJsonrpc() {
        return JSONRPC;
    }

    /**
     * Get the id of this request.
     *
     * @return The id of this request.
     */
    public long getId() {
        return id;
    }

    /**
     * Get the full namespace and method name.
     *
     * @return The full namespace and method name.
     */
    public String getMethod() {
        return method;
    }

    /**
     * Get the additional parameters.
     *
     * @return The additional parameters.
     */
    public Object getParams() {
        return params;
    }

    /**
     * @return The json representation of this object.
     * @throws JsonProcessingException If the object can not be transformed into valid json.
     */
    public String toJson() throws JsonProcessingException {
        return CommunicationHandler.getObjectMapper().writeValueAsString(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
