package utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

/**
 * Author: yoosan, SYSUDNLP Group
 * Date: 16/1/1, 2016.
 * Licence MIT
 */
public interface CliRunner {
    /**
     * Init the options
     *
     * @return Options
     */
    public Options initOptions();

    /**
     * @param cmdLine
     * @return validate result
     */
    public boolean validateOptions(CommandLine cmdLine);

    /**
     * start the program
     *
     * @param cmdLine
     */
    public void start(CommandLine cmdLine);
}
