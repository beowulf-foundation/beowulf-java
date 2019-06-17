package com.beowulfchain.beowulfj.protocol;

import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.interfaces.ByteTransformable;
import com.beowulfchain.beowulfj.protocol.operations.BaseOperation;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joou.UInteger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Symbol extends BaseOperation implements ByteTransformable {
    @JsonProperty("name")
    private String name;
    @JsonProperty("decimals")
    private UInteger decimals;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UInteger getDecimals() {
        return decimals;
    }

    public void setDecimals(UInteger decimals) {
        this.decimals = decimals;
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        int BEOWULF_NAI_SHIFT = 5;
        int SMT_ASSET_NUM_CONTROL_MASK = 0x10;
        try (ByteArrayOutputStream serializedAccountUpdateOperation = new ByteArrayOutputStream()) {
            String asset = this.getName().substring(2); // exclude '@@'
            int asset_num = Integer.parseInt(asset);
            int bytecode = (asset_num << BEOWULF_NAI_SHIFT) | SMT_ASSET_NUM_CONTROL_MASK | this.getDecimals().intValue();
            serializedAccountUpdateOperation.write(bytecode);
            return serializedAccountUpdateOperation.toByteArray();
        } catch (IOException e) {
            throw new BeowulfInvalidTransactionException(
                    "A problem occured while transforming the symbol into a byte array.", e);
        }
    }
}
