package com.eshtio.preprocessor.core.exception;

import com.eshtio.preprocessor.core.directives.Directive;

/**
 * Created by EshtIO on 2019-06-16.
 */
public class UnsupportedDirectiveException extends RuntimeException {

    public UnsupportedDirectiveException(Directive directive) {
        super(directive.getKey());
    }

}
