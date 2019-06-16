package com.eshtio.preprocessor;

import com.eshtio.preprocessor.config.PreprocessorConfig;
import com.eshtio.preprocessor.core.DefineProperties;
import com.eshtio.preprocessor.core.SingleFilePreprocessor;
import com.eshtio.preprocessor.core.directives.impl.IfDirectivesLineHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by EshtIO on 2019-06-16.
 */
public class CSharpFilesPreprocessor {

    private static final Logger log = LoggerFactory.getLogger(CSharpFilesPreprocessor.class);

    private static final PathMatcher CSHARP_FILE_MATCHER =
            FileSystems.getDefault().getPathMatcher("glob:*.cs");

    private final PreprocessorConfig config;

    public CSharpFilesPreprocessor(PreprocessorConfig config) {
        this.config = config;
    }

    public void runPreprocessor() {
        Path inputPath = Paths.get(config.getInputDir());
        Path outputPath = Paths.get(config.getOutputDir(), "preprocess-result");

        DefineProperties define = DefineProperties.read(config.getDefinedPropertiesFile());

        log.info("Run preprocessor file parsing. Input path '{}', output path '{}', define properties file '{}'",
                inputPath, outputPath, config.getDefinedPropertiesFile());

        try (Stream<Path> paths = Files.find(
                inputPath, Integer.MAX_VALUE, (path, attrs) -> CSHARP_FILE_MATCHER.matches(path.getFileName()))) {
            List<Path> collect = paths.collect(Collectors.toList());
            collect.forEach(source ->
                    runPreprocessFile(source, outputPath.resolve(inputPath.relativize(source)), define)
            );
            log.info("Preprocessor completed");
        } catch (Exception ex) {
            try {
                deleteRecursive(outputPath);
            } catch (IOException deleteEx) {
                log.error("Output path deleting error", deleteEx);
            }
            log.error("Preprocess files error", ex);
        }
    }

    private static void deleteRecursive(Path path) throws IOException {
        try (Stream<Path> walk = Files.walk(path)) {
            walk.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }

    }

    private void runPreprocessFile(Path sourceFile, Path targetFile, DefineProperties defineProperties) {
        try {
            Files.createDirectories(targetFile.getParent());
            SingleFilePreprocessor filePreprocessor = new SingleFilePreprocessor(
                    sourceFile,
                    targetFile,
                    new IfDirectivesLineHandler(defineProperties)
            );
            filePreprocessor.runPreprocessFile();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
