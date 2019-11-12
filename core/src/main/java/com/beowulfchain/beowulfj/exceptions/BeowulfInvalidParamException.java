package com.beowulfchain.beowulfj.exceptions;

public class BeowulfInvalidParamException extends RuntimeException {
    private static final long serialVersionUID = 3477552170254217489L;

    public BeowulfInvalidParamException(String message) {
        super(message);
    }

    public BeowulfInvalidParamException(String message, Throwable cause) {
        super(message, cause);
    }


}
