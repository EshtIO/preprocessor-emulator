package com.eshtio.preprocessor;

import com.eshtio.preprocessor.config.PreprocessorConfig;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.eshtio.preprocessor.TestUtils.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by EshtIO on 2019-06-16.
 */
public class CSharpFilesPreprocessorTest {

    @Test
    public void runPreprocessForDirectory() throws IOException {
        Path source = getTestResourcePath("data/csharp-dir-tree-case/source");
        Path expected = getTestResourcePath("data/csharp-dir-tree-case/expected");

        PreprocessorConfig config = createConfig(
                source.toString(), TestUtils.getBuildPath() + "/csharp-dir-tree-temp");

        new CSharpFilesPreprocessor(config).runPreprocessor();

        try (Stream<Path> actualFiles = getFilesStream(Paths.get(config.getOutputDir()));
             Stream<Path> expectedFiles = getFilesStream(expected)) {

            List<Path> actualPathList = actualFiles.sorted().collect(Collectors.toList());
            List<Path> expectedPathList = expectedFiles.sorted().collect(Collectors.toList());

            assertEquals(expectedPathList.size(), actualPathList.size());
            for (int i = 0; i < actualPathList.size(); i++) {
                assertFilesEquals(expectedPathList.get(i), actualPathList.get(i));
            }
        }
    }

    @Test
    public void runPreprocessWithoutEndifDirective() throws IOException {
        Path source = getTestResourcePath("data/csharp-dir-no-endif-case");

        PreprocessorConfig config = createConfig(source.toString(), getBuildPath() + "/csharp-dir-no-endif-temp");
        new CSharpFilesPreprocessor(config).runPreprocessor();
        try (Stream<Path> actual = getFilesStream(Paths.get(config.getOutputDir()))) {
            assertEquals(0, actual.count());
        }
    }

    @Test
    public void runPreprocessWithoutIfDirective() throws IOException {
        Path source = getTestResourcePath("data/csharp-dir-no-if-case");

        PreprocessorConfig config = createConfig(source.toString(), getBuildPath() + "/csharp-dir-no-if-temp");
        new CSharpFilesPreprocessor(config).runPreprocessor();
        try (Stream<Path> actual = getFilesStream(Paths.get(config.getOutputDir()))) {
            assertEquals(0, actual.count());
        }
    }

    private static PreprocessorConfig createConfig(String source, String out) {
        PreprocessorConfig config = new PreprocessorConfig();
        config.setInputDir(source);
        config.setOutputDir(out);
        config.setDefinedPropertiesFile(getTestResourcePath("data/define.properties").toString());
        return config;
    }

}
