package com.eshtio.preprocessor.core;

import com.eshtio.preprocessor.core.directives.Directive;
import com.eshtio.preprocessor.core.directives.DirectiveFileState;
import com.eshtio.preprocessor.core.directives.DirectiveLineHandler;
import com.eshtio.preprocessor.core.exception.UnsupportedDirectiveException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Preprocessor of one file, mutable.
 * Contains {@link DirectiveFileState} state
 *
 * Created by EshtIO on 2019-06-15.
 */
public class SingleFilePreprocessor {

    private final DirectiveLineHandler lineHandler;

    private final Path source;
    private final Path target;

    private DirectiveFileState state;

    public SingleFilePreprocessor(Path source,
                                  Path target,
                                  DirectiveLineHandler lineHandler) {
        this.lineHandler = lineHandler;
        this.source = source;
        this.target = target;
        this.state = DirectiveFileState.DEFAULT;
    }

    public void runPreprocessFile() throws IOException {
        try (Stream<String> lineStream = Files.lines(source);
             BufferedWriter targetWriter = Files.newBufferedWriter(target)) {
            lineStream.forEach(line -> runPreprocessLine(line, targetWriter));

            lineHandler.checkFinalDirectiveState();
        }
    }

    private void runPreprocessLine(String line, BufferedWriter target) {
        String trimmedLine = line.trim();
        // if directive detected and supported, then change preprocessor state
        if (trimmedLine.startsWith(Directive.IDENTIFIER)) {
            Directive directive = Directive.getByKey(trimmedLine.split(" ")[0]);
            if (lineHandler.isSupportDirective(directive)) {
                state = lineHandler.handleLine(directive, trimmedLine);
            } else {
                throw new UnsupportedDirectiveException(directive);
            }
        } else {
            switch (state) {
                case DEFAULT:
                    writeLine(line, target);
                    return;
                case SKIP_LINES:
                default:
                    // do nothing
            }
        }
    }

    private static void writeLine(String line, BufferedWriter target) {
        try {
            target.write(line);
            target.newLine();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
