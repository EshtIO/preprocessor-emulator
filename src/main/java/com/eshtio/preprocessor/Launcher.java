package com.eshtio.preprocessor;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.eshtio.preprocessor.config.PreprocessorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by EshtIO on 2019-06-15.
 */
public class Launcher {

    private static final Logger log = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        PreprocessorConfig config = new PreprocessorConfig();
        log.info("Preprocessor config parsing...");
        try {
            JCommander.newBuilder()
                    .addObject(config)
                    .build()
                    .parse(args);
        } catch (ParameterException ex) {
            log.error("Parse parameters error: {}", ex.getMessage());
            ex.getJCommander().usage();
            return;
        }
        log.info("Start preprocessor...");
        new CSharpFilesPreprocessor(config).runPreprocessor();
        log.info("Preprocessor finished");
    }

}
