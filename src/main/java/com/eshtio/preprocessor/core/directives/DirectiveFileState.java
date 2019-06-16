package com.eshtio.preprocessor.core.directives;

/**
 * Directive file states
 *
 * Created by EshtIO on 2019-06-15.
 */
public enum DirectiveFileState {

    /**
     * Copy lines by default
     */
    DEFAULT,

    /**
     * Skip source lines - don't copy it to result file
     */
    SKIP_LINES

}
