package com.mhd.accountManagement.exception;

public class UserExistsException extends UserRegisterException{
    public UserExistsException(String message){
        super(message);
    }
}
