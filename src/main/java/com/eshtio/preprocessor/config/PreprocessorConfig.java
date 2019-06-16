package com.eshtio.preprocessor.config;

import com.beust.jcommander.Parameter;

/**
 * Created by EshtIO on 2019-06-15.
 */
public class PreprocessorConfig {

    @Parameter(names = {"-i", "--input"}, required = true, description = "Source code directory path")
    private String inputDir;

    @Parameter(names = {"-o", "--output"}, required = true, description = "Output source code directory path")
    private String outputDir;

    @Parameter(names = {"-d", "--defined"}, required = true, description = "Defined properties file path")
    private String definedPropertiesFile;

    public String getInputDir() {
        return inputDir;
    }

    public void setInputDir(String inputDir) {
        this.inputDir = inputDir;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getDefinedPropertiesFile() {
        return definedPropertiesFile;
    }

    public void setDefinedPropertiesFile(String definedPropertiesFile) {
        this.definedPropertiesFile = definedPropertiesFile;
    }

}
