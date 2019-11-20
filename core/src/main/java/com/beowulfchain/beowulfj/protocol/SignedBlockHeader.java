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

import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.interfaces.ByteTransformable;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.bittrade.crypto.core.CryptoUtils;
import eu.bittrade.crypto.core.ECKey.ECDSASignature;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class is the java implementation of the Beowulf "signed_block_header"
 * object.
 */
public class SignedBlockHeader extends BlockHeader implements ByteTransformable {
    @JsonProperty("supernode_signature")
    protected String supernodeSignature;

    /**
     * @return the ECDSASignature supernode
     */
    public ECDSASignature getSupernodeSignatureECDSA() {
        return ECDSASignature.decodeFromDER(CryptoUtils.HEX.decode(supernodeSignature));
    }

    public String getSupernodeSignature() {
        return this.supernodeSignature;
    }

    /**
     * @param supernodeSignature the supernodeSignature to set
     */
    public void setSupernodeSignature(String supernodeSignature) {
        this.supernodeSignature = supernodeSignature;
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        super.toByteArray();
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
