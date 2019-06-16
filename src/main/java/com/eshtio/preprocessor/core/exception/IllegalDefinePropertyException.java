package com.eshtio.preprocessor.core.exception;

/**
 * Created by EshtIO on 2019-06-15.
 */
public class IllegalDefinePropertyException extends RuntimeException {

    public IllegalDefinePropertyException(String property) {
        super(property);
    }
}
