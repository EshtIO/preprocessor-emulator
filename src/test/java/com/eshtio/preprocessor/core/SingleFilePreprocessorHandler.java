package com.eshtio.preprocessor.core;

import com.eshtio.preprocessor.core.directives.DirectiveLineHandler;
import com.eshtio.preprocessor.core.directives.impl.IfDirectivesLineHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;

import static com.eshtio.preprocessor.TestUtils.*;

/**
 * Created by EshtIO on 2019-06-15.
 */
public class SingleFilePreprocessorHandler {

    private Path tmpFile;

    private DirectiveLineHandler lineHandler;

    @Before
    public void setUp() throws IOException {
        Path tmpDir = prepareBuildTestDataTmpPath();

        tmpFile = tmpDir.resolve("temp.txt");
        lineHandler = new IfDirectivesLineHandler(getTestDefineProperties());
    }

    @Test
    public void ifDirectiveNested() throws IOException {
        Path source = getTestResourcePath("data/if-directive-case/if-directive-nested-source.txt");
        Path expected = getTestResourcePath("data/if-directive-case/if-directive-nested-expected.txt");

        new SingleFilePreprocessor(source, tmpFile, lineHandler).runPreprocessFile();

        assertFilesEquals(expected, tmpFile);
    }

    @Test
    public void ifDirectiveBranchNotFound() throws IOException {
        Path source = getTestResourcePath("data/if-directive-case/branch-not-found-source.txt");
        Path expected = getTestResourcePath("data/if-directive-case/branch-not-found-expected.txt");

        new SingleFilePreprocessor(source, tmpFile, lineHandler).runPreprocessFile();

        assertFilesEquals(expected, tmpFile);
    }

    @Test
    public void ifDirectiveSimpleIfElseCase() throws IOException {
        Path source = getTestResourcePath("data/if-directive-case/if-directive-simple-case-source.txt");
        Path expected = getTestResourcePath("data/if-directive-case/if-directive-simple-case-expected.txt");

        new SingleFilePreprocessor(source, tmpFile, lineHandler).runPreprocessFile();

        assertFilesEquals(expected, tmpFile);
    }

}
