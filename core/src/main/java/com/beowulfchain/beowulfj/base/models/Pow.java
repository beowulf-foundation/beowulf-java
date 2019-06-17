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
import com.beowulfchain.beowulfj.interfaces.ByteTransformable;
import com.beowulfchain.beowulfj.protocol.PublicKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.bittrade.crypto.core.ECKey;
import eu.bittrade.crypto.core.Sha256Hash;

public class Pow implements ByteTransformable {
    @JsonProperty("worker")
    private PublicKey worker;
    // Original type is "digest_type" which is a "fc:sha256" object.
    @JsonProperty("input")
    private Sha256Hash input;
    // TODO: signature_type signature;
    @JsonProperty("signature")
    private String signature;
    // Original type is "digest_type" which is a "fc:sha256" object.
    @JsonProperty("work")
    private Sha256Hash work;

    public Pow() {

    }

    public Pow(ECKey privateKey, Sha256Hash input) {
    }

    public PublicKey getWorker() {
        return worker;
    }

    public Sha256Hash getInput() {
        return input;
    }

    public String getSignature() {
        return signature;
    }

    public Sha256Hash getWork() {
        return work;
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        // TODO Auto-generated method stub
        return null;
    }
}
