package com.mhd.DataCollection.exception;

public class DeviceHasAlreadyBeenAddedException extends RuntimeException {
    public DeviceHasAlreadyBeenAddedException(String msg) {
        super(msg);
    }
}
