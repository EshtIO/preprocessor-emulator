package com.eshtio.preprocessor.core.directives;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * All supported directives
 *
 * Created by EshtIO on 2019-06-15.
 */
public enum Directive {

    IF("if"),
    ELIF("elif"),
    ELSE("else"),
    ENDIF("endif");

    public static final String IDENTIFIER = "#";

    private final String key;

    Directive(String alias) {
        this.key = IDENTIFIER + alias;
    }

    public String getKey() {
        return key;
    }

    public static Optional<Directive> findByKey(String key) {
        return Stream.of(Directive.values())
                .filter(directive -> directive.key.equals(key))
                .findAny();
    }

}
