package com.beowulfchain.beowulfj.enums;

public enum ExtensionType {
    EXTENSION_JSON_TYPE(1);

    private byte orderId;

    private ExtensionType(int orderId) {
        this.orderId = (byte) orderId;
    }

    public byte getOrderId() {
        return orderId;
    }

}
