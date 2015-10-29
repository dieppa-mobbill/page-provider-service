package com.pageprovider.util.validation.exception;

/**
 * Created by antoniop on 26/10/15.
 */
public class ValidatorMinLengthException extends ValidatorGenericException{

    private final int length;

    public ValidatorMinLengthException(String fieldName, Object value, int length){
        super(fieldName, value);
        this.length = length;
    }

    @Override
    public String getMessage(){
        return this.fieldName + " value = "+(value!=null?value.toString():"NULL")+" is shorter than "+length;
    }
}
