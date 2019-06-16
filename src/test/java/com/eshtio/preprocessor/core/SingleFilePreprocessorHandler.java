package com.eshtio.preprocessor.core;

import com.eshtio.preprocessor.core.directives.DirectiveLineHandler;
import com.eshtio.preprocessor.core.directives.impl.IfDirectivesLineHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.eshtio.preprocessor.TestUtils.*;

/**
 * Created by EshtIO on 2019-06-15.
 */
public class SingleFilePreprocessorHandler {

    private static final Path TMP_FILE = Paths.get(getBuildPath() + "/temp.txt");

    private DirectiveLineHandler lineHandler;

    @Before
    public void setUp() throws IOException {
        lineHandler = new IfDirectivesLineHandler(getTestDefineProperties());
        Files.deleteIfExists(TMP_FILE);
    }

    @Test
    public void ifDirectiveNested() throws IOException {
        Path source = getTestResourcePath("data/if-directive-case/if-directive-nested-source.txt");
        Path expected = getTestResourcePath("data/if-directive-case/if-directive-nested-expected.txt");

        new SingleFilePreprocessor(source, TMP_FILE, lineHandler).runPreprocessFile();

        assertFilesEquals(expected, TMP_FILE);
    }

    @Test
    public void ifDirectiveBranchNotFound() throws IOException {
        Path source = getTestResourcePath("data/if-directive-case/branch-not-found-source.txt");
        Path expected = getTestResourcePath("data/if-directive-case/branch-not-found-expected.txt");

        new SingleFilePreprocessor(source, TMP_FILE, lineHandler).runPreprocessFile();

        assertFilesEquals(expected, TMP_FILE);
    }

    @Test
    public void ifDirectiveSimpleIfElseCase() throws IOException {
        Path source = getTestResourcePath("data/if-directive-case/if-directive-simple-case-source.txt");
        Path expected = getTestResourcePath("data/if-directive-case/if-directive-simple-case-expected.txt");

        new SingleFilePreprocessor(source, TMP_FILE, lineHandler).runPreprocessFile();

        assertFilesEquals(expected, TMP_FILE);
    }

}
