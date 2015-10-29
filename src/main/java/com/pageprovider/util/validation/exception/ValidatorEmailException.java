package com.pageprovider.util.validation.exception;

/**
 * Created by antoniop on 26/10/15.
 */
public class ValidatorEmailException extends ValidatorGenericException{


    public ValidatorEmailException(String fieldName, String value) {
        super(fieldName, value);
    }

    @Override
    public String getMessage(){
        return this.fieldName + " value("+value+") is not a right email";
    }
}
