package com.eshtio.preprocessor;

import com.eshtio.preprocessor.core.DefineProperties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.nio.file.Files.readAllLines;
import static org.junit.Assert.assertEquals;

/**
 * Created by EshtIO on 2019-06-15.
 */
public final class TestUtils {

    public static final String DP_TRUE_KEY = "TRUE_KEY";
    public static final String DP_FALSE_KEY = "FALSE_KEY";
    public static final String DP_NOT_BOOLEAN_KEY = "NOT_BOOLEAN_KEY";
    public static final String DP_NOT_DEFINED_KEY = "NOT_DEFINED_KEY";

    private TestUtils() {
    }

    public static Stream<Path> getFilesStream(Path dir) throws IOException {
        return Files.find(dir, Integer.MAX_VALUE, ((path, attributes) -> attributes.isRegularFile()));
    }

    public static Path getTestResourcePath(String resourceFilePath) {
        return Paths.get(ClassLoader.getSystemResource(resourceFilePath).getPath());
    }

    public static String getBuildPath() {
        return System.getProperty("user.dir") + "/build";
    }

    public static DefineProperties getTestDefineProperties() {
        return DefineProperties.read(ClassLoader.getSystemResource("data/define.properties").getPath());
    }

    public static void assertFilesEquals(Path expectedFile, Path actualFile) throws IOException {
        assertEquals(readAllLines(expectedFile), readAllLines(actualFile));
    }

}
