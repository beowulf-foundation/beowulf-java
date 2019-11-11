package com.beowulfchain.beowulfj.enums;
import org.joou.UInteger;

public enum ExtensionType {
    EXTENSION_VOID_TYPE(UInteger.valueOf(0)),
    EXTENSION_JSON_TYPE(UInteger.valueOf(1));

    private byte orderId;

    private ExtensionType(UInteger orderId) {
        this.orderId = orderId.byteValue();
    }

    public byte getOrderId() {
        return orderId;
    }

}
