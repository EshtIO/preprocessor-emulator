package com.eshtio.preprocessor.core.directives;

import com.eshtio.preprocessor.core.exception.DirectiveStateException;

/**
 * Created by EshtIO on 2019-06-15.
 */
public interface DirectiveLineHandler {

    boolean isSupportDirective(Directive directive);

    DirectiveFileState handleLine(Directive directive, String line) throws DirectiveStateException;

    void checkFinalDirectiveState() throws DirectiveStateException;

}
