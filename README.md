# C# Preprocessor emulator
Configurable console C# preprocessor emulator<br>

## Supported directives:
- `#if` with one property condition, support only `!` operator
- `#elif` with one property condition, support only `!` operator
- `#else`
- `#endif`

## Build the project
From the command line:
* `./gradlew build` (for Unix)
* `gradlew.bat build` (for Windows)

Gradle build tool will create "build" directory with build files

## Run the project
From the command line:
* (Recommended) Execute build project and unarchive `{root}/build/distributions/preprocessor-emulator-{version}.zip`<br>
    Run script with args:  
    * `{unarchived}/bin/preprocessor-emulator.sh {args}` (for Unix)
    * `{unarchived}/bin/preprocessor-emulator.bat {args}` (for Windows)<br>
* (Not recommended)`./gradlew run -Dexec.args="${args}"` where args delimeter - space

### Usage application options (args):
  *  `-i`, `--input` - Source code directory path<br>
  *  `-o`, `--output` - Output source code directory path<br>
  *  `-d`, `--defined` - Defined properties file path<br>
  