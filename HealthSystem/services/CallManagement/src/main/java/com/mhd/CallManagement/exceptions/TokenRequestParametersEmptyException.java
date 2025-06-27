package com.mhd.CallManagement.exceptions;

public class TokenRequestParametersEmptyException extends RuntimeException {
    public TokenRequestParametersEmptyException(String msg) {
        super(msg);
    }
}
